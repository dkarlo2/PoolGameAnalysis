package hr.fer.kd.zavrsni.graphics.table;

import hr.fer.kd.zavrsni.graphics.GraphicsUtil;
import hr.fer.kd.zavrsni.model.table.Table;

import java.awt.Container;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.image.TextureLoader;

public class GTable extends TransformGroup {

	public static final Color3f AMBIENT = new Color3f(1, 1, 1);
	public static final Color3f EMISSIVE = new Color3f();
	public static final Color3f DIFFUSE = AMBIENT;
	public static final Color3f SPECULAR = new Color3f();
	public static final float SHININESS = 1f;

	public static final double OUT_THICKNESS = 0.05f;
	public static final double MOUT_THICKNESS = 0.015f;

	public static final double UP_THICKNESS = 0.01f;

	public static final double WOOD_THICKNESS = 2 * OUT_THICKNESS;

	public static final double TABLE_HEIGHT = 0.1f;

	private BranchGroup plate;

	public GTable(Table table) {
		super();

		setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		double x = GraphicsUtil.getGF() * table.LENGTH/2;
		double z = GraphicsUtil.getGF() * table.WIDTH/2;

		double height = GraphicsUtil.getGF() * table.BALL_RADIUS * 4/3;

		TextureLoader loader1 = new TextureLoader("Textures/cloth.png", "RGBA", new Container());
		Texture texture = loader1.getTexture();

		TextureLoader loader2 = new TextureLoader("Textures/clothMini.png", new Container());
		Texture textureMini = loader2.getTexture();

		TextureLoader loader3 = new TextureLoader("Textures/clothMiniDark.png", new Container());
		Texture textureDark = loader3.getTexture();

		TextureLoader loader4 = new TextureLoader("Textures/wood.png", new Container());
		Texture textureWood = loader4.getTexture();

		TextureLoader loader5 = new TextureLoader("Textures/wood1.png", new Container());
		Texture textureWood1 = loader5.getTexture();

		TextureLoader loader6 = new TextureLoader("Textures/wood2.png", new Container());
		Texture textureWood2 = loader6.getTexture();

		addChild(GBase.getBase(x, z, texture));
		addChild(GSurround.getSurround(x, z, textureDark, table));
		addChild(GCushions.getCushions(x, z, height, textureMini, textureDark, table));
		addChild(GWood.getWood(x, z, height, textureWood, textureWood1, textureWood2, table));
		addChild(GFrame.getFrame(x, z, height, textureWood));

		initPlate(x, z);
		addChild(plate);
	}

	public BranchGroup getPlate() {
		return plate;
	}

	private void initPlate(double x, double z) {
		Appearance ap = new Appearance();

		TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparencyMode(TransparencyAttributes.BLENDED);
		ta.setTransparency(1f);
		ap.setTransparencyAttributes(ta);

		QuadArray qa = new QuadArray(4, QuadArray.COORDINATES);

		qa.setCoordinate(0, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS, 0, -z-OUT_THICKNESS-WOOD_THICKNESS));
		qa.setCoordinate(1, new Point3d(-x-OUT_THICKNESS-WOOD_THICKNESS, 0, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa.setCoordinate(2, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS, 0, z+OUT_THICKNESS+WOOD_THICKNESS));
		qa.setCoordinate(3, new Point3d(x+OUT_THICKNESS+WOOD_THICKNESS, 0, -z-OUT_THICKNESS-WOOD_THICKNESS));

		plate = new BranchGroup();
		plate.addChild(new Shape3D(qa, ap));
	}

}
