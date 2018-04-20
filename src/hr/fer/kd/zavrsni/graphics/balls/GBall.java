package hr.fer.kd.zavrsni.graphics.balls;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.graphics.GraphicsUtil;
import hr.fer.kd.zavrsni.model.balls.Ball;

import java.awt.Container;
import java.util.Random;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

public class GBall extends BranchGroup {

	private static final Color3f AMBIENT = new Color3f(1, 1, 1);
	private static final Color3f EMISSIVE = new Color3f();
	private static final Color3f DIFFUSE = AMBIENT;
	private static final Color3f SPECULAR = new Color3f(1, 1, 1);
	private static final float SHININESS = 70f;

	private Ball ball;

	private TransformGroup tg;

	private Texture texture;

	private Transform3D rotTransform;

	public GBall(Ball ball) {
		super();

		tg = new TransformGroup();

		this.ball = ball;

		String path;

		if (ball.getNumber() == 0) {
			path = "Textures/BallCue.jpg";
		} else {
			path = "Textures/Ball" + ball.getNumber() + ".jpg";
		}

		TextureLoader loader = new TextureLoader(path, new Container());
		texture = loader.getTexture();

		initTransform();

		Appearance ap = new Appearance();

		Material mat = new Material(AMBIENT, EMISSIVE, DIFFUSE, SPECULAR, SHININESS);
		ap.setMaterial(mat);

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);

		ap.setTexture(texture);
		ap.setTextureAttributes(texAttr);

		int primFlags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

		Sphere sphere = new Sphere((float) (GraphicsUtil.getGF() * ball.getRadius()), primFlags, ap);

		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.addChild(sphere);

		updatePosition();

		addChild(tg);
	}

	private void initTransform() {
		rotTransform = new Transform3D();

		Random random = new Random();

		Transform3D t = new Transform3D();
		t.setRotation(new AxisAngle4d(1, 0, 0, random.nextDouble()));
		rotTransform.mul(t);

		t = new Transform3D();
		t.setRotation(new AxisAngle4d(0, 1, 0, random.nextDouble()));
		rotTransform.mul(t);

		t = new Transform3D();
		t.setRotation(new AxisAngle4d(0, 0, 1, random.nextDouble()));
		rotTransform.mul(t);
	}

	public boolean updatePosition() {
		if (ball.isPotted()) {
			return true;
		}

		MyVector lastMove = ball.getPosition().sub(ball.getLastPosition());
		ball.resetLastPosition();

		double angle = lastMove.norm() / ball.getRadius();

		if (angle > 0) {
			if (lastMove.getY() < 0) {
				angle *= -1;
			}

			MyVector rotAxis;
			if (lastMove.getY() == 0) {
				rotAxis = new MyVector(0, -lastMove.getX()).normalize();
			} else {
				rotAxis = new MyVector(1, -lastMove.getX() / lastMove.getY()).normalize();
			}

			Transform3D tmp = new Transform3D();
			tmp.setRotation(new AxisAngle4d(rotAxis.getX(), 0, rotAxis.getY(), angle));

			tmp.mul(rotTransform);
			rotTransform = tmp;
		}

		Transform3D tmp2 = new Transform3D(rotTransform);
		tmp2.setTranslation(GraphicsUtil.getVector3d(ball.getPosition(), GraphicsUtil.getGF() * ball.getRadius()));
		tg.setTransform(tmp2);

		return false;
	}

	public Ball getBall() {
		return ball;
	}

}
