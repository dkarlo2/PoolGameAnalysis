package hr.fer.kd.zavrsni.shot_analysis.simulation;

import hr.fer.kd.zavrsni.model.balls.Ball;

import java.util.Map;

public interface Resolver {

	void resolve(Map<Integer, Ball> balls);

}
