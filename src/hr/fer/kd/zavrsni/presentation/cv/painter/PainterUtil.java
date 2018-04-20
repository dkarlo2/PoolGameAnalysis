package hr.fer.kd.zavrsni.presentation.cv.painter;

import java.awt.Graphics2D;

import org.opencv.core.Point;

public class PainterUtil {

	private PainterUtil() {}

	public static void drawLine(Graphics2D g, Point p1, Point p2, double scale) {
		int x1 = (int)(p1.x * scale);
		int x2 = (int)(p2.x * scale);
		int y1 = (int)(p1.y * scale);
		int y2 = (int)(p2.y * scale);
		g.drawLine(x1, y1, x2, y2);
	}

	public static void drawCircle(Graphics2D g, Point center, double radius, double scale) {
		int x = (int)(center.x * scale);
		int y = (int)(center.y * scale);
		int r = (int)radius;
		g.drawOval(x-r/2, y-r/2, r, r);
	}

}
