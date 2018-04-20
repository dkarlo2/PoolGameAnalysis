package hr.fer.kd.zavrsni.graphics.table;

import hr.fer.kd.zavrsni.graphics.GraphicsUtil;
import hr.fer.kd.zavrsni.model.table.Table;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Point3d;

public class GSurround {

	private static final double OUT_THICKNESS = GTable.OUT_THICKNESS;
	private static final double MOUT_THICKNESS = GTable.MOUT_THICKNESS;

	private static double CORNER_POCKET_SIZE;
	private static double MIDDLE_POCKET_SIZE;

	private static final double SURROUND_LIFT = 0.001f;

	public static Shape3D getSurround(double x, double z, Texture textureDark, Table table) {
		CORNER_POCKET_SIZE = GraphicsUtil.getGF() * table.CORNER_POCKET_SIZE;
		MIDDLE_POCKET_SIZE = GraphicsUtil.getGF() * table.MIDDLE_POCKET_SIZE;

		Appearance ap = new Appearance();

		Material mat = new Material(GTable.AMBIENT, GTable.EMISSIVE, GTable.DIFFUSE, GTable.SPECULAR, GTable.SHININESS);
		ap.setMaterial(mat);

		ap.setTexture(textureDark);

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		ap.setTextureAttributes(texAttr);

		QuadArray qa = new QuadArray(24, QuadArray.COORDINATES);

		qa.setCoordinate(0, new Point3d(-x, SURROUND_LIFT, -z+CORNER_POCKET_SIZE));
		qa.setCoordinate(1, new Point3d(-x-OUT_THICKNESS, SURROUND_LIFT, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));
		qa.setCoordinate(2, new Point3d(-x-OUT_THICKNESS, SURROUND_LIFT, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		qa.setCoordinate(3, new Point3d(-x, SURROUND_LIFT, z-CORNER_POCKET_SIZE));

		qa.setCoordinate(4, new Point3d(-x+CORNER_POCKET_SIZE, SURROUND_LIFT, z));
		qa.setCoordinate(5, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS, SURROUND_LIFT, z+OUT_THICKNESS));
		qa.setCoordinate(6, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS, SURROUND_LIFT, z+OUT_THICKNESS));
		qa.setCoordinate(7, new Point3d(-MIDDLE_POCKET_SIZE, SURROUND_LIFT, z));

		qa.setCoordinate(8, new Point3d(MIDDLE_POCKET_SIZE, SURROUND_LIFT, z));
		qa.setCoordinate(9, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS, SURROUND_LIFT, z+OUT_THICKNESS));
		qa.setCoordinate(10, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS, SURROUND_LIFT, z+OUT_THICKNESS));
		qa.setCoordinate(11, new Point3d(x-CORNER_POCKET_SIZE, SURROUND_LIFT, z));

		qa.setCoordinate(12, new Point3d(x, SURROUND_LIFT, -z+CORNER_POCKET_SIZE));
		qa.setCoordinate(13, new Point3d(x, SURROUND_LIFT, z-CORNER_POCKET_SIZE));
		qa.setCoordinate(14, new Point3d(x+OUT_THICKNESS, SURROUND_LIFT, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		qa.setCoordinate(15, new Point3d(x+OUT_THICKNESS, SURROUND_LIFT, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));

		qa.setCoordinate(16, new Point3d(MIDDLE_POCKET_SIZE, SURROUND_LIFT, -z));
		qa.setCoordinate(17, new Point3d(x-CORNER_POCKET_SIZE, SURROUND_LIFT, -z));
		qa.setCoordinate(18, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS, SURROUND_LIFT, -z-OUT_THICKNESS));
		qa.setCoordinate(19, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS, SURROUND_LIFT, -z-OUT_THICKNESS));

		qa.setCoordinate(20, new Point3d(-x+CORNER_POCKET_SIZE, SURROUND_LIFT, -z));
		qa.setCoordinate(21, new Point3d(-MIDDLE_POCKET_SIZE, SURROUND_LIFT, -z));
		qa.setCoordinate(22, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS, SURROUND_LIFT, -z-OUT_THICKNESS));
		qa.setCoordinate(23, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS, SURROUND_LIFT, -z-OUT_THICKNESS));

		return new Shape3D(qa, ap);
	}

}
