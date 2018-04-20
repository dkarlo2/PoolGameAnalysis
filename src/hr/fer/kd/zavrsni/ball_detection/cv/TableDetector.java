package hr.fer.kd.zavrsni.ball_detection.cv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class TableDetector {

	private static final Scalar[] TABLE_COLOR = new Scalar[]{new Scalar(80, 20, 20), new Scalar(105, 220, 200)};

	private static final Size ERODE_SIZE = new Size(6, 6);

	private Mat imageTable;

	private Point[] points;

	public TableDetector(Mat image) {
		imageTable = getTableImage(image);
		points = calculatePoints();
	}

	private Mat getTableImage(Mat image) {
		Mat imageTable = new Mat();
		Mat mask = CVUtil.extractColorMask(image, TABLE_COLOR[0], TABLE_COLOR[1]);

		Core.bitwise_and(image, image, imageTable, mask);

		Imgproc.erode(imageTable, imageTable, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, ERODE_SIZE));
		Imgproc.dilate(imageTable, imageTable, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, ERODE_SIZE));

		return imageTable;
	}

	private Point[] calculatePoints() {
		Mat imageGray = new Mat();
		Imgproc.cvtColor(imageTable, imageGray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(imageGray, imageGray, new Size(11, 11), 2, 2);

		Mat edges = new Mat();
		Imgproc.Canny(imageGray, edges, 40, 20);

		Mat lines = new Mat();
		Imgproc.HoughLines(edges, lines, 1, 5*Math.PI/180, 150, 0, 0);

		int w = imageTable.cols();
		int h = imageTable.rows();
		double rhoL = 0, rhoR = w, rhoU = 0, rhoD = h;

		for (int i = 0; i < lines.cols(); i++) {
			double[] vLine = lines.get(0, i);

			double rho = vLine[0];
			double theta = vLine[1];

			if (Math.abs(theta - Math.PI/2) < 0.1) {
				if (rho < h/2) {
					rhoU = Math.max(rhoU, rho);
				} else {
					rhoD = Math.min(rhoD, rho);
				}
			} else {
				if (rho < w/2) {
					rhoL = Math.max(rhoL, rho);
				} else {
					rhoR = Math.min(rhoR, rho);
				}
			}
		}

		return new Point[]{new Point(rhoL, rhoU), new Point(rhoR, rhoU), new Point(rhoR, rhoD), new Point(rhoL, rhoD)};
	}

	public Mat getImageTable() {
		return imageTable.clone();
	}

	public Point[] getPoints() {
		return CVUtil.copy4Points(points);
	}

	public CVColor getColor() {
		int red = (int) (TABLE_COLOR[0].val[0] + TABLE_COLOR[1].val[0]) / 2;
		int green = (int) (TABLE_COLOR[0].val[1] + TABLE_COLOR[1].val[1]) / 2;
		int blue = (int) (TABLE_COLOR[0].val[2] + TABLE_COLOR[1].val[2]) / 2;
		return new CVColor(red, green, blue);
	}

}
