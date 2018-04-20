package hr.fer.kd.zavrsni.ball_detection.balls.model;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.ball_detection.cv.CVColor;

public class CVBall implements ICVBall {

	private int number;

	private MyVector position;

	private CVColor color;

	private double score;

	private double radius;

	public CVBall(int number, MyVector position, CVColor color, double score, double radius) {
		super();
		this.number = number;
		this.position = position;
		this.color = color;
		this.score = score;
		this.radius = radius;
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public MyVector getPosition() {
		return position;
	}

	@Override
	public CVColor getColor() {
		return color;
	}

	@Override
	public double getScore() {
		return score;
	}

	@Override
	public double getRadius() {
		return radius;
	}

}
