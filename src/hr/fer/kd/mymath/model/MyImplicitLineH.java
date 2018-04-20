package hr.fer.kd.mymath.model;


public class MyImplicitLineH {

	public double a;

	public double b;

	public double c;

	public MyImplicitLineH(MyLine line) {
		a = line.getStart().getY() - line.getEnd().getY();
		b = line.getEnd().getX() - line.getStart().getX();
		c = line.getStart().getX() * line.getEnd().getY() - line.getEnd().getX() * line.getStart().getY();
	}

	public boolean isUp(MyVector point) {
		return a*point.getX() + b*point.getY() + c > 0;
	}

}
