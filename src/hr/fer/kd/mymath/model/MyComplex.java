package hr.fer.kd.mymath.model;

import hr.fer.kd.mymath.MathUtil;

public class MyComplex {

	private final double x;

	private final double y;

	public MyComplex() {
		this(0, 0);
	}

	public MyComplex(double x) {
		this(x, 0);
	}

	public MyComplex(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double norm2() {
		return x*x + y*y;
	}

	public double norm() {
		return Math.sqrt(norm2());
	}

	public MyComplex add(MyComplex c) {
		return new MyComplex(x+c.x, y+c.y);
	}

	public MyComplex sub(MyComplex c) {
		return new MyComplex(x-c.x, y-c.y);
	}

	public MyComplex mul(double d) {
		return new MyComplex(x * d, y * d);
	}

	public MyComplex mul(MyComplex c) {
		return new MyComplex(x*c.x - y*c.y, x*c.y + y*c.x);
	}

	public MyComplex div(double d) {
		return new MyComplex(x / d, y / d);
	}

	public MyComplex div(MyComplex c) {
		return this.mul(new MyComplex(c.x, -c.y)).div(c.norm2());
	}

	public MyComplex sqrt() {
		if (MathUtil.doubleEQ(norm(), 0)) {
			return new MyComplex();
		}
		double r = norm();
		double fi = Math.atan2(y, x);

		double newr = Math.sqrt(r);
		double newfi = fi / 2;

		return new MyComplex(newr * Math.cos(newfi), newr * Math.sin(newfi));
	}

	public boolean isReal() {
		return MathUtil.doubleZero(y);
	}

	public boolean isImaginary() {
		return !isReal();
	}

	@Override
	public String toString() {
		return "(" + x + " + i * " + y + ")";
	}
}
