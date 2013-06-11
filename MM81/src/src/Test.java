package src;

import src.CirclesSeparation.Circle;

public class Test {

	public static void main(String[]a){
		/*
		double[][] d;
		CirclesSeparation cs=new CirclesSeparation();
		Circle o1=cs.new Circle(0, 0, 0, 1, 0);
		Circle o2=cs.new Circle(0, -2, 0, 1, 0);
		d=o1.intersection(o1, o2);
		*/
		
		CirclesSeparation cs=new CirclesSeparation();
		double[] x,y,r,m;
		x=new double[]{2,6,2,3,4};
		y=new double[]{2,3,2,3,4};
		r=new double[]{3,2,2,3,4};
		m=new double[]{5,4,0.7,3.4,1.5};
		cs.minimumWork(x, y, r, m);
		
		
	}
}
