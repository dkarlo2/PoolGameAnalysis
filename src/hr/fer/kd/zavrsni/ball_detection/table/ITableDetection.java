package hr.fer.kd.zavrsni.ball_detection.table;

import hr.fer.kd.zavrsni.ball_detection.ICVPresentation;

import org.opencv.core.Mat;

public interface ITableDetection {

	void detectTable(Mat image, ICVTableDetectedAction action, ICVPresentation presentation);

}
