package hr.fer.kd.zavrsni.ball_detection.table.model;

import hr.fer.kd.zavrsni.ball_detection.cv.CVColor;
import hr.fer.kd.zavrsni.ball_detection.cv.CVUtil;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public class CVTable implements ICVTable {

	private Mat image;

	private Mat imageTable;

	private double left;

	private double right;

	private double top;

	private double bottom;

	private CVColor color;

	private Point[] points;

	public CVTable(Mat image, Mat imageTable, Point[] points, CVColor color) {
		super();
		this.image = image;
		this.imageTable = imageTable;
		this.points = points;
		this.color = color;

		left = points[0].x;
		right = points[1].x;
		top = points[0].y;
		bottom = points[3].y;
	}

	@Override
	public Mat getImage() {
		return image;
	}

	@Override
	public Mat getImageTable() {
		return imageTable;
	}

	@Override
	public double getLeft() {
		return left;
	}

	@Override
	public double getRight() {
		return right;
	}

	@Override
	public double getTop() {
		return top;
	}

	@Override
	public double getBottom() {
		return bottom;
	}

	@Override
	public CVColor getColor() {
		return color;
	}

	@Override
	public double getLength() {
		return getRight() - getLeft();
	}

	@Override
	public double getWidth() {
		return getBottom() - getTop();
	}

	@Override
	public Point[] getPoints() {
		return CVUtil.copy4Points(points);
	}

}
