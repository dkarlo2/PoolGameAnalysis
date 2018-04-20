package hr.fer.kd.mymath.model;


/**
 * Immutable linija.
 * @author Karlo
 *
 */

public class MyLine {

	private final MyVector start;
	private final MyVector end;

	public MyLine(MyVector start, MyVector end) {
		super();
		this.start = start;
		this.end = end;
	}

	public MyVector getStart() {
		return start;
	}

	public MyVector getEnd() {
		return end;
	}

	public MyLine translate(double dist) {
		double dx = end.getX() - start.getX();
		double dy = end.getY() - start.getY();

		double alpha = Math.atan(dy / dx);

		double ddx = dist * Math.sin(alpha);
		double ddy = dist * Math.cos(alpha);

		return new MyLine(start.add(ddx, -ddy), end.add(ddx, -ddy));
	}

	@Override
	public String toString() {
		return "[" + start.toString() + " - " + end.toString() + "]";
	}

}
