package hr.fer.kd.mymath;

import hr.fer.kd.mymath.exceptions.RootException;
import hr.fer.kd.mymath.model.MyComplex;
import hr.fer.kd.mymath.model.MyImplicitLineH;
import hr.fer.kd.mymath.model.MyLine;
import hr.fer.kd.mymath.model.MyVector;

public class MathUtil {

	private MathUtil() {}

	public static boolean between(MyVector point, MyLine line) {
		double x1 = Math.min(line.getStart().getX(), line.getEnd().getX());
		double x2 = Math.max(line.getStart().getX(), line.getEnd().getX());
		double y1 = Math.min(line.getStart().getY(), line.getEnd().getY());
		double y2 = Math.max(line.getStart().getY(), line.getEnd().getY());

		double x = point.getX();
		double y = point.getY();

		return doubleGE(x, x1) && doubleLE(x, x2) && doubleGE(y, y1) && doubleLE(y, y2);
	}

	public static boolean after(MyVector point, MyLine line) {
		MyVector v1 = line.getEnd().sub(line.getStart());
		MyVector v2 = point.sub(line.getStart());
		return !doubleZero(v1.norm()) && !doubleZero(v2.norm()) && v1.cosAngle(v2) > 0;
	}

	public static MyVector findIntersection(MyLine line1, MyLine line2) {
		MyImplicitLineH il1 = new MyImplicitLineH(line1);
		MyImplicitLineH il2 = new MyImplicitLineH(line2);

		double crossC = il1.a * il2.b - il1.b * il2.a;

		if (crossC == 0) {
			return null;
		}

		double crossX = (il1.b * il2.c - il1.c * il2.b) / crossC;
		double crossY = (il1.c * il2.a - il1.a * il2.c) / crossC;

		return new MyVector(crossX, crossY);
	}

	public static MyVector findIntersectionX(MyLine line, double x) {
		MyVector dir = line.getEnd().sub(line.getStart());
		MyVector dirX = dir.mul((x - line.getStart().getX()) / dir.getX());
		if (dir.dot(dirX) < 0) {
			return null;
		} else {
			return line.getStart().add(dirX);
		}
	}

	public static MyVector findIntersectionY(MyLine line, double y) {
		MyVector dir = line.getEnd().sub(line.getStart());
		MyVector dirY = dir.mul((y - line.getStart().getY()) / dir.getY());
		if (dir.dot(dirY) < 0) {
			return null;
		} else {
			return line.getStart().add(dirY);
		}
	}

	public static double distance(MyVector point1, MyVector point2) {
		return Math.sqrt(distance2(point1, point2));
	}

	public static double distance2(MyVector point1, MyVector point2) {
		double dx = point1.getX() - point2.getX();
		double dy = point1.getY() - point2.getY();
		return dx*dx + dy*dy;
	}

	public static MyComplex[] roots1(double a, double b) throws RootException {
		if (doubleZero(a)) {
			throw new RootException();
		}

		return new MyComplex[]{new MyComplex(-b/a)};
	}

	public static MyComplex[] roots2(double a, double b, double c) throws RootException {
		if (doubleZero(a)) {
			return roots1(b, c);
		}

		double D = b*b - 4*a*c;
		if (D >= 0) {
			MyComplex x1 = new MyComplex((-b+Math.sqrt(D))/2/a);
			MyComplex x2 = new MyComplex((-b-Math.sqrt(D))/2/a);

			return new MyComplex[]{x1, x2};
		} else {
			MyComplex x1 = new MyComplex(-b/2/a, Math.sqrt(-D)/2/a);
			MyComplex x2 = new MyComplex(-b/2/a, -Math.sqrt(-D)/2/a);

			return new MyComplex[]{x1, x2};
		}
	}

