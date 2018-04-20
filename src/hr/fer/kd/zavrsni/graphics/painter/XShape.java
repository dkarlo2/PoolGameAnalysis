package hr.fer.kd.zavrsni.graphics.painter;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

public class XShape extends Shape {

	private Appearance ap;

	private Color3f color = new Color3f(1, 1, 1);

	private double length;

	public XShape(double length) {
		this.length = length;

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

	public void setColor(Color3f color) {
		this.color = color;
	}

	public void updateX(Point3d center) {
		ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.NICEST);
		ap.setColoringAttributes(ca);

		LineArray la = new LineArray(4, LineArray.COORDINATES);

		double l = length / 2 / Math.sqrt(2);

		la.setCoordinate(0, new Point3d(center.x - l, center.y, center.z - l));
		la.setCoordinate(1, new Point3d(center.x + l, center.y, center.z + l));

		la.setCoordinate(2, new Point3d(center.x - l, center.y, center.z + l));
		la.setCoordinate(3, new Point3d(center.x + l, center.y, center.z - l));

		BranchGroup bg = new BranchGroup();
		bg.setCapability(BranchGroup.ALLOW_DETACH);
		bg.addChild(new Shape3D(la, ap));

		removeAllChildren();
		addChild(bg);
	}

	public void deleteX() {
		removeAllChildren();
	}

}
