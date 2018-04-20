package hr.fer.kd.zavrsni.ball_detection.balls;

import hr.fer.kd.zavrsni.ball_detection.balls.model.ICVBall;

import java.util.Map;

public interface ICVBallsDetectedAction {

	void actionPerformed(Map<Integer, ICVBall> balls);

}
