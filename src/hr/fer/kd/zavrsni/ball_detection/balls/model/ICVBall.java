package hr.fer.kd.zavrsni.ball_detection.balls.model;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.ball_detection.cv.CVColor;

public interface ICVBall {

	int getNumber();

	CVColor getColor();

	MyVector getPosition();

	double getScore();

	double getRadius();

}
