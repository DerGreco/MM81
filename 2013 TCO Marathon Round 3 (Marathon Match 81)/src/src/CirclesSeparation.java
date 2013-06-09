package src;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class CirclesSeparation {
	
	private Vector<Circle> _circles=new Vector<Circle>();

	public class Circle implements Comparable<Circle>, Comparator<Circle>{

		private int _order=-1;
		private double _Xcoord=0.0;
		private double _Ycoord=0.0;
		private double _rad=0.0;
		private double _mass=0.0;
		private final double _eps=Double.MIN_VALUE;
		
		public Circle(int _order, double _Xcoord, double _Ycoord, double _rad,
				double _mass) {
			super();
			this._order = _order;
			this._Xcoord = _Xcoord;
			this._Ycoord = _Ycoord;
			this._rad = _rad;
			this._mass = _mass;
		}
		
		public double distance(Circle o){
			double toRet=0.0;
			toRet=Math.sqrt(
					Math.pow(this._Xcoord - o._Xcoord,2)+
					Math.pow(this._Ycoord - o._Ycoord,2));
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
				
			}
		}
		
		//Compares by order
		@Override
 		public int compare(Circle o1, Circle o2) {			
			return o1._order-o2._order;
		}

		//Compares by mass
		@Override		
		public int compareTo(Circle o) {
			int toRet=0;
			if(this._mass>o._mass)toRet=1;
			else if(this._mass==o._mass)toRet=0;
			else toRet=-1;
			return toRet;
		}

		public int get_order() {
			return _order;
		}

		public double get_Xcoord() {
			return _Xcoord;
		}

		public double get_Ycoord() {
			return _Ycoord;
		}

		public double get_rad() {
			return _rad;
		}

		public double get_mass() {
			return _mass;
		}
		
	}

	public double[] minimumWork(double[] x, double[] y, double[] r, double[] m){
		double[] toRet=null;
		initializeCircles(x, y, r, m);
		return toRet;
	}
	
	private void initializeCircles(double[] x, double[] y, double[] r, double[] m){
		for (int i = 0; i < m.length; i++) {
			_circles.add(new Circle(i, x[i], y[i], r[i], m[i]));
		}
		Collections.sort(_circles);
		Collections.reverse(_circles);
	}
	
}
