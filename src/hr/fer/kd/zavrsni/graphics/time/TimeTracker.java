package hr.fer.kd.zavrsni.graphics.time;

public class TimeTracker {

	private long startTime;
	private long time;

	public TimeTracker() {
		time = System.currentTimeMillis();
		startTime = time;
	}

	public double getPassedSeconds() {
		long newTime = System.currentTimeMillis();
		long passed = newTime - time;
		time = newTime;

		return passed / 1000.;
	}

	public double fromStart() {
		long newTime = System.currentTimeMillis();
		long passed = newTime - startTime;

		return passed / 1000.;
	}

}
