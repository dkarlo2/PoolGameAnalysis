package hr.fer.kd.zavrsni.ball_detection.cv.balls;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.ball_detection.ICVPresentation;
import hr.fer.kd.zavrsni.ball_detection.balls.model.ICVBall;
import hr.fer.kd.zavrsni.ball_detection.cv.CVUtil;
import hr.fer.kd.zavrsni.ball_detection.table.model.ICVTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class BallsDetector {

	private static final double BORDER_TRESH = 1;
	private static final double DISTANCE2_TRESH = 400;

	private static final double BALL_AREA_MIN = 1000;
	private static final double BALL_AREA_MAX = 2000;

	private ICVTable table;

	private ICVPresentation presentation;

	public BallsDetector(ICVTable table, ICVPresentation presentation) {
		this.table = table;
		this.presentation = presentation;
	}

	public Map<Integer, ICVBall> detectBalls() {
		Mat circles = detectCircles(table.getImageTable());

		List<MatOfPoint> contours = findContours(table.getImageTable());

		BallSet ballSet = new BallSet(table.getLength(), table.getWidth());

		Mat image = table.getImage();

		Mat imageDisplay = new Mat(image.rows(), image.cols(), image.type());
		image.copyTo(imageDisplay);

		for (int i = 0; i < circles.cols(); i++) {
			double vCircle[] = circles.get(0,i);
			Point center = new Point(vCircle[0], vCircle[1]);
			double radius = vCircle[2];

			if (inBorders(center, radius)) {
				int index = findMatchingContour(contours, center);

				BallDetector cvBall = detectBall(image, center, radius, contours, index);

				//JOptionPane.showMessageDialog(this, cvBall);
				ballSet.add(cvBall);

				if (index != -1) {
					Imgproc.drawContours(imageDisplay, contours, index, new Scalar(0, 255, 0), 2);
					contours.remove(index);
				}

				Core.circle(imageDisplay, center, (int)radius, new Scalar(0, 0, 255), 2);
			}

		}

		System.out.println("Contours left: " + contours.size());

		for (int i = 0; i < contours.size(); i++) {
			BallDetector cvBall = detectBall(image, contours, i);

			//JOptionPane.showMessageDialog(this, cvBall);
			ballSet.add(cvBall);

			Imgproc.drawContours(imageDisplay, contours, i, new Scalar(0, 255, 0), 2);
		}

		System.out.println("Balls: " + ballSet.size());

		presentation.setImage(imageDisplay);

		return ballSet.calculatePositions();
	}

	private int findMatchingContour(List<MatOfPoint> contours, Point center) {
		for (int i = 0; i < contours.size(); i++) {
			MatOfPoint contour = contours.get(i);

			Rect rect = Imgproc.boundingRect(contour);

			if (CVUtil.distance2(new Point(rect.x + rect.width/2, rect.y + rect.height/2), center)
					< DISTANCE2_TRESH) {
				return i;
			}
		}
		return -1;
	}

	private static List<MatOfPoint> findContours(Mat image) {
		Mat imageGray = new Mat();
		Imgproc.cvtColor(image, imageGray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(imageGray, imageGray, new Size(3, 3), 2);

		Mat edges = new Mat();
		Imgproc.Canny(imageGray, edges, 100, 50);

		List<MatOfPoint> allContours = new ArrayList<>();
		Imgproc.findContours(edges, allContours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

		List<MatOfPoint> contours = new ArrayList<>();

		Set<Rect> usedRects = new HashSet<>();
		for (int i = 0; i < allContours.size(); i++) {
			MatOfPoint contour = allContours.get(i);

			double area = Imgproc.contourArea(contour);
			Rect rect = Imgproc.boundingRect(contour);

			if (area > BALL_AREA_MIN && area < BALL_AREA_MAX && !usedRects.contains(rect)) {
				System.out.println("Contour: " + area + " " + rect);

				contours.add(contour);
				usedRects.add(rect);
			}
		}

		return contours;
	}

	private BallDetector detectBall(Mat image, List<MatOfPoint> contours, int index) {
		Mat mask = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
		Imgproc.drawContours(mask, contours, index, new Scalar(255), -1);

		Rect rect = Imgproc.boundingRect(contours.get(index));

		return getBall(image, mask, rect);
	}

	private BallDetector detectBall(Mat image, Point center, double radius, List<MatOfPoint> contours, int index) {
		int x = (int)(center.x - radius);
		int y = (int)(center.y - radius);
		int width = (int)(radius * 2);
		int height = width;

		Rect rect = new Rect(x, y, width, height);

		Mat mask = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
		Core.circle(mask, center, (int)radius, new Scalar(255), -1);

		if (index != -1) {
			Mat contourMask = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
			Imgproc.drawContours(contourMask, contours, index, new Scalar(255), -1);

			Core.bitwise_or(mask, contourMask, mask);

			Rect contourRect = Imgproc.boundingRect(contours.get(index));

			Rect newRect = new Rect();
			newRect.x = Math.min(rect.x, contourRect.x);
			newRect.y = Math.min(rect.y, contourRect.y);
			newRect.width = Math.max(rect.x + rect.width, contourRect.x + contourRect.width) - newRect.x;
			newRect.height = Math.max(rect.y + rect.height, contourRect.y + contourRect.height) - newRect.y;

			rect = newRect;
		} else {
			System.out.println("Contour not found for " + center + ".");
		}

		return getBall(image, mask, rect);
	}

	private BallDetector getBall(Mat image, Mat mask, Rect rect) {
		Mat imageMasked = new Mat();
		Core.bitwise_and(image, image, imageMasked, mask);

		Mat imageBall = new Mat(imageMasked, rect);

		double centerx = rect.x + rect.width/2 - table.getLeft();
		double centery = rect.y + rect.height/2 - table.getTop();

		BallDetector cvBall = new BallDetector(imageBall, new MyVector(centerx, centery));

		return cvBall;
	}

	private static Mat detectCircles(Mat image) {
		Mat imageGray = new Mat();
		Imgproc.cvtColor(image, imageGray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(imageGray, imageGray, new Size(5, 5), 2, 2);

		Mat circles = new Mat();
		Imgproc.HoughCircles(imageGray, circles, Imgproc.CV_HOUGH_GRADIENT, 2, 30, 150, 30, 15, 25);

		return circles;
	}

	private boolean inBorders(Point center, double radius) {
		return greaterEqual(center.x-radius, table.getLeft())
				&& lessEqual(center.x+radius, table.getRight())
				&& greaterEqual(center.y-radius, table.getTop())
				&& lessEqual(center.y+radius, table.getBottom());
	}

	private static boolean greaterEqual(double x, double y) {
		if (x > y) {
			return true;
		}
		if (Math.abs(x-y) < BORDER_TRESH) {
			return true;
		}
		return false;
	}

	private static boolean lessEqual(double x, double y) {
		return greaterEqual(-x, -y);
	}

}
