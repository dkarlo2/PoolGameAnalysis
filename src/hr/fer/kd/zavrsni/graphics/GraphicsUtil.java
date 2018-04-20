package hr.fer.kd.zavrsni.graphics;

import hr.fer.kd.mymath.model.MyVector;

import java.util.Enumeration;

import javax.media.j3d.Geometry;
import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.picking.PickTool;

public class GraphicsUtil {

	private static double GRAPHIC_FACTOR;

	public static void setGF(double gf) {
		GRAPHIC_FACTOR = gf;
	}

	public static double getGF() {
		return GRAPHIC_FACTOR;
	}

	public static Vector3d getVector3d(MyVector v, double height) {
		return new Vector3d(v.getX() * GRAPHIC_FACTOR, height, v.getY() * GRAPHIC_FACTOR);
	}

	public static MyVector getMyVector(Point3d p) {
		return new MyVector(p.x / GRAPHIC_FACTOR, p.z / GRAPHIC_FACTOR);
	}

	@SuppressWarnings("rawtypes")
	public static void enablePicking(Node node) {
		node.setPickable(true);
		node.setCapability(Node.ENABLE_PICK_REPORTING);

		try {
			Group group = (Group)node;
			for (Enumeration e = group.getAllChildren(); e.hasMoreElements();) {
				enablePicking((Node)e.nextElement());
			}
		}

		catch(ClassCastException e) {
			// if not a group node, there are no children so ignore exception
		}

		try {
			Shape3D shape = (Shape3D)node;

			PickTool.setCapabilities(node, PickTool.INTERSECT_FULL);

			for (Enumeration e = shape.getAllGeometries(); e.hasMoreElements();) {
				Geometry g = (Geometry)e.nextElement();
				g.setCapability(Geometry.ALLOW_INTERSECT);

			}

		}

		catch(ClassCastException e) {
			// not a Shape3D node ignore exception
		}

	}

}
