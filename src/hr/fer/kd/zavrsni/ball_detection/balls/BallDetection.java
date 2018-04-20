package hr.fer.kd.zavrsni.ball_detection.balls;

import hr.fer.kd.zavrsni.actions.PGRegularAction;
import hr.fer.kd.zavrsni.ball_detection.ICVPresentation;
import hr.fer.kd.zavrsni.ball_detection.balls.model.ICVBall;
import hr.fer.kd.zavrsni.ball_detection.cv.CVKeys;
import hr.fer.kd.zavrsni.ball_detection.cv.balls.BallsDetector;
import hr.fer.kd.zavrsni.ball_detection.table.model.ICVTable;

import java.util.Map;

public class BallDetection implements IBallDetection {

	private PGRegularAction actionDone;

	private ICVBallsDetectedAction action;

	private Map<Integer, ICVBall> balls;

	@Override
	public void detectBalls(ICVTable table, ICVBallsDetectedAction action, ICVPresentation presentation) {
		this.action = action;

		presentation.newPreview("Detekcija kugli");
		presentation.setImage(table.getImage());
		presentation.drawTableOutline(table.getPoints());

		initActions();

		presentation.addRegularAction(actionDone, CVKeys.ENTER);

		balls = new BallsDetector(table, presentation).detectBalls();
	}

	private void initActions() {
		actionDone = new PGRegularAction("Nacrtaj u 3D!") {

			@Override
			public void actionPerformed() {
				if (balls != null) {
					action.actionPerformed(balls);
				}
			}
		};
	}

}
