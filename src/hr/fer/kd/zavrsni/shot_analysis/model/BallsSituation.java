package hr.fer.kd.zavrsni.shot_analysis.model;

import hr.fer.kd.zavrsni.model.balls.Ball;

import java.util.Map;

public class BallsSituation {

	private Map<Integer, Ball> balls;

	private BallsSituation lastSituation;

	public BallsSituation(Map<Integer, Ball> balls, BallsSituation lastSituation) {
		super();
		this.balls = balls;
		this.lastSituation = lastSituation;
	}

	public Map<Integer, Ball> getBalls() {
		return balls;
	}

	public BallsSituation getLastSituation() {
		return lastSituation;
	}

}
