package hr.fer.kd.mymath.model;

import hr.fer.kd.mymath.MathUtil;


/**
 * Immutable vektor.
 * @author Karlo
 *
 */
public class MyVector {

	private final double x;

	private final double y;

	public MyVector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public MyVector() {
		this(0, 0);
	}

	public MyVector(MyVector v) {
		this(v.x, v.y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public MyVector add(MyVector v) {
		return new MyVector(x+v.x, y+v.y);
	}

	public MyVector add(double dx, double dy) {
		return new MyVector(x+dx, y+dy);
	}

	public MyVector sub(MyVector v) {
		return new MyVector(x-v.x, y-v.y);
	}

	public MyVector sub(double dx, double dy) {
		return new MyVector(x-dx, y-dy);
	}

	public MyVector mul(double mulx, double muly) {
		return new MyVector(x*mulx, y*muly);
	}

	public MyVector mul(double m) {
		return mul(m, m);
	}

	public MyVector normalize() {
		double norm = norm();
		if (norm == 0) {
			return new MyVector();
		}
		return new MyVector(x/norm, y/norm);
	}

	public double norm() {
		return Math.sqrt(x*x + y*y);
	}

	public double norm2() {
		return x*x + y*y;
	}

	public double dot(MyVector v) {
		return x*v.x + y*v.y;
	}

	public double cosAngle(MyVector v) {
		return dot(v)/ v.norm() / norm();
	}

	public MyVector findPerpendicular(MyVector direction) {
		MyVector v;
		if (MathUtil.doubleZero(y)) {
			v = new MyVector(0, 1);
		} else {
			v = new MyVector(1, -x/y).normalize();
		}

		MyImplicitLineH line = new MyImplicitLineH(new MyLine(new MyVector(), this));

		boolean b1 = line.isUp(direction);
		boolean b2 = line.isUp(v);

		if (b1 && b2 || !b1 && !b2) {
			return v;
		} else {
			return v.mul(-1);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof MyVector)) {
			return false;
		}

		MyVector v = (MyVector) obj;

		return MathUtil.doubleEQ(x, v.x) && MathUtil.doubleEQ(y, v.y);
	}

	@Override
	public String toString() {
		return String.format("(%.6f, %.6f)", x, y);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

}
