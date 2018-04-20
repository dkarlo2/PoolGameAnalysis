package hr.fer.kd.zavrsni.ball_detection.cv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CVUtil {

	private CVUtil() {}

	/**
	 * Udaljenost od tocke do tocke.
	 */
	public static double distance2(Point p1, Point p2) {
		return (p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y);
	}

	/**
	 * Udaljenost od tocke do pravca.
	 */
	public static double distance2(Point p, Point p1, Point p2) {
		double a = p1.y - p2.y;
		double b = p2.x - p1.x;
		double c = p1.x*p2.y - p2.x*p1.y;

		double d = (a*p.x + b*p.y + c);

		return d*d / (a*a + b*b);
	}

	public static Point half(Point p1, Point p2) {
		return new Point((p1.x+p2.x)/2, (p1.y+p2.y)/2);
	}

	public static Mat extractColorMask(Mat image, Scalar colorLow, Scalar colorUp) {
		Mat imageHSV = new Mat();
		Imgproc.cvtColor(image, imageHSV, Imgproc.COLOR_BGR2HSV);

		Mat mask = new Mat();
		Core.inRange(imageHSV, colorLow, colorUp, mask);

		return mask;
	}

	public static Point[] copy4Points(Point[] points) {
		return new Point[]{
				new Point(points[0].x, points[0].y), new Point(points[1].x, points[1].y),
				new Point(points[2].x, points[2].y), new Point(points[3].x, points[3].y)};
	}

	public static Scalar convertBGR2HSV(Scalar bgr) {
		int bB = (int) bgr.val[0];
		int bG = (int) bgr.val[1];
		int bR = (int) bgr.val[2];

		// Convert from 8-bit integers to floats.
		double fR = bR / 255.;
		double fG = bG / 255.;
		double fB = bB / 255.;

		// Convert from RGB to HSV, using float ranges 0.0 to 1.0.
		double fDelta;
		double fMin, fMax;
		int iMax;
		// Get the min and max, but use integer comparisons for slight speedup.
		if (bB < bG) {
			if (bB < bR) {
				fMin = fB;
				if (bR > bG) {
					iMax = bR;
					fMax = fR;
				}
				else {
					iMax = bG;
					fMax = fG;
				}
			}
			else {
				fMin = fR;
				fMax = fG;
				iMax = bG;
			}
		}
		else {
			if (bG < bR) {
				fMin = fG;
				if (bB > bR) {
					fMax = fB;
					iMax = bB;
				}
				else {
					fMax = fR;
					iMax = bR;
				}
			}
			else {
				fMin = fR;
				fMax = fB;
				iMax = bB;
			}
		}
		fDelta = fMax - fMin;
		double fH, fS, fV;
		fV = fMax;				// Value (Brightness).
		if (iMax != 0) {			// Make sure it's not pure black.
			fS = fDelta / fMax;		// Saturation.
			double ANGLE_TO_UNIT = 1.0f / (6.0f * fDelta);	// Make the Hues between 0.0 to 1.0 instead of 6.0
			if (iMax == bR) {		// between yellow and magenta.
				fH = (fG - fB) * ANGLE_TO_UNIT;
			}
			else if (iMax == bG) {		// between cyan and yellow.
				fH = (2.0f/6.0f) + ( fB - fR ) * ANGLE_TO_UNIT;
			}
			else {				// between magenta and cyan.
				fH = (4.0f/6.0f) + ( fR - fG ) * ANGLE_TO_UNIT;
			}
			// Wrap outlier Hues around the circle.
			if (fH < 0.0f)
				fH += 1.0f;
			if (fH >= 1.0f)
				fH -= 1.0f;
		}
		else {
			// color is pure Black.
			fS = 0;
			fH = 0;	// undefined hue
		}

		// Convert from floats to 8-bit integers.
		int bH = (int)(0.5f + fH * 255.0f);
		int bS = (int)(0.5f + fS * 255.0f);
		int bV = (int)(0.5f + fV * 255.0f);

		// Clip the values to make sure it fits within the 8bits.
		if (bH > 255)
			bH = 255;
		if (bH < 0)
			bH = 0;
		if (bS > 255)
			bS = 255;
		if (bS < 0)
			bS = 0;
		if (bV > 255)
			bV = 255;
		if (bV < 0)
			bV = 0;

		return new Scalar(bH, bS, bV);
	}

}
