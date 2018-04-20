package hr.fer.kd.zavrsni.ball_detection.cv;

import java.util.Arrays;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

public class PerspectiveTransformer {

	private static final double CUSHION_SIZE_FACTOR = 0.03;

	private Mat imageWarped;

	private Point[] dstPoints;

	public PerspectiveTransformer(Mat image, Point[] points) {
		double tableW = Math.max(points[1].x, points[2].x) - Math.min(points[3].x, points[0].x);
		double tableH = Math.max(points[2].y, points[3].y) - Math.max(points[0].y, points[1].y);
		double tableX = tableW*CUSHION_SIZE_FACTOR;
		double tableY = tableH*CUSHION_SIZE_FACTOR;

		dstPoints = new Point[]{new Point(tableX, tableY), new Point(tableW+tableX, tableY),
				new Point(tableW+tableX, tableH+tableY), new Point(tableX, tableH+tableY)};

		Mat perspective = Imgproc.getPerspectiveTransform(
				Converters.vector_Point2f_to_Mat(Arrays.asList(points)),
				Converters.vector_Point2f_to_Mat(Arrays.asList(dstPoints)));

		imageWarped = new Mat();
		Imgproc.warpPerspective(image, imageWarped, perspective, new Size(tableW + 2*tableX, tableH + 2*tableY));
	}

	public Mat getImage() {
		return imageWarped;
	}

	public Point[] getPoints() {
		return CVUtil.copy4Points(dstPoints);
	}

}
