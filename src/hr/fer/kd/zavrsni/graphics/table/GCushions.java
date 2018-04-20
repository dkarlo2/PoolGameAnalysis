package hr.fer.kd.zavrsni.graphics.table;

import hr.fer.kd.zavrsni.graphics.GraphicsUtil;
import hr.fer.kd.zavrsni.model.table.Table;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;
import javax.vecmath.TexCoord2f;

public class GCushions {

	private static TransformGroup tg;
	private static final double OUT_THICKNESS = GTable.OUT_THICKNESS;
	private static final double MOUT_THICKNESS = GTable.MOUT_THICKNESS;
	private static final double UP_THICKNESS = GTable.UP_THICKNESS;

	private static double CORNER_POCKET_SIZE;
	private static double MIDDLE_POCKET_SIZE;

	public static TransformGroup getCushions(double x, double z, double height, Texture textureMini, Texture textureDark,
			Table table) {
		CORNER_POCKET_SIZE = GraphicsUtil.getGF() * table.CORNER_POCKET_SIZE;
		MIDDLE_POCKET_SIZE = GraphicsUtil.getGF() * table.MIDDLE_POCKET_SIZE;

		tg  = new TransformGroup();

		Appearance ap1 = new Appearance();
		Appearance ap2 = new Appearance();

		Material mat = new Material(GTable.AMBIENT, GTable.EMISSIVE, GTable.DIFFUSE, GTable.SPECULAR, GTable.SHININESS);
		ap1.setMaterial(mat);
		ap2.setMaterial(mat);

		ap1.setTexture(textureMini);
		ap2.setTexture(textureDark);

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		ap1.setTextureAttributes(texAttr);
		ap2.setTextureAttributes(texAttr);

		addCushion1(x, z, height, ap1, ap2);
		addCushion2(x, z, height, ap1, ap2);
		addCushion3(x, z, height, ap1, ap2);
		addCushion4(x, z, height, ap1, ap2);
		addCushion5(x, z, height, ap1, ap2);
		addCushion6(x, z, height, ap1, ap2);

		return tg;
	}

	private static void addCushion1(double x, double z, double height, Appearance ap1, Appearance ap2) {
		QuadArray qa = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2);

