package hr.fer.kd.zavrsni.graphics.painter;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point3d;

public class Line extends Shape {

	private Appearance ap;
	private LineAttributes lattr;

	public Line() {
		ap = new Appearance();

		lattr = new LineAttributes();
		lattr.setLineWidth(2f);
		lattr.setLineAntialiasingEnable(true);
		ap.setLineAttributes(lattr);

		setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	}

	public Line(Point3d start, Point3d end) {
		this();
		updateLine(start, end);
	}

	public void setLinePattern(int pattern) {
		lattr.setLinePattern(pattern);
	}

	public void updateLine(Point3d start, Point3d end) {
		LineArray la = new LineArray(2, LineArray.COORDINATES);

		la.setCoordinate(0, start);
		la.setCoordinate(1, end);

		BranchGroup bg = new BranchGroup();
		bg.setCapability(BranchGroup.ALLOW_DETACH);
		bg.addChild(new Shape3D(la, ap));

		removeAllChildren();
		addChild(bg);
	}

	public void deleteLine() {
		removeAllChildren();
	}

}
