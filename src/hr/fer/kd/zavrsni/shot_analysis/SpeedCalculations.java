package hr.fer.kd.zavrsni.shot_analysis;

import java.util.Map;

import hr.fer.kd.mymath.MathUtil;
import hr.fer.kd.mymath.exceptions.RootException;
import hr.fer.kd.mymath.model.MyComplex;
import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.model.balls.Ball;
import hr.fer.kd.zavrsni.shot_analysis.model.ACushion;
import hr.fer.kd.zavrsni.shot_analysis.simulation.Resolver;

public class SpeedCalculations {

	private SpeedCalculations() {}

	private static final double FRICTION = -0.03;

	public static final double BALL2CUSHION_ELOSS = 0.7;

	public static final double BALL2BALL_ELOSS = 0.8;

	public static double startSpeed(MyVector start, MyVector end, double endSpeed) {
		double path = MathUtil.distance(start, end);
		return Math.sqrt(endSpeed * endSpeed - 2 * FRICTION * path);
	}

	public static double startToEndTime(MyVector start, MyVector velocity, MyVector end) {
		double path = MathUtil.distance(start, end);
		double speed = velocity.norm();
		double speedEnd = Math.sqrt(speed * speed + 2 * FRICTION * path);
		double time = (speedEnd - speed) / FRICTION;
		if (stoppingTime(velocity) < time || !MathUtil.doubleEQ(velocity.cosAngle(end.sub(start)), 1)) {
			return -1;
		} else {
			return time;
		}
	}

	public static MyVector newPosAfterTime(MyVector position, MyVector velocity, double time) {
		double speed = velocity.norm();
		double path = speed * time + FRICTION * time * time / 2;
		MyVector newPosition = position.add(velocity.normalize().mul(path));
		return newPosition;
	}

	public static MyVector newVelAfterTime(MyVector velocity, double time) {
		double speed = velocity.norm();
		double newSpeed = speed + FRICTION * time;
		return velocity.normalize().mul(newSpeed);
	}

	public static boolean stillMoving(MyVector velocity, double time) {
		double speed = velocity.norm();
		return speed + FRICTION * time > 0;
	}

	public static double stoppingTime(MyVector velocity) {
		double speed = velocity.norm();
		return speed / (-FRICTION);
	}

	public static double ballIntersectionTime(MyVector pos1, MyVector vel1, MyVector pos2, MyVector vel2, double radius) {
		double D = 2 * radius;

		double speed1 = vel1.norm();
		double speed2 = vel2.norm();

		MyVector nvel1 = vel1.normalize();
		MyVector nvel2 = vel2.normalize();

		double Ax = pos1.getX() - pos2.getX();
		double Bx = 1./2 * FRICTION * (nvel1.getX() - nvel2.getX());
		double Cx = nvel1.getX() * speed1 - nvel2.getX() * speed2;

		double Ay = pos1.getY() - pos2.getY();
		double By = 1./2 * FRICTION * (nvel1.getY() - nvel2.getY());
		double Cy = nvel1.getY() * speed1 - nvel2.getY() * speed2;

		double a = Bx*Bx + By*By; // uz t^4
		double b = 2*Bx*Cx + 2*By*Cy; // uz t^3
		double c = Cx*Cx + 2*Ax*Bx + Cy*Cy + 2*Ay*By; // uz t^2
		double d = 2*Ax*Cx + 2*Ay*Cy; // uz t
		double e = Ax*Ax + Ay*Ay - D*D; // uz 1

		MyComplex[] roots;
		try {
			roots = MathUtil.roots4(a, b, c, d, e);
		} catch (RootException exc) {
			return -1;
		}

		double time = -1;

		// System.out.println(a + " " + b + " " + c + " " + d + " " + e);

		for (MyComplex root : roots) {
			// System.out.println(root);
			if (root.isReal()) {
				double t = root.getX();
				if (MathUtil.doubleZero(t) && MathUtil.doubleLE(vel1.dot(vel2), 0)) {
					continue;
				}
				if (t >= 0 && (time < 0 || t < time)) {
					time = t;
				}
			}
		}

		// System.out.println("---");
		// System.out.println(time);

		double st1 = stoppingTime(vel1);
		double st2 = stoppingTime(vel2);

		if (!MathUtil.doubleZero(st1) && st1 < time && !MathUtil.doubleZero(st2) && st2 < time) {
			return -1;
		}

		if (!MathUtil.doubleZero(st1) && st1 < time) {
			time = ballIntersectionTime(newPosAfterTime(pos1, vel1, st1), newVelAfterTime(vel1, st1),
					newPosAfterTime(pos2, vel2, st1), newVelAfterTime(vel2, st1), radius);
			if (time != -1) {
				time += st1;
			}
		}

		if (!MathUtil.doubleZero(st2) && st2 < time) {
			time = ballIntersectionTime(newPosAfterTime(pos1, vel1, st2), newVelAfterTime(vel1, st2),
					newPosAfterTime(pos2, vel2, st2), newVelAfterTime(vel2, st2), radius);
			if (time != -1) {
				time += st2;
			}
		}

		// System.out.println(time);
		return time;
	}

	public static Resolver cushionHit(final Ball ball, ACushion cushion) {
		MyVector velocity;
		if (cushion.isHorizontal()) {
			velocity = new MyVector(ball.getVelocity().getX(), -ball.getVelocity().getY());
		} else {
			velocity = new MyVector(-ball.getVelocity().getX(), ball.getVelocity().getY());
		}
		double speed = velocity.norm();
		final double newSpeed = speed * BALL2CUSHION_ELOSS;

		final MyVector newVelocity = velocity.normalize().mul(newSpeed);
		ball.setVelocity(newVelocity);

		return new Resolver() {

			@Override
			public void resolve(Map<Integer, Ball> balls) {
				balls.get(ball.getNumber()).setVelocity(newVelocity);
			}
		};
	}

	public static Resolver ballHit(final Ball ball, final Ball hitBall) {
		double speed1 = ball.getVelocity().norm() * BALL2BALL_ELOSS;

		MyVector vel12 = new MyVector();
		MyVector vel11 = new MyVector();
		if (!MathUtil.doubleZero(speed1)) {
			MyVector dir12 = hitBall.getPosition().sub(ball.getPosition()).normalize();
			MyVector dir11 = dir12.findPerpendicular(ball.getVelocity());

			double factor1 = ball.getVelocity().cosAngle(dir12);
			vel12 = dir12.mul(speed1 * factor1);
			vel11 = dir11.mul(speed1 * Math.sqrt(1 - factor1));
		}

		double speed2 = hitBall.getVelocity().norm() * BALL2BALL_ELOSS;

		MyVector vel21 = new MyVector();
		MyVector vel22 = new MyVector();
		if (!MathUtil.doubleZero(speed2)) {
			MyVector dir21 = ball.getPosition().sub(hitBall.getPosition()).normalize();
			MyVector dir22 = dir21.findPerpendicular(hitBall.getVelocity());

			double factor2 = hitBall.getVelocity().cosAngle(dir21);
			vel21 = dir21.mul(speed2 * factor2);
			vel22 = dir22.mul(speed2 * Math.sqrt(1 - factor2));
		}

		final MyVector newVel1 = vel11.add(vel21);
		final MyVector newVel2 = vel12.add(vel22);

		ball.setVelocity(newVel1);
		hitBall.setVelocity(newVel2);

		return new Resolver() {

			@Override
			public void resolve(Map<Integer, Ball> balls) {
				balls.get(ball.getNumber()).setVelocity(newVel1);
				balls.get(hitBall.getNumber()).setVelocity(newVel2);
			}
		};
	}

}
