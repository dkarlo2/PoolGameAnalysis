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
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3d;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3f;

public class GWood {

	private static TransformGroup tg;

	private static final double OUT_THICKNESS = GTable.OUT_THICKNESS;
	private static final double MOUT_THICKNESS = GTable.MOUT_THICKNESS;
	private static final double UP_THICKNESS = GTable.UP_THICKNESS;
	private static final double WOOD_THICKNESS = GTable.WOOD_THICKNESS;

	private static double CORNER_POCKET_SIZE;
	private static double MIDDLE_POCKET_SIZE;

	public static TransformGroup getWood(double x, double z, double height,
			Texture textureWood, Texture textureWood1, Texture textureWood2, Table table) {
		CORNER_POCKET_SIZE = GraphicsUtil.getGF() * table.CORNER_POCKET_SIZE;
		MIDDLE_POCKET_SIZE = GraphicsUtil.getGF() * table.MIDDLE_POCKET_SIZE;

		tg = new TransformGroup();

		// wood

		Appearance ap = new Appearance();

		TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparencyMode(TransparencyAttributes.BLENDED);
		ta.setTransparency(0);
		ap.setTransparencyAttributes(ta);

		Material mat = new Material(GTable.AMBIENT, GTable.EMISSIVE, GTable.DIFFUSE, GTable.SPECULAR, GTable.SHININESS);
		ap.setMaterial(mat);

		ap.setTexture(textureWood);

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		ap.setTextureAttributes(texAttr);

		QuadArray qa = new QuadArray(24, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);

		Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f);

