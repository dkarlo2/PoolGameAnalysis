package hr.fer.kd.zavrsni.shot_analysis.model;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.model.table.Cushion;
import hr.fer.kd.zavrsni.model.table.Cushion.CushionID;

public class ACushion {

	private CushionID id;

	private double level;

	private double point1;

	private double point2;

	public ACushion(Cushion cushion, double ballRadius) {
		this(cushion.getId(),
				cushion.getLevel() < 0 ? cushion.getLevel() + ballRadius : cushion.getLevel() - ballRadius,
						cushion.getPoint1(), cushion.getPoint2());
	}

	public ACushion(CushionID id, double level, double point1, double point2) {
		super();
		this.id = id;
		this.level = level;
		this.point1 = point1;
		this.point2 = point2;
	}

	public boolean isHorizontal() {
		return id.isHorizontal();
	}

	public CushionID getId() {
		return id;
	}

	public double getLevel() {
		return level;
	}

	public double getPoint1() {
		return point1;
	}

	public double getPoint2() {
		return point2;
	}

	public MyVector[] getPoints() {
		if (id.isHorizontal()) {
			return new MyVector[]{new MyVector(point1, level), new MyVector(point2, level)};
		} else {
			return new MyVector[]{new MyVector(level, point1), new MyVector(level, point2)};
		}
	}

}
