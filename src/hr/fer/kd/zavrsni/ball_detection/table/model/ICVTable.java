package hr.fer.kd.zavrsni.ball_detection.table.model;

import hr.fer.kd.zavrsni.ball_detection.cv.CVColor;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public interface ICVTable {

	Mat getImage();

	Mat getImageTable();

	double getLeft();

	double getRight();

	double getTop();

	double getBottom();

	Point[] getPoints();

	CVColor getColor();

	double getLength();

	double getWidth();

}
