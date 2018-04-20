package hr.fer.kd.zavrsni.ball_detection.balls;

import hr.fer.kd.zavrsni.ball_detection.ICVPresentation;
import hr.fer.kd.zavrsni.ball_detection.table.model.ICVTable;

public interface IBallDetection {

	void detectBalls(ICVTable table, ICVBallsDetectedAction action, ICVPresentation presentation);

}
