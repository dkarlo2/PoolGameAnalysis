package hr.fer.kd.zavrsni.graphics.table;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Point3d;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3f;

public class GFrame {

	private static final double OUT_THICKNESS = GTable.OUT_THICKNESS;
	private static final double UP_THICKNESS = GTable.UP_THICKNESS;
	private static final double WOOD_THICKNESS = GTable.WOOD_THICKNESS;
	private static final double TABLE_HEIGHT = GTable.TABLE_HEIGHT;

	public static Shape3D getFrame(double x, double z, double height, Texture textureWood) {
		Appearance ap = new Appearance();

		PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);
		ap.setPolygonAttributes(pa);

		Material mat = new Material(GTable.AMBIENT, GTable.EMISSIVE, GTable.DIFFUSE, GTable.SPECULAR, GTable.SHININESS);
		ap.setMaterial(mat);

		ap.setTexture(textureWood);

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		ap.setTextureAttributes(texAttr);

		QuadArray qa = new QuadArray(20, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);

		qa.setCoordinate(0, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(1, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				-TABLE_HEIGHT, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(2, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				-TABLE_HEIGHT, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa.setCoordinate(3, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));

		qa.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 1, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 2, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 3, new TexCoord2f(1f, 0.0f));

		Vector3f normal1 = new Vector3f(-1.0f, 0.0f, 0.0f);
		qa.setNormal(0, normal1);
		qa.setNormal(1, normal1);
		qa.setNormal(2, normal1);
		qa.setNormal(3, normal1);

		qa.setCoordinate(4, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(5, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(6, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				-TABLE_HEIGHT, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(7, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				-TABLE_HEIGHT, -z-OUT_THICKNESS-WOOD_THICKNESS));

		qa.setTextureCoordinate(0, 4, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 7, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 6, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 5, new TexCoord2f(1f, 0.0f));

		Vector3f normal2 = new Vector3f(0.0f, 0.0f, 1.0f);
		qa.setNormal(4, normal2);
		qa.setNormal(5, normal2);
		qa.setNormal(6, normal2);
		qa.setNormal(7, normal2);

		qa.setCoordinate(8, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				height+UP_THICKNESS, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(11, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				-TABLE_HEIGHT, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(10, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				-TABLE_HEIGHT, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa.setCoordinate(9, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));

		qa.setTextureCoordinate(0, 8, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 11, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 10, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 9, new TexCoord2f(1f, 0.0f));

		Vector3f normal3 = new Vector3f(-1.0f, 0.0f, 0.0f);
		qa.setNormal(8, normal3);
		qa.setNormal(9, normal3);
		qa.setNormal(10, normal3);
		qa.setNormal(11, normal3);

		qa.setCoordinate(12, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa.setCoordinate(15, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				height+UP_THICKNESS, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa.setCoordinate(14, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				-TABLE_HEIGHT, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa.setCoordinate(13, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				-TABLE_HEIGHT, z+OUT_THICKNESS+WOOD_THICKNESS));

		qa.setTextureCoordinate(0, 12, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 13, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 14, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 15, new TexCoord2f(1f, 0.0f));

		Vector3f normal4 = new Vector3f(0.0f, 0.0f, 1.0f);
		qa.setNormal(12, normal4);
		qa.setNormal(13, normal4);
		qa.setNormal(14, normal4);
		qa.setNormal(15, normal4);

		qa.setCoordinate(16, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				-TABLE_HEIGHT, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(17, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				-TABLE_HEIGHT, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(18, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS,
				-TABLE_HEIGHT, +z+OUT_THICKNESS+WOOD_THICKNESS));
		qa.setCoordinate(19, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS,
				-TABLE_HEIGHT, z+OUT_THICKNESS+WOOD_THICKNESS));

		qa.setTextureCoordinate(0, 16, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 17, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 18, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 19, new TexCoord2f(1f, 0.0f));

		return new Shape3D(qa, ap);
	}

}
