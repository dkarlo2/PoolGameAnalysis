package hr.fer.kd.zavrsni.shot_analysis.simulation;

import hr.fer.kd.mymath.MathUtil;
import hr.fer.kd.mymath.model.MyLine;
import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.graphics.GraphicsUtil;
import hr.fer.kd.zavrsni.graphics.painter.Circle;
import hr.fer.kd.zavrsni.graphics.painter.Line;
import hr.fer.kd.zavrsni.model.balls.Ball;
import hr.fer.kd.zavrsni.model.table.Cushion.CushionID;
import hr.fer.kd.zavrsni.model.table.Pocket;
import hr.fer.kd.zavrsni.model.table.Pocket.PocketID;
import hr.fer.kd.zavrsni.shot_analysis.SpeedCalculations;
import hr.fer.kd.zavrsni.shot_analysis.model.ACushion;
import hr.fer.kd.zavrsni.shot_analysis.simulation.BreakPoint.BreakPointID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.media.j3d.LineAttributes;
import javax.swing.SwingWorker;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

public class Simulator {

	private Map<Integer, Ball> balls;

	private Map<CushionID, ACushion> cushions;

	private Map<PocketID, Pocket> pockets;

	private int desiredBall = -1;

	public Simulator(Map<Integer, Ball> balls, Map<CushionID, ACushion> cushions, Map<PocketID, Pocket> pockets) {
		super();
		this.balls = balls;
		this.cushions = cushions;
		this.pockets = pockets;
	}

	public List<BreakPoint> findBreakPoints(Ball ball, MyVector velocity, int desiredBall) {
		this.desiredBall = desiredBall;

		List<BreakPoint> breakPoints = new ArrayList<>();

		Map<Integer, Ball> bpBalls = copyBalls(balls);

		BreakPoint startBP = new BreakPoint(ball, 0, BreakPointID.START, velocity);
		breakPoints.add(startBP);
		resolveBreakPoint(bpBalls, startBP);

		Set<Integer> numbers = bpBalls.keySet();
		BreakPoint bp = null;
		while (true) {
			bp = findFirstBreakPoint(bpBalls, cushions, pockets, bp);

			// System.out.println(bp);
			breakPoints.add(bp);

			Map<Integer, MyVector> ballPositions = new HashMap<>();
			for (int number : numbers) {
				if (bpBalls.get(number).isPotted()) {
					continue;
				}
				ballPositions.put(number, bpBalls.get(number).getPosition());
			}

			setBallsToBreakPoint(bpBalls, ballPositions, bp, cushions, desiredBall);
			resolveBreakPoint(bpBalls, bp);

			if (bp.getBpid() == BreakPointID.ALL_STOPPED) {
				break;
			}
		}

		return breakPoints;
	}

	public void startSimulating(final List<BreakPoint> breakPoints) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				try {
					for (BreakPoint bp : breakPoints) {
						simulateToBreakPoint(bp);
						bp.getResolver().resolve(balls);
						System.out.println("Break point!");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
				System.out.println("Gotova simulacija.");
				return null;
			}
		};

