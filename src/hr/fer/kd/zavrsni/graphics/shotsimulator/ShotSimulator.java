package hr.fer.kd.zavrsni.graphics.shotsimulator;

import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.WakeupOnElapsedFrames;


public class ShotSimulator extends Behavior {

	private static ShotSimulator simulator;

	public static void init(BallSetContainer bsc) {
		simulator = new ShotSimulator(bsc);
	}

	public static ShotSimulator getSimulator() {
		if (simulator == null) {
			throw new NullPointerException("Simulator not initialized!");
		}
		return simulator;
	}

	private WakeupOnElapsedFrames criteria;

	private BallSetContainer ballSetContainer;

	public ShotSimulator(BallSetContainer bc) {
		ballSetContainer = bc;
		setSchedulingBounds(new BoundingSphere());
		criteria = new WakeupOnElapsedFrames(0);
	}

	@Override
	public void initialize() {
		wakeupOn(criteria);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void processStimulus(Enumeration c) {
		ballSetContainer.updateBallPositions();
		wakeupOn(criteria);
	}

}
