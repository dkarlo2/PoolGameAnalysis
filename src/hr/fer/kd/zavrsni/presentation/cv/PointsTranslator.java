package hr.fer.kd.zavrsni.presentation.cv;

import hr.fer.kd.zavrsni.ball_detection.cv.CVUtil;
import hr.fer.kd.zavrsni.presentation.cv.image_display.MatImageDisplayer;
import hr.fer.kd.zavrsni.presentation.cv.painter.Graphics2DPainter;
import hr.fer.kd.zavrsni.presentation.cv.painter.PainterUtil;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.opencv.core.Point;

public class PointsTranslator extends MouseAdapter implements Graphics2DPainter {

	private static final double RADIUS2 = 1000;
	private static final double RADIUS = Math.sqrt(RADIUS2);

	private static final double LINE_SELECTION_DIST2 = 1000;

	private Point[] points;

	private int mouse = -1;

	private MatImageDisplayer display;

	public PointsTranslator(Point[] points, MatImageDisplayer display) {
		this.points = points;
		this.display = display;

		display.addMouseListener(this);
		display.addMouseMotionListener(this);
		display.setPainter(this);
	}

	public Point[] getPoints() {
		return points;
	}

	public void setPoints(Point[] points) {
		this.points = points;
		display.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouse = findSelectedPoint(display.getPointOnImage(e.getPoint()));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouse = -1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (mouse != -1) {
			transferPoints(display.getPointOnImage(e.getPoint()));
			display.repaint();
		}
	}

	private void transferPoints(Point point) {
		if (mouse < 4) {
			points[mouse] = point;
		}
		if (mouse == 4) {
			double offset = point.y - (points[0].y + points[1].y) / 2;
			points[0].y += offset;
			points[1].y += offset;
		}
		if (mouse == 5) {
			double offset = point.x - (points[1].x + points[2].x) / 2;
			points[1].x += offset;
			points[2].x += offset;
		}
		if (mouse == 6) {
			double offset = point.y - (points[2].y + points[3].y) / 2;
			points[2].y += offset;
			points[3].y += offset;
		}
		if (mouse == 7) {
			double offset = point.x - (points[3].x + points[0].x) / 2;
			points[3].x += offset;
			points[0].x += offset;
		}
	}

	private int findSelectedPoint(Point point) {
		if (CVUtil.distance2(point, points[0]) < RADIUS2) {
			return 0;
		}
		if (CVUtil.distance2(point, points[1]) < RADIUS2) {
			return 1;
		}
		if (CVUtil.distance2(point, points[2]) < RADIUS2) {
			return 2;
		}
		if (CVUtil.distance2(point, points[3]) < RADIUS2) {
			return 3;
		}
		if (CVUtil.distance2(point, points[0], points[1]) < LINE_SELECTION_DIST2) {
			return 4;
		}
		if (CVUtil.distance2(point, points[1], points[2]) < LINE_SELECTION_DIST2) {
			return 5;
		}
		if (CVUtil.distance2(point, points[2], points[3]) < LINE_SELECTION_DIST2) {
			return 6;
		}
		if (CVUtil.distance2(point, points[3], points[0]) < LINE_SELECTION_DIST2) {
			return 7;
		}
		return -1;
	}

	@Override
	public void paint(Graphics2D g) {
		double scale = display.getScale();

		g.setColor(Color.WHITE);

		g.setStroke(new BasicStroke(2));

		PainterUtil.drawLine(g, points[0], points[1], scale);
		PainterUtil.drawLine(g, points[1], points[2], scale);
		PainterUtil.drawLine(g, points[2], points[3], scale);
		PainterUtil.drawLine(g, points[3], points[0], scale);

		PainterUtil.drawCircle(g, points[0], RADIUS, scale);
		PainterUtil.drawCircle(g, points[1], RADIUS, scale);
		PainterUtil.drawCircle(g, points[2], RADIUS, scale);
		PainterUtil.drawCircle(g, points[3], RADIUS, scale);

		PainterUtil.drawCircle(g, CVUtil.half(points[0], points[1]), RADIUS, scale);
		PainterUtil.drawCircle(g, CVUtil.half(points[2], points[3]), RADIUS, scale);
	}

}