		worker.execute();
	}

	private static BreakPoint findFirstBreakPoint(Map<Integer, Ball> balls, Map<CushionID, ACushion> cushions,
			Map<PocketID, Pocket> pockets, BreakPoint lastBreakPoint) {
		TreeMap<Double, BreakPoint> breakPoints = new TreeMap<>();

		Set<Integer> numbers = balls.keySet();
		for (int number : numbers) {
			if (balls.get(number).isPotted()) {
				continue;
			}
			breakPoints.putAll(findBallsIntersections(balls, balls.get(number)));
			breakPoints.putAll(findCushionIntersections(balls, cushions, balls.get(number)));
			breakPoints.putAll(findBallPotted(balls.get(number), cushions, pockets));
		}

		if (lastBreakPoint != null) {
			while (!breakPoints.isEmpty() && breakPoints.firstEntry().getValue().isSameAs(lastBreakPoint)) {
				breakPoints.remove(breakPoints.firstKey());
			}
		}

		if (breakPoints.isEmpty()) {
			double time = 0;
			for (int number : numbers) {
				if (balls.get(number).isPotted()) {
					continue;
				}
				time = Math.max(time, SpeedCalculations.stoppingTime(balls.get(number).getVelocity()));
			}
			return new BreakPoint(null, time, BreakPointID.ALL_STOPPED, null);
		}

		return breakPoints.firstEntry().getValue();
	}

	private static Map<Double, BreakPoint> findBallsIntersections(Map<Integer, Ball> balls, Ball ball) {
		Map<Double, BreakPoint> breakPoints = new HashMap<>();

		Set<Integer> numbers = balls.keySet();
		for (int number : numbers) {
			if (balls.get(number).isPotted()) {
				continue;
			}
			if (ball.getNumber() == number) {
				continue;
			}

			Ball b = balls.get(number);

			// System.out.println(ball.getNumber() + " " + b.getNumber());
			double time = SpeedCalculations.ballIntersectionTime(ball.getPosition(), ball.getVelocity(),
					b.getPosition(), b.getVelocity(), ball.getRadius());

			if (time >= 0) {
				breakPoints.put(time, new BreakPoint(ball, time, BreakPointID.BALL_HIT, b));
			}
		}

		return breakPoints;
	}

	private static Map<Double, BreakPoint> findCushionIntersections(Map<Integer, Ball> balls,
			Map<CushionID, ACushion> cushions, Ball ball) {
		Map<Double, BreakPoint> breakPoints = new HashMap<>();

		Set<CushionID> cids = cushions.keySet();
		for (CushionID cid : cids) {
			ACushion c = cushions.get(cid);
			MyVector[] cPoints = c.getPoints();
			MyLine cLine = new MyLine(cPoints[0], cPoints[1]);
			MyLine ballLine = new MyLine(ball.getPosition(), ball.getPosition().add(ball.getVelocity()));
			MyVector intersection = MathUtil.findIntersection(ballLine, cLine);

			if (intersection != null && MathUtil.between(intersection, cLine)
					&& MathUtil.after(intersection, ballLine)) {
				double time = SpeedCalculations.startToEndTime(ball.getPosition(), ball.getVelocity(), intersection);
				if (MathUtil.doubleGE(time, 0)) {
					breakPoints.put(time, new BreakPoint(ball, time, BreakPointID.CUSHION_HIT, c));
				}
			}
		}

		return breakPoints;
	}

	private static Map<Double, BreakPoint> findBallPotted(Ball ball, Map<CushionID, ACushion> cushions,
			Map<PocketID, Pocket> pockets) {
		Map<Double, BreakPoint> breakPoints = new HashMap<>();

		MyLine ballLine = new MyLine(ball.getPosition(), ball.getPosition().add(ball.getVelocity()));

		ACushion c1, c2;
		double level;
		MyVector intersection;

		// right
		c1 = cushions.get(CushionID.R);
		level = c1.getLevel();
		intersection = MathUtil.findIntersectionX(ballLine, level);

		if (intersection != null) {
			double y1 = Math.min(c1.getPoint1(), c1.getPoint2());
			double y2 = Math.max(c1.getPoint1(), c1.getPoint2());
			double time = SpeedCalculations.startToEndTime(ball.getPosition(), ball.getVelocity(), intersection);
			if (MathUtil.doubleGE(time, 0)) {
				if (intersection.getY() < y1) {
					breakPoints.put(time,
							new BreakPoint(ball, time, BreakPointID.BALL_POTTED, pockets.get(PocketID.UR)));
				} else if (intersection.getY() > y2) {
					breakPoints.put(time,
							new BreakPoint(ball, time, BreakPointID.BALL_POTTED, pockets.get(PocketID.DR)));
				}
			}
		}

		// left
		c1 = cushions.get(CushionID.L);
		level = c1.getLevel();
		intersection = MathUtil.findIntersectionX(ballLine, level);

		if (intersection != null) {
			double y1 = Math.min(c1.getPoint1(), c1.getPoint2());
			double y2 = Math.max(c1.getPoint1(), c1.getPoint2());
			double time = SpeedCalculations.startToEndTime(ball.getPosition(), ball.getVelocity(), intersection);
			if (MathUtil.doubleGE(time, 0)) {
				if (intersection.getY() < y1) {
					breakPoints.put(time,
							new BreakPoint(ball, time, BreakPointID.BALL_POTTED, pockets.get(PocketID.UL)));
				} else if (intersection.getY() > y2) {
					breakPoints.put(time,
							new BreakPoint(ball, time, BreakPointID.BALL_POTTED, pockets.get(PocketID.DL)));
				}
			}
		}

		// up
		c1 = cushions.get(CushionID.UL);
		c2 = cushions.get(CushionID.UR);
		level = c1.getLevel();
		intersection = MathUtil.findIntersectionY(ballLine, level);

		if (intersection != null) {
			double x1 = Math.min(c1.getPoint1(), c1.getPoint2());
			double x2 = Math.max(c1.getPoint1(), c1.getPoint2());
			double x3 = Math.min(c2.getPoint1(), c2.getPoint2());
			double x4 = Math.max(c2.getPoint1(), c2.getPoint2());
			double time = SpeedCalculations.startToEndTime(ball.getPosition(), ball.getVelocity(), intersection);
			if (MathUtil.doubleGE(time, 0)) {
				if (intersection.getX() < x1) {
					breakPoints.put(time,
							new BreakPoint(ball, time, BreakPointID.BALL_POTTED, pockets.get(PocketID.UL)));
				}
				if (intersection.getX() > x2 && intersection.getX() < x3) {
					breakPoints.put(time,
							new BreakPoint(ball, time, BreakPointID.BALL_POTTED, pockets.get(PocketID.UM)));
				}
				if (intersection.getX() > x4) {
					breakPoints.put(time,
							new BreakPoint(ball, time, BreakPointID.BALL_POTTED, pockets.get(PocketID.UR)));
				}
			}
		}

		// down
		c1 = cushions.get(CushionID.DL);
		c2 = cushions.get(CushionID.DR);
		level = c1.getLevel();
		intersection = MathUtil.findIntersectionY(ballLine, level);

		if (intersection != null) {
			double x1 = Math.min(c1.getPoint1(), c1.getPoint2());
			double x2 = Math.max(c1.getPoint1(), c1.getPoint2());
			double x3 = Math.min(c2.getPoint1(), c2.getPoint2());
			double x4 = Math.max(c2.getPoint1(), c2.getPoint2());
			double time = SpeedCalculations.startToEndTime(ball.getPosition(), ball.getVelocity(), intersection);
			if (MathUtil.doubleGE(time, 0)) {
				if (intersection.getX() < x1) {
					breakPoints.put(time,
							new BreakPoint(ball, time, BreakPointID.BALL_POTTED, pockets.get(PocketID.DL)));
				}
				if (intersection.getX() > x2 && intersection.getX() < x3) {
					breakPoints.put(time,
							new BreakPoint(ball, time, BreakPointID.BALL_POTTED, pockets.get(PocketID.DM)));
				}
				if (intersection.getX() > x4) {
					breakPoints.put(time,
							new BreakPoint(ball, time, BreakPointID.BALL_POTTED, pockets.get(PocketID.DR)));
				}
			}
		}

		return breakPoints;
	}

	private void simulateToBreakPoint(BreakPoint bp) {
		Map<Integer, MyVector> ballPositions = new HashMap<>();
		Set<Integer> numbers = balls.keySet();
		for (int number : numbers) {
			if (balls.get(number).isPotted()) {
				continue;
			}
			ballPositions.put(number, balls.get(number).getPosition());
		}

		long startTime = System.currentTimeMillis();
		while (true) {
			double elapsedTime = (System.currentTimeMillis() - startTime) / 1000.;
			if (elapsedTime >= bp.getTime()) {
				setBallsToBreakPoint(balls, ballPositions, bp, cushions, desiredBall);
				return;
			}

			for (int number : numbers) {
				if (balls.get(number).isPotted()) {
					continue;
				}
				Ball ball = balls.get(number);
				MyVector velocity = ball.getVelocity();
				MyVector pos = ballPositions.get(number);
				if (SpeedCalculations.stillMoving(velocity, elapsedTime)) {
					ball.setPosition(SpeedCalculations.newPosAfterTime(pos, velocity, elapsedTime));
				} else {
					double time = SpeedCalculations.stoppingTime(velocity);
					ball.setPosition(SpeedCalculations.newPosAfterTime(pos, velocity, time));
				}
			}
		}
	}

	private static void setBallsToBreakPoint(Map<Integer, Ball> balls, Map<Integer, MyVector> ballPositions,
			BreakPoint bp, Map<CushionID, ACushion> cushions, int desiredBall) {
		Set<Integer> numbers = balls.keySet();
		for (int number : numbers) {
			if (balls.get(number).isPotted()) {
				continue;
			}
			Ball ball = balls.get(number);
			double time = bp.getTime();
			if (!SpeedCalculations.stillMoving(ball.getVelocity(), time)) {
				time = SpeedCalculations.stoppingTime(ball.getVelocity());
			}
			MyVector pos = ballPositions.get(number);

			MyVector newPos = SpeedCalculations.newPosAfterTime(pos, ball.getVelocity(), time);
			newPos = fixForCushions(newPos, cushions);
			newPos = fixForBalls(newPos, ball, balls);
			ball.setPosition(newPos);

			ball.setVelocity(SpeedCalculations.newVelAfterTime(ball.getVelocity(), time));

			if (!pos.equals(newPos)) {
				Line line = new Line(new Point3d(GraphicsUtil.getVector3d(pos, 0)),
						new Point3d(GraphicsUtil.getVector3d(newPos, 0)));
				line.setLinePattern(LineAttributes.PATTERN_DASH);
				bp.addShape(line);

				if (bp.getBall() == null ||
						bp.getBpid() == BreakPointID.BALL_HIT && (number == bp.getBall().getNumber()
						|| number == ((Ball)bp.getCollObject()).getNumber())
						|| MathUtil.doubleZero(ball.getVelocity().norm())
						|| bp.getBpid() == BreakPointID.BALL_POTTED && number == bp.getBall().getNumber()
						|| bp.getBpid() == BreakPointID.CUSHION_HIT && number == bp.getBall().getNumber()) {

					Circle circle = new Circle(ball.getRadius() * GraphicsUtil.getGF());
					if (bp.getBpid() == BreakPointID.BALL_POTTED && number == bp.getBall().getNumber()) {
						if (number == 0) {
							circle.setColor(new Color3f(1, 0, 0));
						} else if (number == desiredBall) {
							circle.setColor(new Color3f(0, 1, 0));
						} else {
							circle.setColor(new Color3f(1, 1, 0));
						}
					}
					circle.updateCircle(new Point3d(GraphicsUtil.getVector3d(newPos, 0)));
					bp.addShape(circle);
				}
			}
		}
	}

	private static MyVector fixForCushions(MyVector pos, Map<CushionID, ACushion> cushions) {
		MyVector fixedPos = pos;

		Set<CushionID> cids = cushions.keySet();
		for (CushionID cid : cids) {
			ACushion c = cushions.get(cid);
			if (c.isHorizontal()) {
				if (c.getLevel() < 0) {
					fixedPos = new MyVector(fixedPos.getX(), Math.max(fixedPos.getY(), c.getLevel()));
				} else {
					fixedPos = new MyVector(fixedPos.getX(), Math.min(fixedPos.getY(), c.getLevel()));
				}
			} else {
				if (c.getLevel() < 0) {
					fixedPos = new MyVector(Math.max(fixedPos.getX(), c.getLevel()), fixedPos.getY());
				} else {
					fixedPos = new MyVector(Math.min(fixedPos.getX(), c.getLevel()), fixedPos.getY());
				}
			}
		}

		return fixedPos;
	}

	private static MyVector fixForBalls(MyVector pos, Ball ball, Map<Integer, Ball> balls) {
		MyVector fixedPos = pos;

		for (int number : balls.keySet()) {
			if (balls.get(number).isPotted()) {
				continue;
			}
			if (number == ball.getNumber()) {
				continue;
			}

			Ball b = balls.get(number);
			double r = ball.getRadius();

			double distance2 = MathUtil.distance2(pos, b.getPosition());
			if (distance2 < r * r * 4) {
				MyVector dir = pos.sub(b.getPosition()).normalize().mul(2 * r);
				fixedPos = b.getPosition().add(dir);
			}
		}

		return fixedPos;
	}

	private static void resolveBreakPoint(Map<Integer, Ball> balls, BreakPoint bp) {
		switch (bp.getBpid()) {
		case START:
			bp.setResolver(started(balls.get(bp.getBall().getNumber()), (MyVector) bp.getCollObject()));
			break;
		case CUSHION_HIT:
			bp.setResolver(SpeedCalculations.cushionHit(
					balls.get(bp.getBall().getNumber()), (ACushion) bp.getCollObject()));
			break;
		case BALL_HIT:
			bp.setResolver(SpeedCalculations.ballHit(balls.get(bp.getBall().getNumber()),
					balls.get(((Ball) bp.getCollObject()).getNumber())));
			break;
		case BALL_POTTED:
			bp.setResolver(ballPotted(balls, balls.get(bp.getBall().getNumber())));
			break;
		case ALL_STOPPED:
			bp.setResolver(allStopped(balls));
			break;
		}
	}

	private static Resolver started(final Ball ball, final MyVector velocity) {
		ball.setVelocity(velocity);

		return new Resolver() {

			@Override
			public void resolve(Map<Integer, Ball> balls) {
				balls.get(ball.getNumber()).setVelocity(velocity);
			}
		};
	}

	private static Resolver ballPotted(Map<Integer, Ball> balls, final Ball ball) {
		ball.setPotted(true);

		return new Resolver() {

			@Override
			public void resolve(Map<Integer, Ball> balls) {
				balls.get(ball.getNumber()).setPotted(true);
			}
		};
	}

	private static Resolver allStopped(Map<Integer, Ball> balls) {
		Set<Integer> numbers = balls.keySet();
		for (int number : numbers) {
			if (balls.get(number).isPotted()) {
				continue;
			}
			balls.get(number).setVelocity(new MyVector());
		}

		return new Resolver() {

			@Override
			public void resolve(Map<Integer, Ball> balls) {
				Set<Integer> numbers = balls.keySet();
				for (int number : numbers) {
					if (balls.get(number).isPotted()) {
						continue;
					}
					balls.get(number).setVelocity(new MyVector());
				}
			}
		};
	}

	private Map<Integer, Ball> copyBalls(Map<Integer, Ball> balls) {
		Map<Integer, Ball> newBalls = new HashMap<>();

		Set<Integer> numbers = balls.keySet();
		for (int number : numbers) {
			if (balls.get(number).isPotted()) {
				continue;
			}
			newBalls.put(number, new Ball(balls.get(number), true));
		}

		return newBalls;
	}

}
