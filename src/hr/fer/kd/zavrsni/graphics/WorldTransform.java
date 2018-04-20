package hr.fer.kd.zavrsni.graphics;

import hr.fer.kd.zavrsni.graphics.picker.Picker;
import hr.fer.kd.zavrsni.model.balls.Ball;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class WorldTransform {

	private static final double TRANSLATE_SPEED = 0.01;
	private static final double TRANSLATE_SPEED_FACTOR = 10;
	private static final double ROTATE_SPEED = 0.01;
	private static final double MOUSE_WHEEL_FACTOR = 10;

	private TransformGroup transformGroup;

	private Transform3D transform;

	private MouseRotate mouseRotate;
	private KeyboardMove keyboardMove;

	private Picker picker;

	private int scene;

	public WorldTransform(TransformGroup tg, Canvas3D canvas, Picker p) {
		transformGroup = tg;
		picker = p;

		keyboardMove = new KeyboardMove();
		canvas.addKeyListener(keyboardMove);

		mouseRotate = new MouseRotate();
		canvas.addMouseListener(mouseRotate);
		canvas.addMouseMotionListener(mouseRotate);
		canvas.addMouseWheelListener(mouseRotate);
	}

	public void transformScene(int scene) {
		switch (scene) {
		case 1:
			transformScene1();
			break;
		case 2:
			transformScene2();
			break;
		}
	}

	private void transformScene1() {
		scene = 1;

		transform = new Transform3D();
		transform.setTranslation(new Vector3f(0f, 0f, -3f));
		transform.setRotation(new AxisAngle4d(1, 0, 0, 0.6));
		transformGroup.setTransform(transform);

		mouseRotate.reset();
	}

	private void transformScene2() {
		scene = 2;

		Transform3D t = new Transform3D();
		t.setRotation(new AxisAngle4d(1, 0, 0, Math.PI / 2));
		t.setTranslation(new Vector3f(0f, 0f, -1.7f));
		transformGroup.setTransform(t);
	}

	public void changeTransform() {
		if (scene == 1) {
			transformScene2();
		} else {
			transformScene1();
		}
	}

	private class KeyboardMove extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.isControlDown()) {
				return;
			}

			Transform3D tmp = new Transform3D();

			double speed = TRANSLATE_SPEED;

			if (e.isShiftDown()) {
				speed *= TRANSLATE_SPEED_FACTOR;
			}

			Transform3D t = new Transform3D();
			transformGroup.getTransform(t);
			Matrix3d m = new Matrix3d();
			t.getRotationScale(m);

			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				tmp.setTranslation(new Vector3d(speed*m.m00, speed*m.m01, speed*m.m02));
				break;
			case KeyEvent.VK_RIGHT:
				tmp.setTranslation(new Vector3d(-speed*m.m00, -speed*m.m01, -speed*m.m02));
				break;
			case KeyEvent.VK_UP:
				tmp.setTranslation(new Vector3d(speed*m.m20, 0/*speed*m.m21*/, speed*m.m22));
				break;
			case KeyEvent.VK_DOWN:
				tmp.setTranslation(new Vector3d(-speed*m.m20, 0/*-speed*m.m21*/, -speed*m.m22));
				break;
			}

			transform.mul(tmp);

			mouseRotate.rotate();
		}

	}

	private class MouseRotate extends MouseAdapter {

		private Point start;

		private Transform3D tmp = new Transform3D();
		private Vector3d rotateCenter = new Vector3d();

		public void reset() {
			tmp = new Transform3D();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			start = e.getPoint();
			Ball ball = picker.getPickedBall(e);
			if (ball == null) {
				rotateCenter = new Vector3d();
			} else {
				rotateCenter = GraphicsUtil.getVector3d(ball.getPosition(), 0);
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Point delta = new Point(e.getX() - start.x, e.getY() - start.y);

			Transform3D t = new Transform3D();
			transformGroup.getTransform(t);
			Matrix3d m = new Matrix3d();
			t.getRotationScale(m);

			tmp = new Transform3D();
			tmp.setRotation(new AxisAngle4d(0, 1, 0, delta.x * ROTATE_SPEED));

			Transform3D tmp2 = new Transform3D();
			tmp2.setRotation(new AxisAngle4d(m.m00, m.m01, m.m02, delta.y * ROTATE_SPEED));
			tmp.mul(tmp2);

			rotate();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			rotateArroundCenter(transform);
			tmp = new Transform3D();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			Transform3D tmp = new Transform3D();

			double speed = TRANSLATE_SPEED * MOUSE_WHEEL_FACTOR;

			Transform3D t = new Transform3D();
			transformGroup.getTransform(t);
			Matrix3d m = new Matrix3d();
			t.getRotationScale(m);

			speed *= -e.getPreciseWheelRotation();

			tmp.setTranslation(new Vector3d(speed*m.m20, speed*m.m21, speed*m.m22));

			transform.mul(tmp);

			mouseRotate.rotate();
		}

		public void rotate() {
			if (scene == 1) {
				Transform3D t1 = new Transform3D(transform);

				rotateArroundCenter(t1);

				transformGroup.setTransform(t1);
			}
		}

		private void rotateArroundCenter(Transform3D t1) {
			Transform3D t2 = new Transform3D();
			t2.setTranslation(rotateCenter);
			t1.mul(t2);

			t1.mul(tmp);

			Transform3D t3 = new Transform3D();
			rotateCenter.negate();
			t3.setTranslation(rotateCenter);
			rotateCenter.negate();
			t1.mul(t3);
		}

	}

}
