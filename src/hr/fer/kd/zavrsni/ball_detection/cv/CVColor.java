package hr.fer.kd.zavrsni.ball_detection.cv;

import org.opencv.core.Scalar;

public class CVColor {

	public static final CVColor WHITE = new CVColor(255, 255, 255);
	public static final CVColor BLACK = new CVColor(0, 0, 0);

	private int red;

	private int green;

	private int blue;

	public CVColor(Scalar scalar) {
		this((int)scalar.val[0], (int)scalar.val[1], (int)scalar.val[2]);
	}

	public CVColor(int red, int green, int blue) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

}
