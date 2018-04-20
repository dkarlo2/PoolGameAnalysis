package hr.fer.kd.zavrsni.graphics.painter;

import hr.fer.kd.mymath.model.MyLine;
import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.graphics.GraphicsUtil;

import java.util.HashMap;
import java.util.Map;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.LineAttributes;
import javax.vecmath.Point3d;

public class SimplePainter extends BranchGroup {

	public static final SimplePainter painter = new SimplePainter();

	private Map<Shape, Boolean> shapes;

	private SimplePainter() {
		setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);

		shapes = new HashMap<>();
	}

	public void addShape(Shape shape, boolean removable) {
		shapes.put(shape, removable);
		shape.setCapability(BranchGroup.ALLOW_DETACH);
		addChild(shape);
	}

	public void drawCircle(MyVector center, double radius) {
		addShape(new Circle(new Point3d(GraphicsUtil.getVector3d(center, 0)), radius), true);
	}

	public void drawLine(MyLine myLine, boolean removable) {
		Line line = new Line();
		line.updateLine(new Point3d(GraphicsUtil.getVector3d(myLine.getStart(), 0)),
				new Point3d(GraphicsUtil.getVector3d(myLine.getEnd(), 0)));
		addShape(line, removable);
	}

	public void drawDashedLine(MyLine myLine, boolean removable) {
		Line line = new Line();
		line.updateLine(new Point3d(GraphicsUtil.getVector3d(myLine.getStart(), 0)),
				new Point3d(GraphicsUtil.getVector3d(myLine.getEnd(), 0)));
		line.setLinePattern(LineAttributes.PATTERN_DASH);
		addShape(line, removable);
	}

	public void drawTrace(MyVector start, MyVector end, double radius) {
		drawLine(new MyLine(start, end), true);

		MyLine line1 = new MyLine(start, end).translate(radius);
		MyLine line2 = new MyLine(start, end).translate(-radius);

		drawDashedLine(line1, true);
		drawDashedLine(line2, true);
	}

	public void removeShapes() {
		for (Shape s : shapes.keySet()) {
			if (shapes.get(s)) {
				removeChild(s);
			}
		}
	}

}
