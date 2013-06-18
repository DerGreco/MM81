package src;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class CirclesSeparation {
    
    private Vector<Circle> _circles=new Vector<Circle>();   
    private double wm, wr;    

    public class Circle implements Comparable<Circle>{

        private int _order=-1;
        private double _Xcoord=0.0;
        private double _Ycoord=0.0;
        private double _rad=0.0;
        private double _mass=0.0;
        private final double _eps=0.0000000001;
        
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
            return Math.sqrt((this._Xcoord - o._Xcoord)*(this._Xcoord - o._Xcoord)+
                    (this._Ycoord - o._Ycoord)*(this._Ycoord - o._Ycoord));
        }    
        public double distance(double x, double y){            
            return Math.sqrt((this._Xcoord - x)*(this._Xcoord - x)+
                    (this._Ycoord - y)*(this._Ycoord - y));
        }
        //No  considera que dos circulos tangentes se superpongan, quiza deberia, por el eps.
        public boolean overlaps(Circle o){            
            if(distance(o)<(this._rad+o._rad+_eps))return true;
            return false;
        }        
        //Devuelve null para dos circulos tangentes.
        public double[][] intersection(Circle o1, Circle o2){                                
            if(!o1.overlaps(o2))return null;
            else{                
                double p,q,r,a,b,c;
                p=o1._rad*o1._rad-o1._Xcoord*o1._Xcoord-o1._Ycoord*o1._Ycoord-
                        o2._rad*o2._rad+o2._Xcoord*o2._Xcoord+o2._Ycoord*o2._Ycoord;                
                q=2*o1._Ycoord-2*o2._Ycoord;
                r=2*o2._Xcoord-2*o1._Xcoord;
                a=1+((q*q)/(r*r));
                b=((2*p*q)/(r*r))-((2*q*o1._Xcoord)/r)-2*o1._Ycoord;
                c=(p*p)/(r*r)-((2*p*o1._Xcoord)/r)+o1._Xcoord*o1._Xcoord+
                    o1._Ycoord*o1._Ycoord-o1._rad*o1._rad;
                if(b*b-4*a*c<0)return null; 
                double[][] toRet=new double[2][2];
                toRet[0][1]=(-b+Math.sqrt(b*b-4*a*c))/(2*a);
                toRet[1][1]=(-b-Math.sqrt(b*b-4*a*c))/(2*a);
                toRet[0][0]=(p+q*toRet[0][1])/r;
                toRet[1][0]=(p+q*toRet[1][1])/r;
                return toRet;
            }            
        }
        public void push(Circle o){
            if(this.overlaps(o)){                
                double vx=o._Xcoord-this._Xcoord;
                double vy=o._Ycoord-this._Ycoord;
                double k=(this._rad+o._rad+_eps)/(Math.sqrt(vx*vx+vy*vy));                
                o._Xcoord=this._Xcoord+k*vx;
                o._Ycoord=this._Ycoord+k*vy;                
            }
        }
        //Compares by order
        public Comparator<Circle> CircleByOrder 
        = new Comparator<Circle>() {            
             public int compare(Circle o1, Circle o2) {            
                return o1._order-o2._order;
            }
        };
        public Comparator<Circle> CircleByRadius 
        = new Comparator<Circle>() {            
             public int compare(Circle o1, Circle o2) {        
                 if(o1._rad-o1._mass<o2._rad-o2._mass)return -1;
                 else if(o1._rad==o2._rad)return 0;
                 else return 1;
            }
        };
      //Compares by mass        
        public int compareTo(Circle o) {
            int toRet=0;  
            wm=1;
            wr=5;
            double maxR=Math.sqrt(5/_circles.size());
            if(wm*this._mass-wr*this._rad>wm*o._mass-wr*o._rad)toRet=1;
            else if(this._mass==o._mass)toRet=0;
            else toRet=-1;
            return toRet;
        }
                    
        public int get_order() {
            return _order;
        }        
    }
            
    public class Intersection implements Comparable<Intersection>{
        
        private double _x, _y, _distance;               
        
        public Intersection(double x, double y){
            _x=x;
            _y=y;
            _distance=100;
        }                

        public int compareTo(Intersection o) {        
            int toRet=0;
            if(this._distance<o._distance)toRet=-1;
            else if(this._distance>o._distance)toRet=1;
            return toRet;
        }
        
        
    }
    
    public double[] minimumWork(double[] x, double[] y, double[] r, double[] m){    
        int i=0;        
        double score=0;
        Vector<Circle> placedCircles=new Vector<Circle>();
        Vector<Circle> toPlace=new Vector<Circle>();
        Circle aux=new Circle(0,0,0,0,0);        
        double[] toRet=null;        
        initializeCircles(x, y, r, m);                        
        for (Circle o : _circles) {
            if(i==0){                        
                placedCircles.add(o);
            }else{                                                                
                aux=overlapped(placedCircles, o);
                if(aux==null)placedCircles.add(o);        
                else{
                    toPlace.add(o);
                }
            }            
            i++;
        }           
        for (Circle c : toPlace) {
            placedCircles=placeCircle(placedCircles, c);
        }        
        aux=new Circle(0,0,0,0,0);
        Collections.sort(_circles, aux.CircleByOrder);        
        toRet=new double[_circles.size()*2];
        int j=0;
        for (Circle o : _circles) {
            toRet[j]=o._Xcoord;
            j++;
            toRet[j]=o._Ycoord;
            j++;
        }
        for (int k=0; k < _circles.size(); k++) {
            score += m[k] * Math.sqrt((x[k] - toRet[2*k]) * (x[k] - toRet[2*k]) + (y[k] - toRet[2*k+1]) * (y[k] - toRet[2*k+1]));
        }
        return toRet;
    }
    
    private Vector<Circle> placeCircle(Vector<Circle> placedCircles, Circle o){        
        Vector<Intersection> intersections=null;
        Vector<Circle> copy=new Vector<Circle>();
        copy=copy(placedCircles);        
        for (Circle c : copy) {
            if(c.overlaps(o)){
                c.push(o);
                placedCircles.add(o);
                if(overlapping(placedCircles)){
                    placedCircles.remove(o);
                    intersections=intersections(placedCircles, o._rad+2*o._eps);                    
                    for (Intersection in : intersections) {
                        in._distance=o.distance(in._x, in._y);
                    }
                    Collections.sort(intersections);                    
                    for (Intersection in : intersections) {                        
                        if(overlapped(placedCircles, 
                                new Circle(-1, in._x, in._y, o._rad, o._mass))==null){                            
                            o._Xcoord=in._x;
                            o._Ycoord=in._y;
                            placedCircles.add(o);                            
                            return placedCircles;
                        }                        
                    }                    
                }else return placedCircles;
            }
        }
        return placedCircles;
    }
    
    private Vector<Circle> copy(Vector<Circle> c){
        Vector<Circle> copy=new Vector<Circle>();
        for (Circle circle : c) {
            copy.add(circle);
        }
        return copy;
    }
    
    //No se si esto funcionara, porque el toArray deberia poder inferir el tipo de toRet;
     private Vector<Intersection> intersections(Vector<Circle> v, double rad) {
        double[][] aux;
        Vector<Intersection> toRet=new Vector<Intersection>(); 
        Circle o,o2;        
        double factor=((_circles.size()-500)*(_circles.size()-500))/25+510;
        for (int i=v.size()-1; i>=0; i--) {
            for (int j=v.size()-1; j>=0; j--) {
                if(toRet.size()<factor){
                    o=v.elementAt(i);
                    o2=v.elementAt(j);
                    if(!o.equals(o2) && o.CircleByOrder.compare(o, o2)>0){                    
                        aux=o.intersection(new Circle(o, rad), new Circle(o2, rad));
                        if(aux!=null){                        
                            toRet.add(new Intersection(aux[0][0],aux[0][1]));        
                            toRet.add(new Intersection(aux[1][0],aux[1][1]));                                                        
                        }                    
                    }
                }else return toRet;
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
    
    private boolean overlapping(Vector<Circle> c){        
        boolean toRet=false;
        for (Circle o : c) {
            for (Circle o2 : c) {
                if(!o.equals(o2) && o.CircleByOrder.compare(o, o2)<0){
                    if(o.overlaps(o2)){
                        toRet=true;                                                
                    }
                }                    
            }
        }
        return toRet;
    }
    
    private Circle overlapped(Vector<Circle> c, Circle o2){        
        Circle toRet=null;        
        for (Circle o : c) {                        
                if(o.overlaps(o2)){
                    return o;                                                
                }                                            
        }
        return toRet;
    }

	public double getWm() {
		return wm;
	}

	public void setWm(double wm) {
		this.wm = wm;
	}

	public double getWr() {
		return wr;
	}

	public void setWr(double wr) {
		this.wr = wr;
	}        
}