	public static MyComplex[] roots3(double a, double b, double c, double d) throws RootException {
		if (doubleZero(a)) {
			return roots2(b, c ,d);
		}

		double f = ((3*c/a) - b*b/a/a) / 3;
		double g = (2*b*b*b/a/a/a - 9*b*c/a/a + 27*d/a) / 27;
		double h = g*g/4 + f*f*f/27;

		if (h > 0) {
			double R = -g/2 + Math.sqrt(h);
			double S = thirdRoot(R);
			double T = -g/2 - Math.sqrt(h);
			double U = thirdRoot(T);

			MyComplex x1 = new MyComplex(S + U - b/3/a);
			MyComplex x2 = new MyComplex(-(S + U)/2 - (b/3/a), (S-U)*Math.sqrt(3)/2);
			MyComplex x3 = new MyComplex(-(S + U)/2 - (b/3/a), -(S-U)*Math.sqrt(3)/2);

			return new MyComplex[]{x1, x2, x3};
		}

		if (doubleZero(f) && doubleZero(g) && doubleZero(h)) {
			double x = thirdRoot(d/a) * -1;

			return new MyComplex[]{new MyComplex(x), new MyComplex(x), new MyComplex(x)};
		}

		double i = Math.sqrt(g*g/4 - h);
		double j = thirdRoot(i);
		double K = Math.acos(- g / 2 / i);
		double L = j * -1;
		double M = Math.cos(K / 3);
		double N = Math.sqrt(3) * Math.sin(K / 3);
		double P = b/3/a * -1;

		MyComplex x1 = new MyComplex(2*j*Math.cos(K/3) - b/3/a);
		MyComplex x2 = new MyComplex(L * (M + N) + P);
		MyComplex x3 = new MyComplex(L * (M - N) + P);

		return new MyComplex[]{x1, x2, x3};
	}

	public static MyComplex[] roots4(double a, double b, double c, double d, double e) throws RootException {
		if (doubleZero(a)) {
			MyComplex[] roots = roots3(b, c, d, e);

			return roots;
		}

		b /= a;
		c /= a;
		d /= a;
		e /= a;
		a = 1;

		double f = c - 3*b*b/8;
		double g = d + b*b*b/8 - b*c/2;
		double h = e - 3*b*b*b*b/256 + b*b*c/16 - b*d/4;

		MyComplex[] Y = chooseTwo(roots3(1, f/2, (f*f -4*h)/16, -g*g/64));

		MyComplex p = Y[0].sqrt();
		MyComplex q = Y[1].sqrt();
		MyComplex r = new MyComplex(-g).div(p.mul(q).mul(8));
		MyComplex s = new MyComplex(b).div(4).div(a);

		MyComplex x1 = p.add(q).add(r).sub(s);
		MyComplex x2 = p.sub(q).sub(r).sub(s);
		MyComplex x3 = q.sub(p).sub(r).sub(s);
		MyComplex x4 = r.sub(p).sub(q).sub(s);

		return new MyComplex[]{x1, x2, x3, x4};
	}

	private static double thirdRoot(double x) {
		return x > 0 ? Math.pow(x, 1./3) : -Math.pow(-x, 1./3);
	}

	private static MyComplex[] chooseTwo(MyComplex[] x) {
		if (x[0].isImaginary() || x[1].isImaginary()) {
			if (!x[0].isImaginary()) {
				return new MyComplex[]{x[1], x[2]};
			}
			if (!x[1].isImaginary()) {
				return new MyComplex[]{x[0], x[2]};
			}
			return new MyComplex[]{x[0], x[1]};
		} else {
			if (doubleEQ(x[0].norm(), 0)) {
				return new MyComplex[]{x[1], x[2]};
			}
			if (doubleEQ(x[1].norm(), 0)) {
				return new MyComplex[]{x[0], x[2]};
			}
			return new MyComplex[]{x[0], x[1]};
		}
	}

	private static final double TRESH = 1e-6;

	public static boolean doubleEQ(double x, double y) {
		return Math.abs(x-y) < TRESH;
	}

	public static boolean doubleZero(double x) {
		return doubleEQ(x, 0);
	}

	public static boolean doubleGE(double x, double y) {
		return x > y || doubleEQ(x, y);
	}

	public static boolean doubleLE(double x, double y) {
		return x < y || doubleEQ(x, y);
	}

}
