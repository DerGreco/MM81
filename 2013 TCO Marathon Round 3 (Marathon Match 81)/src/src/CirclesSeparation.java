package src;

import java.util.Comparator;

public class CirclesSeparation {

	private class Circle implements Comparable<Circle>, Comparator<Circle>{

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
		
		public boolean overlaps(Circle o){
			boolean toRet=false;
			if(distance(o)<(this._rad+o._rad+_eps))toRet=true;
			return toRet;
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
}
