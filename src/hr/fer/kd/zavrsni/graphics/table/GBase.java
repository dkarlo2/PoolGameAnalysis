package hr.fer.kd.zavrsni.graphics.table;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3d;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3f;

public class GBase {

	private static final double BASE_OFFSET = 0.04f;

	public static Shape3D getBase (double x, double z, Texture texture) {
		Appearance ap = new Appearance();

		TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparencyMode(TransparencyAttributes.BLENDED);
		ta.setTransparency(0);
		ap.setTransparencyAttributes(ta);

		Material mat = new Material(GTable.AMBIENT, GTable.EMISSIVE, GTable.DIFFUSE, GTable.SPECULAR, GTable.SHININESS);
		ap.setMaterial(mat);

		ap.setTexture(texture);

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		ap.setTextureAttributes(texAttr);

		QuadArray qa = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);
		qa.setCoordinate(0, new Point3d(-x-BASE_OFFSET,  0, -z-BASE_OFFSET));
		qa.setCoordinate(1, new Point3d(-x-BASE_OFFSET, 0, z+BASE_OFFSET));
		qa.setCoordinate(2, new Point3d(x+BASE_OFFSET, 0, z+BASE_OFFSET));
		qa.setCoordinate(3, new Point3d(x+BASE_OFFSET, 0, -z-BASE_OFFSET));

		qa.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
		qa.setTextureCoordinate(0, 1, new TexCoord2f(0.0f, 1f));
		qa.setTextureCoordinate(0, 2, new TexCoord2f(1f, 1f));
		qa.setTextureCoordinate(0, 3, new TexCoord2f(1f, 0.0f));

		Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f);
		qa.setNormal(0, normal);
		qa.setNormal(1, normal);
		qa.setNormal(2, normal);
		qa.setNormal(3, normal);

		return new Shape3D(qa, ap);
	}

}