		qa.setCoordinate(0, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(1, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS));
		qa.setCoordinate(2, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS));
		qa.setCoordinate(3, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));

		qa.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 1, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 2, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 3, new TexCoord2f(1f, 0.0f));

		qa.setNormal(0, normal);
		qa.setNormal(1, normal);
		qa.setNormal(2, normal);
		qa.setNormal(3, normal);

		qa.setCoordinate(4, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(7, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS));
		qa.setCoordinate(6, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS));
		qa.setCoordinate(5, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));

		qa.setTextureCoordinate(0, 4, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 7, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 6, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 5, new TexCoord2f(1f, 0.0f));

		qa.setNormal(4, normal);
		qa.setNormal(7, normal);
		qa.setNormal(6, normal);
		qa.setNormal(5, normal);

		qa.setCoordinate(8, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa.setCoordinate(11, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS));
		qa.setCoordinate(10, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS));
		qa.setCoordinate(9, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));

		qa.setTextureCoordinate(0, 8, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 11, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 10, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 9, new TexCoord2f(1f, 0.0f));

		qa.setNormal(8, normal);
		qa.setNormal(11, normal);
		qa.setNormal(10, normal);
		qa.setNormal(9, normal);

		qa.setCoordinate(12, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa.setCoordinate(13, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS));
		qa.setCoordinate(14, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS));
		qa.setCoordinate(15, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));

		qa.setTextureCoordinate(0, 12, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 13, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 14, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 15, new TexCoord2f(1f, 0.0f));

		qa.setNormal(12, normal);
		qa.setNormal(13, normal);
		qa.setNormal(14, normal);
		qa.setNormal(15, normal);

		qa.setCoordinate(16, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));
		qa.setCoordinate(17, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		qa.setCoordinate(18, new Point3d(-x-OUT_THICKNESS,
				height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		qa.setCoordinate(19, new Point3d(-x-OUT_THICKNESS,
				height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));

		qa.setTextureCoordinate(0, 16, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 19, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 18, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 17, new TexCoord2f(1f, 0.0f));

		qa.setNormal(16, normal);
		qa.setNormal(17, normal);
		qa.setNormal(18, normal);
		qa.setNormal(19, normal);

		qa.setCoordinate(20, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));
		qa.setCoordinate(23, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		qa.setCoordinate(22, new Point3d(x+OUT_THICKNESS,
				height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		qa.setCoordinate(21, new Point3d(x+OUT_THICKNESS,
				height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));

		qa.setTextureCoordinate(0, 20, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 21, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 22, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 23, new TexCoord2f(1f, 0.0f));

		qa.setNormal(20, normal);
		qa.setNormal(21, normal);
		qa.setNormal(22, normal);
		qa.setNormal(23, normal);

		tg.addChild(new Shape3D(qa, ap));


		// wood1

		Appearance ap1 = new Appearance();

		ap1.setTransparencyAttributes(ta);

		ap1.setMaterial(mat);

		ap1.setTexture(textureWood1);

		ap1.setTextureAttributes(texAttr);

		QuadArray qa1 = new QuadArray(16, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);

		qa1.setCoordinate(0, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS,
				height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));
		qa1.setCoordinate(1, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa1.setCoordinate(2, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa1.setCoordinate(3, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));

		qa1.setTextureCoordinate(0, 3, new TexCoord2f(0.01f, 0.01f));
		qa1.setTextureCoordinate(0, 0, new TexCoord2f(0.01f, .99f));
		qa1.setTextureCoordinate(0, 1, new TexCoord2f(.99f, .99f));
		qa1.setTextureCoordinate(0, 2, new TexCoord2f(.99f, 0.01f));

		qa1.setNormal(0, normal);
		qa1.setNormal(1, normal);
		qa1.setNormal(2, normal);
		qa1.setNormal(3, normal);

		qa1.setCoordinate(4, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS,
				height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));
		qa1.setCoordinate(7, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa1.setCoordinate(6, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa1.setCoordinate(5, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				height+UP_THICKNESS, -z+CORNER_POCKET_SIZE-OUT_THICKNESS));

		qa1.setTextureCoordinate(0, 5, new TexCoord2f(0.01f, 0.01f));
		qa1.setTextureCoordinate(0, 4, new TexCoord2f(0.01f, .99f));
		qa1.setTextureCoordinate(0, 7, new TexCoord2f(.99f, .99f));
		qa1.setTextureCoordinate(0, 6, new TexCoord2f(.99f, 0.01f));

		qa1.setNormal(4, normal);
		qa1.setNormal(7, normal);
		qa1.setNormal(6, normal);
		qa1.setNormal(5, normal);

		qa1.setCoordinate(8, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS,
				height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		qa1.setCoordinate(11, new Point3d(-x+CORNER_POCKET_SIZE-OUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa1.setCoordinate(10, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa1.setCoordinate(9, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));

		qa1.setTextureCoordinate(0, 9, new TexCoord2f(0.01f, 0.01f));
		qa1.setTextureCoordinate(0, 8, new TexCoord2f(0.01f, .99f));
		qa1.setTextureCoordinate(0, 11, new TexCoord2f(.99f, .99f));
		qa1.setTextureCoordinate(0, 10, new TexCoord2f(.99f, 0.01f));

		qa1.setNormal(8, normal);
		qa1.setNormal(11, normal);
		qa1.setNormal(10, normal);
		qa1.setNormal(9, normal);

		qa1.setCoordinate(12, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS,
				height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));
		qa1.setCoordinate(13, new Point3d(x-CORNER_POCKET_SIZE+OUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa1.setCoordinate(14, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa1.setCoordinate(15, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				height+UP_THICKNESS, z-CORNER_POCKET_SIZE+OUT_THICKNESS));

		qa1.setTextureCoordinate(0, 13, new TexCoord2f(0.01f, 0.01f));
		qa1.setTextureCoordinate(0, 12, new TexCoord2f(0.01f, .99f));
		qa1.setTextureCoordinate(0, 15, new TexCoord2f(.99f, .99f));
		qa1.setTextureCoordinate(0, 14, new TexCoord2f(.99f, 0.01f));

		qa1.setNormal(12, normal);
		qa1.setNormal(13, normal);
		qa1.setNormal(14, normal);
		qa1.setNormal(15, normal);

		tg.addChild(new Shape3D(qa1, ap1));


		// wood 2

		Appearance ap2 = new Appearance();

		ap2.setTransparencyAttributes(ta);

		ap2.setMaterial(mat);

		ap2.setTexture(textureWood2);

		ap2.setTextureAttributes(texAttr);

		QuadArray qa2 = new QuadArray(8, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);

		qa2.setCoordinate(0, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS));
		qa2.setCoordinate(1, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS));
		qa2.setCoordinate(2, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa2.setCoordinate(3, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));

		qa2.setTextureCoordinate(0, 1, new TexCoord2f(0.01f, 0.01f));
		qa2.setTextureCoordinate(0, 2, new TexCoord2f(0.01f, .99f));
		qa2.setTextureCoordinate(0, 3, new TexCoord2f(.99f, .99f));
		qa2.setTextureCoordinate(0, 0, new TexCoord2f(.99f, 0.01f));

		qa2.setNormal(0, normal);
		qa2.setNormal(1, normal);
		qa2.setNormal(2, normal);
		qa2.setNormal(3, normal);

		qa2.setCoordinate(4, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS));
		qa2.setCoordinate(7, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS));
		qa2.setCoordinate(6, new Point3d(MIDDLE_POCKET_SIZE-MOUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa2.setCoordinate(5, new Point3d(-MIDDLE_POCKET_SIZE+MOUT_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));

		qa2.setTextureCoordinate(0, 7, new TexCoord2f(0.01f, 0.01f));
		qa2.setTextureCoordinate(0, 6, new TexCoord2f(0.01f, .99f));
		qa2.setTextureCoordinate(0, 5, new TexCoord2f(.99f, .99f));
		qa2.setTextureCoordinate(0, 4, new TexCoord2f(.99f, 0.01f));

		qa2.setNormal(4, normal);
		qa2.setNormal(7, normal);
		qa2.setNormal(6, normal);
		qa2.setNormal(5, normal);

		tg.addChild(new Shape3D(qa2, ap2));

		return tg;
	}

}
