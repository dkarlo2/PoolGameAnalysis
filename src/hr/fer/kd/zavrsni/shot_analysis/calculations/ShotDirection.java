package hr.fer.kd.zavrsni.shot_analysis.calculations;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.model.balls.Ball;
import hr.fer.kd.zavrsni.model.table.Cushion.CushionID;
import hr.fer.kd.zavrsni.model.table.Pocket;
import hr.fer.kd.zavrsni.shot_analysis.calculations.PocketMirroring.PocketMirrored;
import hr.fer.kd.zavrsni.shot_analysis.model.ACushion;
import hr.fer.kd.zavrsni.shot_analysis.simulation.BreakPoint;
import hr.fer.kd.zavrsni.shot_analysis.simulation.BreakPoint.BreakPointID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShotDirection {

	private static final int MAX_BALL_CUSHIONS = 3;

	private static final int MAX_WHITE_CUSHIONS = 3;

	private Ball white;

	private Ball ball;

	private Pocket pocket;

	private Map<CushionID, ACushion> cushions;

	public ShotDirection(Ball white, Ball ball, Pocket pocket,
			Map<CushionID, ACushion> cushions) {
		super();
		this.white = white;
		this.ball = ball;
		this.pocket = pocket;
		this.cushions = cushions;
	}

	public ShotDirection(Ball ball, Pocket pocket) {
		super();
		this.ball = ball;
		this.pocket = pocket;
	}

	public List<ShotAndBreakPoints> calculate() {
		List<ShotAndBreakPoints> shots = new ArrayList<>();

		for (int ballCushions = 0; ballCushions <= MAX_BALL_CUSHIONS; ballCushions++) {
			List<PocketMirrored> pockets = new PocketMirroring(pocket, cushions).getPoints(ballCushions);

			for (PocketMirrored pocket : pockets) {
				MyVector velocity = pocket.getPoint().sub(white.getPosition()).normalize();

				List<BreakPoint> bps = new ArrayList<>();
				bps.add(new BreakPoint(white, BreakPointID.START, velocity));
				for (ACushion c : pocket.getCushions()) {
					bps.add(new BreakPoint(white, BreakPointID.CUSHION_HIT, c));
				}
				bps.add(new BreakPoint(white, BreakPointID.BALL_POTTED, this.pocket));
				bps.add(new BreakPoint(BreakPointID.ALL_STOPPED));

				shots.add(new ShotAndBreakPoints(velocity, bps));
			}
		}

		return shots;
	}

	public static class ShotAndBreakPoints {

		private MyVector velocity;

		private List<BreakPoint> breakPoints;

		public ShotAndBreakPoints(MyVector velocity, List<BreakPoint> breakPoints) {
			super();
			this.velocity = velocity;
			this.breakPoints = breakPoints;
		}

		public MyVector getVelocity() {
			return velocity;
		}

		public List<BreakPoint> getBreakPoints() {
			return breakPoints;
		}

	}

}
