package hr.fer.kd.zavrsni.graphics.picker;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.graphics.GraphicsUtil;
import hr.fer.kd.zavrsni.graphics.balls.GBall;
import hr.fer.kd.zavrsni.graphics.painter.Circle;
import hr.fer.kd.zavrsni.graphics.painter.Shape;
import hr.fer.kd.zavrsni.graphics.painter.SimplePainter;
import hr.fer.kd.zavrsni.graphics.painter.XShape;
import hr.fer.kd.zavrsni.graphics.pockets.GPocket;
import hr.fer.kd.zavrsni.model.balls.Ball;
import hr.fer.kd.zavrsni.model.table.Pocket;
import hr.fer.kd.zavrsni.shot_analysis.simulation.BreakPoint;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickIntersection;
import com.sun.j3d.utils.picking.PickResult;

public class Picker {

	private static final double BALL_CIRCLE_CONSTANT = 1.5;

	private IPickerListener listener;

	private Ball pickedBall;
	private Pocket pickedPocket;
	private MyVector pickedPoint;

	private BallPicker ballPicker;
	private PlatePicker platePicker;
	private PocketPicker pocketPicker;

	private Circle ballCircle;
	private XShape xShape;

	private KeyListener keyListener;

	private LinkedList<List<BreakPoint>> breakPoints;

	private ISpeedSupply speedSupply;

	public Picker(IPickerListener listener, Canvas3D canvas, BranchGroup ballSet, BranchGroup plate,
			BranchGroup pockets, double ballRadius, ISpeedSupply speedSupply) {
		this.listener = listener;
		this.speedSupply = speedSupply;

		ballPicker = new BallPicker(canvas, ballSet);
		platePicker = new PlatePicker(canvas, plate);
		pocketPicker = new PocketPicker(canvas, pockets);

		ballCircle = new Circle(ballRadius * BALL_CIRCLE_CONSTANT);
		ballCircle.setColor(new Color3f(1, 0, 0));
		xShape = new XShape(ballRadius * 2);
		xShape.setColor(new Color3f(1, 0, 0));

		keyListener = new KeyListener();

		SimplePainter.painter.addShape(ballCircle, false);
		SimplePainter.painter.addShape(xShape, false);
	}

	public void registerListeners(Canvas3D canvas) {
		canvas.addMouseListener(platePicker);
		canvas.addMouseMotionListener(platePicker);
		canvas.addMouseListener(pocketPicker);
		canvas.addMouseListener(ballPicker);
		canvas.addKeyListener(keyListener);
	}

	public Ball getPickedBall(MouseEvent e) {
		return ballPicker.getPickedBall(e);
	}

	private void drawShapes() {
		if (breakPoints == null || breakPoints.isEmpty()) {
			return;
		}

		SimplePainter.painter.removeShapes();
		for (BreakPoint bp : breakPoints.getFirst()) {
			List<Shape> shapes = bp.getShapes();
			for (Shape s : shapes) {
				SimplePainter.painter.addShape(s, true);
			}
		}
	}

	private class BallPicker extends MouseAdapter {

		private PickCanvas pickCanvas;

		public BallPicker(Canvas3D canvas, BranchGroup ballSet) {
			pickCanvas = new PickCanvas(canvas, ballSet);
			pickCanvas.setMode(PickCanvas.GEOMETRY);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			Ball pb = getPickedBall(e);
			if (pb == null || pickedBall != null && pickedBall.getNumber() == 0) {
				return;
			}
			reset();

			pickedBall = pb;
			if (pickedBall != null && pickedBall.getNumber() != 0) {
				ballCircle.updateCircle(new Point3d(GraphicsUtil.getVector3d(pickedBall.getPosition(), 0)));
			}
		}

