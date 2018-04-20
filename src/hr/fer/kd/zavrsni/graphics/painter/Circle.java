package hr.fer.kd.zavrsni.graphics.painter;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

public class Circle extends Shape {

	private static final int SEGMENTS = 40;

	private double radius;

	private Appearance ap;

	private Color3f color = new Color3f(1, 1, 1);

	public Circle(double radius) {
		this.radius = radius;

		ap = new Appearance();

		LineAttributes lattr = new LineAttributes();
		lattr.setLineWidth(2f);
		lattr.setLineAntialiasingEnable(true);
		ap.setLineAttributes(lattr);

		setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		setCapability(BranchGroup.ALLOW_CHILDREN_READ);

		ap.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_READ);
		ap.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
	}

	public Circle(Point3d center, double radius) {
		this(radius);
		updateCircle(center);
	}

	public void setColor(Color3f color) {
		this.color = color;
	}

	public void updateCircle(Point3d center) {
		ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.NICEST);
		ap.setColoringAttributes(ca);

		LineArray la = new LineArray(SEGMENTS * 2, LineArray.COORDINATES);

		calculateCoordinates(la, center, radius);

		BranchGroup bg = new BranchGroup();
		bg.setCapability(BranchGroup.ALLOW_DETACH);
		bg.addChild(new Shape3D(la, ap));

		removeAllChildren();
		addChild(bg);
	}

	private void calculateCoordinates(LineArray la, Point3d center, double radius) {
		double alpha = 2*Math.PI / SEGMENTS;

		double x = Math.sin(alpha/2) * radius;
		double z = Math.sqrt(radius*radius - x*x);

		Point3d start = new Point3d(center.x + x, 0, center.z - z);

		double a = 2*x;
		double angle = alpha;

		Point3d point = start;
		for (int i = 0; i < SEGMENTS - 1; i++) {
			la.setCoordinate(i*2, point);
			double dx = a * Math.cos(angle);
			double dz = a * Math.sin(angle);
			point.x += dx;
			point.z += dz;
			angle += alpha;
			la.setCoordinate(i*2 + 1, point);
		}
		la.setCoordinate(2*SEGMENTS - 2, point);
		la.setCoordinate(2*SEGMENTS - 1, start);
	}

	public void deleteCircle() {
		removeAllChildren();
	}

}
