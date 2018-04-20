package hr.fer.kd.zavrsni.ball_detection.cv.balls;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.ball_detection.cv.CVUtil;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class BallDetector {

	private static final Scalar[] WHITE_COLOR1 = new Scalar[]{new Scalar(20, 0, 150), new Scalar(55, 150, 255)};
	private static final Scalar[] WHITE_COLOR2 = new Scalar[]{new Scalar(0, 0, 230), new Scalar(179, 80, 255)};
	private static final Scalar[] TABLE_COLOR = new Scalar[]{new Scalar(75, 20, 20), new Scalar(140, 205, 200)};

	private static double CIRCLE_PART = 0.1;

	private Mat image;

	private double colorPart;
	private double bluePart; // TODO potrebno?
	private Scalar color;
	private MyVector position;
	private double radius;

	/**
	 * @param im Ball image.
	 * @param pos Ball position relative to the table. (0, 0) is the table corner.
	 */
	public BallDetector(Mat im, MyVector pos) {
		image = im;
		position = pos;

		detect();
	}

	private void detect() {
		Mat mask = getMask();

		color = Core.mean(image, mask);

		Mat imageMasked = new Mat();
		Core.bitwise_and(image, image, imageMasked, mask);

		Mat circles = detectNumberCircle(image);
		drawCircles(circles, image, new Scalar(0, 0, 255));

		if (circles.cols() > 0) {
			colorPart += CIRCLE_PART;
		}
	}

	private Mat getMask() {
		Mat maskWhite = CVUtil.extractColorMask(image, WHITE_COLOR1[0], WHITE_COLOR1[1]);
		Core.bitwise_or(CVUtil.extractColorMask(image, WHITE_COLOR2[0], WHITE_COLOR2[1]), maskWhite, maskWhite);
		Core.bitwise_not(maskWhite, maskWhite);

		Mat maskBlack = new Mat();
		Core.inRange(image, new Scalar(0), new Scalar(0), maskBlack);
		Core.bitwise_not(maskBlack, maskBlack);

		Mat maskBlue = CVUtil.extractColorMask(image, TABLE_COLOR[0], TABLE_COLOR[1]);

		bluePart = (double)Core.countNonZero(maskBlue) / Core.countNonZero(maskBlack);

		Core.bitwise_not(maskBlue, maskBlue);

		Mat maskBall = new Mat();
		Core.bitwise_and(maskBlack, maskBlue, maskBall);

		Mat mask = new Mat();
		Core.bitwise_and(maskWhite, maskBall, mask);

		colorPart = (double)Core.countNonZero(mask) / Core.countNonZero(maskBall);

		radius = Math.sqrt((double)Core.countNonZero(maskBall) / Math.PI); // TODO provjera

		return mask;
	}

	private static Mat detectNumberCircle(Mat image) {
		// TODO steka

		Mat imageGray = new Mat();
		Imgproc.cvtColor(image, imageGray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(imageGray, imageGray, new Size(3, 3), 2, 2);

		Mat circles = new Mat();
		Imgproc.HoughCircles(imageGray, circles, Imgproc.CV_HOUGH_GRADIENT, 1.2, 40, 30, 15, 5, 10);

		return circles;
	}

	private static void drawCircles(Mat circles, Mat image, Scalar color) {
		int cols = circles.cols();
		for (int i = 0; i < cols; i++) {
			double vCircle[] = circles.get(0,i);
			//System.out.println(vCircle[0] + " " + vCircle[1] + " " + vCircle[2]);

			Point center = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
			int radius = (int)Math.round(vCircle[2]);

			Core.circle(image, center, radius, color, -1);
		}
		//System.out.println(cols);
	}

	public double getColorPart() {
		return colorPart;
	}

	public double getBluePart() {
		return bluePart;
	}

	public Scalar getColor() {
		return color;
	}

	public MyVector getPosition() {
		return position;
	}

	public double getRadius() {
		return radius;
	}

}
