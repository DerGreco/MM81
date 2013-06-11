package src;

import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

public class CirclesSeparation {
	
	private Vector<Circle> _circles=new Vector<Circle>();
	
	private Vector<Push> _pushes=new Vector<Push>();

	public class Circle implements Comparable<Circle>{

		private int _order=-1;
		private double _Xcoord=0.0;
		private double _Ycoord=0.0;
		private double _rad=0.0;
		private double _mass=0.0;
		private final double _eps=0.000000001;
		
		public Circle(int _order, double _Xcoord, double _Ycoord, double _rad,
				double _mass) {
			super();
			this._order = _order;
			this._Xcoord = _Xcoord;
			this._Ycoord = _Ycoord;
			this._rad = _rad;
			this._mass = _mass;
		}		
		//Devuelve el mismo circulo con el radio aumentado en rad.
		public Circle(Circle o, double rad) {
			super();
			this._order = o._order;
			this._Xcoord = o._Xcoord;
			this._Ycoord = o._Ycoord;
			this._rad = o._rad+rad;
			this._mass = o._mass;
		}
		public double distance(Circle o){
			double toRet=0.0;
			toRet=Math.sqrt(
					Math.pow(this._Xcoord - o._Xcoord,2)+
					Math.pow(this._Ycoord - o._Ycoord,2));
			return toRet;
		}	
		public double distance(double x, double y){
			double toRet=0.0;
			toRet=Math.sqrt(
					Math.pow(this._Xcoord - x,2)+
					Math.pow(this._Ycoord - y,2));
			return toRet;
		}
		//No  considera que dos circulos tangentes se superpongan, quiza deberia, por el eps.
		public boolean overlaps(Circle o){
			boolean toRet=false;
			if(distance(o)<(this._rad+o._rad+_eps))toRet=true;
			return toRet;
		}		
		//Devuelve null para dos circulos tangentes.
		public double[][] intersection(Circle o1, Circle o2){
			double[][] toRet=null;
			if(o1.overlaps(o2)){
				toRet=new double[2][2];
				double p,q,r,a,b,c;
				p=Math.pow(o1._rad, 2)-Math.pow(o1._Xcoord, 2)-Math.pow(o1._Ycoord, 2)-
					Math.pow(o2._rad, 2)+Math.pow(o2._Xcoord, 2)+Math.pow(o2._Ycoord, 2);
				q=2*o1._Ycoord-2*o2._Ycoord;
				r=2*o2._Xcoord-2*o1._Xcoord;
				a=1+(Math.pow(q, 2)/Math.pow(r, 2));
				b=((2*p*q)/Math.pow(r, 2))-((2*q*o1._Xcoord)/r)-2*o1._Ycoord;
				c=(Math.pow(p, 2)/Math.pow(r, 2))-((2*p*o1._Xcoord)/r)+Math.pow(o1._Xcoord, 2)+
					Math.pow(o1._Ycoord, 2)-Math.pow(o1._rad, 2);
				toRet[0][1]=(-b+Math.sqrt(Math.pow(b, 2)-4*a*c))/(2*a);
				toRet[1][1]=(-b-Math.sqrt(Math.pow(b, 2)-4*a*c))/(2*a);
				toRet[0][0]=(p+q*toRet[0][1])/r;
				toRet[1][0]=(p+q*toRet[1][1])/r;
			}
			return toRet;
		}
		public void push(Circle o){
			if(this.overlaps(o)){
				double vx=o._Xcoord-this._Xcoord;
				double vy=o._Ycoord-this._Ycoord;
				double k=(this._rad+o._rad+_eps)/(Math.sqrt(Math.pow(vx,2)+Math.pow(vy, 2)));
				o.set_Xcoord(this._Xcoord+k*vx);
				o.set_Ycoord(this._Ycoord+k*vy);
				_pushes.add(new Push(this,o));
			}
		}
		//Compares by order
		public Comparator<Circle> CircleByOrder 
        = new Comparator<Circle>() {
			@Override
	 		public int compare(Circle o1, Circle o2) {			
				return o1._order-o2._order;
			}
		};
		//Compares by mass
		@Override		
		public int compareTo(Circle o) {
			int toRet=0;
			if(this._mass>o._mass)toRet=1;
			else if(this._mass==o._mass)toRet=0;
			else toRet=-1;
			return toRet;
		}
		public void set_Xcoord(double _Xcoord) {
			this._Xcoord = _Xcoord;
		}
		public void set_Ycoord(double _Ycoord) {
			this._Ycoord = _Ycoord;
		}
		public int get_order() {
			return _order;
		}		
	}