		qa.setCoordinate(0, new Point3d(-x, height, -z+CORNER_POCKET_SIZE));
		qa.setCoordinate(1, new Point3d(-x-OUT_THICKNESS, height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));
		qa.setCoordinate(2, new Point3d(-x-OUT_THICKNESS, height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		qa.setCoordinate(3, new Point3d(-x, height, z-CORNER_POCKET_SIZE));

		qa.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 1, new TexCoord2f(0.0f, 1.0f));
		qa.setTextureCoordinate(0, 2, new TexCoord2f(1.0f, 1.0f));
		qa.setTextureCoordinate(0, 3, new TexCoord2f(1.0f, 0.0f));

		QuadArray qaDark = new QuadArray(4, QuadArray.COORDINATES);

		qaDark.setCoordinate(0, new Point3d(-x-OUT_THICKNESS, 0, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));
		qaDark.setCoordinate(1, new Point3d(-x, height, -z+CORNER_POCKET_SIZE));
		qaDark.setCoordinate(2, new Point3d(-x, height, z-CORNER_POCKET_SIZE));
		qaDark.setCoordinate(3, new Point3d(-x-OUT_THICKNESS, 0, z-CORNER_POCKET_SIZE+OUT_THICKNESS));

		TriangleArray ta = new TriangleArray(6, TriangleArray.COORDINATES);

		ta.setCoordinate(0, new Point3d(-x, height, -z+CORNER_POCKET_SIZE));
		ta.setCoordinate(1, new Point3d(-x-OUT_THICKNESS, 0, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));
		ta.setCoordinate(2, new Point3d(-x-OUT_THICKNESS, height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));

		ta.setCoordinate(3, new Point3d(-x, height, z-CORNER_POCKET_SIZE));
		ta.setCoordinate(4, new Point3d(-x-OUT_THICKNESS, height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		ta.setCoordinate(5, new Point3d(-x-OUT_THICKNESS, 0, z-CORNER_POCKET_SIZE+OUT_THICKNESS));

		tg.addChild(new Shape3D(qa, ap1));
		tg.addChild(new Shape3D(qaDark, ap2));
		tg.addChild(new Shape3D(ta, ap2));
	}

	private static void addCushion2(double x, double z, double height, Appearance ap1, Appearance ap2) {
		QuadArray qa = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2);

		qa.setCoordinate(0, new Point3d(x, height, -z+CORNER_POCKET_SIZE));
		qa.setCoordinate(3, new Point3d(x+OUT_THICKNESS, height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));
		qa.setCoordinate(2, new Point3d(x+OUT_THICKNESS, height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		qa.setCoordinate(1, new Point3d(x, height, z-CORNER_POCKET_SIZE));

		qa.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 3, new TexCoord2f(0.0f, 1.0f));
		qa.setTextureCoordinate(0, 2, new TexCoord2f(1.0f, 1.0f));
		qa.setTextureCoordinate(0, 1, new TexCoord2f(1.0f, 0.0f));

		QuadArray qaDark = new QuadArray(4, QuadArray.COORDINATES);

		qaDark.setCoordinate(0, new Point3d(x+OUT_THICKNESS, 0, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));
		qaDark.setCoordinate(3, new Point3d(x, height, -z+CORNER_POCKET_SIZE));
		qaDark.setCoordinate(2, new Point3d(x, height, z-CORNER_POCKET_SIZE));
		qaDark.setCoordinate(1, new Point3d(x+OUT_THICKNESS, 0, z-CORNER_POCKET_SIZE+OUT_THICKNESS));

		TriangleArray ta = new TriangleArray(6, TriangleArray.COORDINATES);

		ta.setCoordinate(0, new Point3d(x, height, -z+CORNER_POCKET_SIZE));
		ta.setCoordinate(2, new Point3d(x+OUT_THICKNESS, 0, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));
		ta.setCoordinate(1, new Point3d(x+OUT_THICKNESS, height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));

		ta.setCoordinate(3, new Point3d(x, height, z-CORNER_POCKET_SIZE));
		ta.setCoordinate(5, new Point3d(x+OUT_THICKNESS, height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		ta.setCoordinate(4, new Point3d(x+OUT_THICKNESS, 0, z-CORNER_POCKET_SIZE+OUT_THICKNESS));

		tg.addChild(new Shape3D(qa, ap1));
		tg.addChild(new Shape3D(qaDark, ap2));
		tg.addChild(new Shape3D(ta, ap2));
	}

	private static void addCushion3(double x, double z, double height, Appearance ap1, Appearance ap2) {
		QuadArray qa = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2);

		qa.setCoordinate(0, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS, height+UP_THICKNESS, -z-OUT_THICKNESS));
		qa.setCoordinate(1, new Point3d(-x+CORNER_POCKET_SIZE, height, -z));
		qa.setCoordinate(2, new Point3d(-MIDDLE_POCKET_SIZE, height, -z));
		qa.setCoordinate(3, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS, height+UP_THICKNESS, -z-OUT_THICKNESS));

		qa.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 1, new TexCoord2f(0.0f, 1.0f));
		qa.setTextureCoordinate(0, 2, new TexCoord2f(1.0f, 1.0f));
		qa.setTextureCoordinate(0, 3, new TexCoord2f(1.0f, 0.0f));

		QuadArray qaDark = new QuadArray(4, QuadArray.COORDINATES);

		qaDark.setCoordinate(0, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS, 0, -z-OUT_THICKNESS));
		qaDark.setCoordinate(1, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS, 0, -z-OUT_THICKNESS));
		qaDark.setCoordinate(2, new Point3d(-MIDDLE_POCKET_SIZE, height, -z));
		qaDark.setCoordinate(3, new Point3d(-x+CORNER_POCKET_SIZE, height, -z));

		TriangleArray ta = new TriangleArray(6, TriangleArray.COORDINATES);

		ta.setCoordinate(0, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS, 0, -z-OUT_THICKNESS));
		ta.setCoordinate(1, new Point3d(-x+CORNER_POCKET_SIZE, height, -z));
		ta.setCoordinate(2, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS, height+UP_THICKNESS, -z-OUT_THICKNESS));

		ta.setCoordinate(3, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS, 0, -z-OUT_THICKNESS));
		ta.setCoordinate(4, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS, height+UP_THICKNESS, -z-OUT_THICKNESS));
		ta.setCoordinate(5, new Point3d(-MIDDLE_POCKET_SIZE, height, -z));

		tg.addChild(new Shape3D(qa, ap1));
		tg.addChild(new Shape3D(qaDark, ap2));
		tg.addChild(new Shape3D(ta, ap2));
	}

	private static void addCushion4(double x, double z, double height, Appearance ap1, Appearance ap2) {
		QuadArray qa = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2);

		qa.setCoordinate(0, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS, height+UP_THICKNESS, z+OUT_THICKNESS));
		qa.setCoordinate(3, new Point3d(-x+CORNER_POCKET_SIZE, height, z));
		qa.setCoordinate(2, new Point3d(-MIDDLE_POCKET_SIZE, height, z));
		qa.setCoordinate(1, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS, height+UP_THICKNESS, z+OUT_THICKNESS));

		qa.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 3, new TexCoord2f(0.0f, 1.0f));
		qa.setTextureCoordinate(0, 2, new TexCoord2f(1.0f, 1.0f));
		qa.setTextureCoordinate(0, 1, new TexCoord2f(1.0f, 0.0f));

		QuadArray qaDark = new QuadArray(4, QuadArray.COORDINATES);

		qaDark.setCoordinate(0, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS, 0, z+OUT_THICKNESS));
		qaDark.setCoordinate(3, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS, 0, z+OUT_THICKNESS));
		qaDark.setCoordinate(2, new Point3d(-MIDDLE_POCKET_SIZE, height, z));
		qaDark.setCoordinate(1, new Point3d(-x+CORNER_POCKET_SIZE, height, z));

		TriangleArray ta = new TriangleArray(6, TriangleArray.COORDINATES);

		ta.setCoordinate(0, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS, 0, z+OUT_THICKNESS));
		ta.setCoordinate(2, new Point3d(-x+CORNER_POCKET_SIZE, height, z));
		ta.setCoordinate(1, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS, height+UP_THICKNESS, z+OUT_THICKNESS));

		ta.setCoordinate(3, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS, 0, z+OUT_THICKNESS));
		ta.setCoordinate(5, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS, height+UP_THICKNESS, z+OUT_THICKNESS));
		ta.setCoordinate(4, new Point3d(-MIDDLE_POCKET_SIZE, height, z));

		tg.addChild(new Shape3D(qa, ap1));
		tg.addChild(new Shape3D(qaDark, ap2));
		tg.addChild(new Shape3D(ta, ap2));
	}

	private static void addCushion5(double x, double z, double height, Appearance ap1, Appearance ap2) {
		QuadArray qa = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2);

		qa.setCoordinate(0, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS, height+UP_THICKNESS, -z-OUT_THICKNESS));
		qa.setCoordinate(3, new Point3d(x-CORNER_POCKET_SIZE, height, -z));
		qa.setCoordinate(2, new Point3d(MIDDLE_POCKET_SIZE, height, -z));
		qa.setCoordinate(1, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS, height+UP_THICKNESS, -z-OUT_THICKNESS));

		qa.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 3, new TexCoord2f(0.0f, 1.0f));
		qa.setTextureCoordinate(0, 2, new TexCoord2f(1.0f, 1.0f));
		qa.setTextureCoordinate(0, 1, new TexCoord2f(1.0f, 0.0f));

		QuadArray qaDark = new QuadArray(4, QuadArray.COORDINATES);

		qaDark.setCoordinate(0, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS, 0, -z-OUT_THICKNESS));
		qaDark.setCoordinate(3, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS, 0, -z-OUT_THICKNESS));
		qaDark.setCoordinate(2, new Point3d(MIDDLE_POCKET_SIZE, height, -z));
		qaDark.setCoordinate(1, new Point3d(x-CORNER_POCKET_SIZE, height, -z));

		TriangleArray ta = new TriangleArray(6, TriangleArray.COORDINATES);

		ta.setCoordinate(0, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS, 0, -z-OUT_THICKNESS));
		ta.setCoordinate(2, new Point3d(x-CORNER_POCKET_SIZE, height, -z));
		ta.setCoordinate(1, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS, height+UP_THICKNESS, -z-OUT_THICKNESS));

		ta.setCoordinate(3, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS, 0, -z-OUT_THICKNESS));
		ta.setCoordinate(5, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS, height+UP_THICKNESS, -z-OUT_THICKNESS));
		ta.setCoordinate(4, new Point3d(MIDDLE_POCKET_SIZE, height, -z));

		tg.addChild(new Shape3D(qa, ap1));
		tg.addChild(new Shape3D(qaDark, ap2));
		tg.addChild(new Shape3D(ta, ap2));
	}

	private static void addCushion6(double x, double z, double height, Appearance ap1, Appearance ap2) {
		QuadArray qa = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2);

		qa.setCoordinate(0, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS, height+UP_THICKNESS, z+OUT_THICKNESS));
		qa.setCoordinate(1, new Point3d(x-CORNER_POCKET_SIZE, height, z));
		qa.setCoordinate(2, new Point3d(MIDDLE_POCKET_SIZE, height, z));
		qa.setCoordinate(3, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS, height+UP_THICKNESS, z+OUT_THICKNESS));

		qa.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 1, new TexCoord2f(0.0f, 1.0f));
		qa.setTextureCoordinate(0, 2, new TexCoord2f(1.0f, 1.0f));
		qa.setTextureCoordinate(0, 3, new TexCoord2f(1.0f, 0.0f));

		QuadArray qaDark = new QuadArray(4, QuadArray.COORDINATES);

		qaDark.setCoordinate(0, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS, 0, z+OUT_THICKNESS));
		qaDark.setCoordinate(1, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS, 0, z+OUT_THICKNESS));
		qaDark.setCoordinate(2, new Point3d(MIDDLE_POCKET_SIZE, height, z));
		qaDark.setCoordinate(3, new Point3d(x-CORNER_POCKET_SIZE, height, z));

		TriangleArray ta = new TriangleArray(6, TriangleArray.COORDINATES);

		ta.setCoordinate(0, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS, 0, z+OUT_THICKNESS));
		ta.setCoordinate(1, new Point3d(x-CORNER_POCKET_SIZE, height, z));
		ta.setCoordinate(2, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS, height+UP_THICKNESS, z+OUT_THICKNESS));

		ta.setCoordinate(3, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS, 0, z+OUT_THICKNESS));
		ta.setCoordinate(4, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS, height+UP_THICKNESS, z+OUT_THICKNESS));
		ta.setCoordinate(5, new Point3d(MIDDLE_POCKET_SIZE, height, z));

		tg.addChild(new Shape3D(qa, ap1));
		tg.addChild(new Shape3D(qaDark, ap2));
		tg.addChild(new Shape3D(ta, ap2));
	}

}
