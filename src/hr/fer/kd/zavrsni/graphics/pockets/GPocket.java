package hr.fer.kd.zavrsni.graphics.pockets;

import hr.fer.kd.zavrsni.graphics.GraphicsUtil;
import hr.fer.kd.zavrsni.graphics.table.GTable;
import hr.fer.kd.zavrsni.model.table.Pocket;
import hr.fer.kd.zavrsni.model.table.Table;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3d;

public class GPocket extends BranchGroup {

	private static final double OUT = GTable.OUT_THICKNESS;
	private final double CDELTA;
	private final double MDELTA;

	private Pocket pocket;
	private Point3d center;

	public GPocket(Table table, Pocket pocket) {
		this.pocket = pocket;
		CDELTA = GraphicsUtil.getGF() * table.CORNER_POCKET_SIZE;
		MDELTA = GraphicsUtil.getGF() * table.MIDDLE_POCKET_SIZE;

		Appearance ap = new Appearance();

		TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparencyMode(TransparencyAttributes.BLENDED);
		ta.setTransparency(1f);
		ap.setTransparencyAttributes(ta);

		QuadArray qa = new QuadArray(4, QuadArray.COORDINATES);

		setCoordinates(qa, table);

		addChild(new Shape3D(qa, ap));
	}

	private void setCoordinates(QuadArray qa, Table table) {
		double x = GraphicsUtil.getGF() * table.LENGTH/2;
		double z = GraphicsUtil.getGF() * table.WIDTH/2;

		switch (pocket.getID()) {
		case UL:
			qa.setCoordinate(0, new Point3d(-x-OUT, 0, -z-OUT));
			qa.setCoordinate(1, new Point3d(-x-OUT, 0, -z+CDELTA));
			qa.setCoordinate(2, new Point3d(-x+CDELTA, 0, -z+CDELTA));
			qa.setCoordinate(3, new Point3d(-x+CDELTA, 0, -z-OUT));
			center = new Point3d(-x, 0, -z);
			break;
		case UM:
			qa.setCoordinate(0, new Point3d(-MDELTA, 0, -z-OUT));
			qa.setCoordinate(1, new Point3d(-MDELTA, 0, -z+MDELTA));
			qa.setCoordinate(2, new Point3d(MDELTA, 0, -z+MDELTA));
			qa.setCoordinate(3, new Point3d(MDELTA, 0, -z-OUT));
			center = new Point3d(0, 0, -z-OUT/2);
			break;
		case UR:
			qa.setCoordinate(0, new Point3d(x+OUT, 0, -z-OUT));
			qa.setCoordinate(1, new Point3d(x-CDELTA, 0, -z-OUT));
			qa.setCoordinate(2, new Point3d(x-CDELTA, 0, -z+CDELTA));
			qa.setCoordinate(3, new Point3d(x+OUT, 0, -z+CDELTA));
			center = new Point3d(x, 0, -z);
			break;
		case DR:
			qa.setCoordinate(0, new Point3d(x+OUT, 0, z+OUT));
			qa.setCoordinate(1, new Point3d(x+OUT, 0, z-CDELTA));
			qa.setCoordinate(2, new Point3d(x-CDELTA, 0, z-CDELTA));
			qa.setCoordinate(3, new Point3d(x-CDELTA, 0, z+OUT));
			center = new Point3d(x, 0, z);
			break;
		case DM:
			qa.setCoordinate(0, new Point3d(-MDELTA, 0, z+OUT));
			qa.setCoordinate(1, new Point3d(MDELTA, 0, z+OUT));
			qa.setCoordinate(2, new Point3d(MDELTA, 0, z-MDELTA));
			qa.setCoordinate(3, new Point3d(-MDELTA, 0, z-MDELTA));
			center = new Point3d(0, 0, z+OUT/2);
			break;
		case DL:
			qa.setCoordinate(0, new Point3d(-x-OUT, 0, z+OUT));
			qa.setCoordinate(1, new Point3d(-x+CDELTA, 0, z+OUT));
			qa.setCoordinate(2, new Point3d(-x+CDELTA, 0, z-CDELTA));
			qa.setCoordinate(3, new Point3d(-x-OUT, 0, z-CDELTA));
			center = new Point3d(-x, 0, z);
			break;
		}
	}

	public Pocket getPocket() {
		return pocket;
	}

	public Point3d getCenter() {
		return center;
	}

}