	public class Push{
		
		private Circle _pushes;
		private Circle _pushed;
		
		public Push(Circle o, Circle o2){
			_pushes=o;
			_pushed=o2;
		}
		
		public boolean samePush(Push p){
			boolean toRet=false;
			if(this._pushes.get_order()==p._pushes.get_order() &&
			   this._pushed.get_order()==p._pushed.get_order())toRet=true;
			return toRet;
		}
		
		
	}
	
	/*
	public static void main(String[]a){
		int n;
		double[] x,y,r,m,ret;
		Scanner sc=new Scanner(System.in);
		CirclesSeparation cs=new CirclesSeparation();
		n=Integer.parseInt(sc.nextLine());
		x=y=r=m=ret=new double[n];
		for (int i = 0; i < x.length; i++)x[i]=Double.parseDouble(sc.nextLine());
		for (int i = 0; i < y.length; i++)y[i]=Double.parseDouble(sc.nextLine());
		for (int i = 0; i < r.length; i++)r[i]=Double.parseDouble(sc.nextLine());
		for (int i = 0; i < m.length; i++)m[i]=Double.parseDouble(sc.nextLine());
		ret=cs.minimumWork(x, y, r, m);
		for (int i = 0; i < ret.length; i++)System.out.println(ret[i]);
	}
	*/
	
	public double[] minimumWork(double[] x, double[] y, double[] r, double[] m){		
		Circle aux=new Circle(0,0,0,0,0);		
		double[] toRet=null;		
		initializeCircles(x, y, r, m);
		while(overlapping());
		Collections.sort(_circles, aux.CircleByOrder);
		toRet=new double[_circles.size()*2];
		int j=0;
		for (Circle o : _circles) {
			toRet[j]=o._Xcoord;
			j++;
			toRet[j]=o._Ycoord;
			j++;
		}
		return toRet;
	}
	//No se si esto funcionara, porque el toArray deberia poder inferir el tipo de toRet;
	private double[][] intersections(Vector<Circle> v, double rad) {
		double[][] toRet=null;
		Vector<Double> inters=new Vector<Double>();
		for (Circle o : v) {
			for (Circle o2 : v) {
				if(!o.equals(o2) && o.CircleByOrder.compare(o, o2)>0){					
					toRet=o.intersection(new Circle(o, rad), new Circle(o2, rad));
					if(toRet!=null){
						inters.add(toRet[0][0]);
						inters.add(toRet[0][1]);						
						inters.add(toRet[1][0]);
						inters.add(toRet[1][1]);						
					}					
				}
			}
		}
		if(inters.size()!=0){
			toRet=new double[inters.size()/2][2];		
			for (int i = 0; i < toRet.length; i++) {
				toRet[i][0]=inters.elementAt(2*i);
				toRet[i][1]=inters.elementAt(2*i+1);
			}
		}		
		return toRet;
	}

	private void initializeCircles(double[] x, double[] y, double[] r, double[] m){
		for (int i = 0; i < m.length; i++) {
			_circles.add(new Circle(i, x[i], y[i], r[i], m[i]));
		}
		Collections.sort(_circles);
		Collections.reverse(_circles);
	}
	
	private boolean overlapping(){		
		boolean toRet=false;
		for (Circle o : _circles) {
			for (Circle o2 : _circles) {
				if(!o.equals(o2)){
					if(o.overlaps(o2)){
						toRet=true;
						if(!yetPushed(new Push(o, o2)))o.push(o2);
						else{
							o2.set_Xcoord(o2._Xcoord+(Math.random()-0.0)*0.05);
							o2.set_Ycoord(o2._Ycoord+(Math.random()-0.0)*0.05);
						}						
					}
				}					
			}
		}
		return toRet;
	}
	
	private boolean yetPushed(Push p2){
		boolean toRet=false;
		for (Push p : _pushes) {
			if(p.samePush(p2))toRet=true;
		}
		return toRet;
	}
}
