package hr.fer.kd.zavrsni.ball_detection;

import hr.fer.kd.zavrsni.ball_detection.balls.BallDetection;
import hr.fer.kd.zavrsni.ball_detection.balls.ICVBallsDetectedAction;
import hr.fer.kd.zavrsni.ball_detection.balls.model.ICVBall;
import hr.fer.kd.zavrsni.ball_detection.table.ICVTableDetectedAction;
import hr.fer.kd.zavrsni.ball_detection.table.TableDetection;
import hr.fer.kd.zavrsni.ball_detection.table.model.ICVTable;

import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class PoolGameCV {

	private ICVPoolGameDetectedAction action;

	public PoolGameCV() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public void detectPoolGame(String filename, ICVPoolGameDetectedAction action, final ICVPresentation presentation) {
		this.action = action;

		Mat image = loadImage(filename);

		new TableDetection().detectTable(image, new ICVTableDetectedAction() {

			@Override
			public void actionPerformed(ICVTable table) {
				tableDetected(table, presentation);
			}
		}, presentation);
	}

	private void tableDetected(final ICVTable table, ICVPresentation presentation) {
		new BallDetection().detectBalls(table, new ICVBallsDetectedAction() {

			@Override
			public void actionPerformed(Map<Integer, ICVBall> balls) {
				action.actionPerformed(table, balls);
			}
		}, presentation);
	}

	private Mat loadImage(String filename) {
		Mat image = Highgui.imread(filename, Highgui.CV_LOAD_IMAGE_COLOR);
		if (image.empty()) {
			throw new RuntimeException("Can't load the image.");
		}
		return image;
	}

}