		public Ball getPickedBall(MouseEvent e) {
			pickCanvas.setShapeLocation(e);
			PickResult result = pickCanvas.pickClosest();

			if (result != null) {
				Primitive p = (Primitive)result.getNode(PickResult.PRIMITIVE);

				if (p == null) {
					return null;
				}

				try {
					return ((GBall)p.getParent().getParent()).getBall();
				} catch (ClassCastException exc) {
					return null;
				}
			}

			return null;
		}

	}

	private class PlatePicker extends MouseAdapter {

		private PickCanvas pickCanvas;

		public PlatePicker(Canvas3D canvas, BranchGroup plate) {
			pickCanvas = new PickCanvas(canvas, plate);
			pickCanvas.setMode(PickCanvas.GEOMETRY);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (pickedBall == null || pickedPocket != null || pickedPoint != null) {
				return;
			}

			if (pickedBall.getNumber() == 0) {
				Point3d point = getPoint(e);
				if (point != null) {
					MyVector p = GraphicsUtil.getMyVector(point);
					breakPoints = new LinkedList<>();
					breakPoints.add(listener.shotPicked(pickedBall, p, speedSupply.getSpeed()));
					drawShapes();
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (pickedBall == null || pickedBall.getNumber() != 0 || pickedPocket != null || pickedPoint != null) {
				return;
			}

			Point3d point = getPoint(e);
			if (point != null) {
				pickedPoint = GraphicsUtil.getMyVector(point);
				breakPoints = new LinkedList<>();
				breakPoints.add(listener.shotPicked(pickedBall, pickedPoint, speedSupply.getSpeed()));
				drawShapes();
			}
		}

		private Point3d getPoint(MouseEvent e) {
			pickCanvas.setShapeLocation(e);
			PickResult result = pickCanvas.pickClosest();

			if (result != null) {
				int n = result.numIntersections();
				if (n > 0) {
					PickIntersection inter = result.getIntersection(0);
					return inter.getPointCoordinates();
				}
			}

			return null;
		}

	}

	private class PocketPicker extends MouseAdapter {

		private PickCanvas pickCanvas;

		public PocketPicker(Canvas3D canvas, BranchGroup pockets) {
			pickCanvas = new PickCanvas(canvas, pockets);
			pickCanvas.setMode(PickCanvas.GEOMETRY);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (pickedBall == null || pickedBall.getNumber() == 0 || pickedPocket != null || pickedPoint != null) {
				return;
			}

			pickCanvas.setShapeLocation(e);
			PickResult result = pickCanvas.pickClosest();

			if (result != null) {
				Shape3D s = (Shape3D)result.getNode(PickResult.SHAPE3D);

				try {
					GPocket gp = (GPocket)s.getParent();
					pickedPocket = gp.getPocket();
					xShape.updateX(new Point3d(GraphicsUtil.getVector3d(pickedPocket.getPoint(), 0)));
					breakPoints = listener.potPicked(pickedBall, pickedPocket);
					drawShapes();
				} catch (ClassCastException exc) {
					exc.printStackTrace();
					return;
				}
			}
		}

	}

	private class KeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyChar() == 'c') {
				reset();
			}

			if (e.getKeyChar() == '\n' && pickedBall != null) {
				if (breakPoints == null || breakPoints.isEmpty()) {
					return;
				}
				listener.simulate(breakPoints.getFirst());
				reset();
			}

			if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_LEFT
					&& breakPoints != null && !breakPoints.isEmpty()) {
				List<BreakPoint> bps = breakPoints.removeLast();
				breakPoints.addFirst(bps);
				drawShapes();
			}

			if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_RIGHT
					&& breakPoints != null && !breakPoints.isEmpty()) {
				List<BreakPoint> bps = breakPoints.removeFirst();
				breakPoints.addLast(bps);
				drawShapes();
			}
		}

	}

	private void reset() {
		pickedBall = null;
		pickedPocket = null;
		pickedPoint = null;
		ballCircle.deleteCircle();
		xShape.deleteX();
	}

}
