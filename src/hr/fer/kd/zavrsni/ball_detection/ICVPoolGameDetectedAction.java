package hr.fer.kd.zavrsni.ball_detection;

import hr.fer.kd.zavrsni.ball_detection.balls.model.ICVBall;
import hr.fer.kd.zavrsni.ball_detection.table.model.ICVTable;

import java.util.Map;

public interface ICVPoolGameDetectedAction {

	void actionPerformed(ICVTable table, Map<Integer, ICVBall> balls);

}
