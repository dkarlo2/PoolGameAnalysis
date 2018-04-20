package hr.fer.kd.zavrsni.shot_analysis;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.graphics.IUndo;
import hr.fer.kd.zavrsni.graphics.painter.SimplePainter;
import hr.fer.kd.zavrsni.graphics.picker.IPickerListener;
import hr.fer.kd.zavrsni.model.balls.Ball;
import hr.fer.kd.zavrsni.model.table.Cushion.CushionID;
import hr.fer.kd.zavrsni.model.table.Pocket;
import hr.fer.kd.zavrsni.model.table.Pocket.PocketID;
import hr.fer.kd.zavrsni.model.table.Table;
import hr.fer.kd.zavrsni.shot_analysis.calculations.ShotDirection;
import hr.fer.kd.zavrsni.shot_analysis.calculations.ShotDirection.ShotAndBreakPoints;
import hr.fer.kd.zavrsni.shot_analysis.model.ACushion;
import hr.fer.kd.zavrsni.shot_analysis.model.BallsSituation;
import hr.fer.kd.zavrsni.shot_analysis.simulation.BreakPoint;
import hr.fer.kd.zavrsni.shot_analysis.simulation.Simulator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShotAnalysis implements IPickerListener, IUndo {

	private static final double MAX_SPEED = 1;

	private Map<Integer, Ball> balls;

	private Map<CushionID, ACushion> cushions;

	private Map<PocketID, Pocket> pockets;

	private BallsSituation lastSituation;

	public ShotAnalysis(Map<Integer, Ball> balls, Table table) {
		this.balls = balls;

		cushions = new HashMap<>();
		Set<CushionID> cids = table.cushions.keySet();
		for (CushionID cid : cids) {
			cushions.put(cid, new ACushion(table.cushions.get(cid), table.BALL_RADIUS));
		}

		pockets = table.pockets;
	}

	@Override
	public List<BreakPoint> shotPicked(Ball ball, MyVector direction, int speed) {
		double s = speed / 100. * MAX_SPEED;
		return new Simulator(balls, cushions, pockets).findBreakPoints(ball,
				direction.sub(ball.getPosition()).normalize().mul(s), -1);
	}

	@Override
	public LinkedList<List<BreakPoint>> potPicked(Ball ball, Pocket pocket) {
		List<ShotAndBreakPoints> shots = new ShotDirection(balls.get(0), ball, pocket, cushions).calculate();

		LinkedList<List<BreakPoint>> breakPoints = new LinkedList<>();

		for (ShotAndBreakPoints shot : shots) {
			List<BreakPoint> bps = new Simulator(balls, cushions, pockets).findBreakPoints(balls.get(0),
					shot.getVelocity(), ball.getNumber());

			if (BreakPoint.allTheSame(bps, shot.getBreakPoints())) {
				breakPoints.add(bps);
			}
		}

		System.out.println("Number of shots: " + breakPoints.size());

		return breakPoints;
	}

	@Override
	public void simulate(List<BreakPoint> breakPoints) {
		lastSituation = new BallsSituation(copyBalls(balls), lastSituation);

		new Simulator(balls, cushions, pockets).startSimulating(breakPoints);
	}

	@Override
	public void undo() {
		if (lastSituation == null) {
			return;
		}

		SimplePainter.painter.removeShapes();

		Map<Integer, Ball> lastBalls = lastSituation.getBalls();
		Set<Integer> numbers = lastBalls.keySet();
		for (int number : numbers) {
			Ball lastBall = lastBalls.get(number);
			balls.get(number).setAll(lastBall.getPosition(), lastBall.getVelocity(), lastBall.isPotted());
		}
		lastSituation = lastSituation.getLastSituation();
	}

	private Map<Integer, Ball> copyBalls(Map<Integer, Ball> balls) {
		Map<Integer, Ball> newBalls = new HashMap<>();

		Set<Integer> numbers = balls.keySet();
		for (int number : numbers) {
			newBalls.put(number, new Ball(balls.get(number)));
		}

		return newBalls;
	}

}
