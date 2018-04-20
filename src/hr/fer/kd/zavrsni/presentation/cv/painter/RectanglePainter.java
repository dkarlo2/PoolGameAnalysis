package hr.fer.kd.zavrsni.presentation.cv.painter;

import hr.fer.kd.zavrsni.presentation.cv.image_display.MatImageDisplayer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import org.opencv.core.Point;

public class RectanglePainter implements Graphics2DPainter {

	private Point[] points;

	private MatImageDisplayer mid;

	private Color color;

	public RectanglePainter(Point[] points, MatImageDisplayer mid) {
		this(points, mid, Color.RED);
	}

	public RectanglePainter(Point[] points, MatImageDisplayer mid, Color color) {
		this.points = points;
		this.mid = mid;
		this.color = color;
	}

	@Override
	public void paint(Graphics2D g) {
		double scale = mid.getScale();

		g.setColor(color);

		g.setStroke(new BasicStroke(2));

		PainterUtil.drawLine(g, points[0], points[1], scale);
		PainterUtil.drawLine(g, points[1], points[2], scale);
		PainterUtil.drawLine(g, points[2], points[3], scale);
		PainterUtil.drawLine(g, points[3], points[0], scale);
	}

}
