package hr.fer.kd.zavrsni.model.table;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.model.table.Cushion.CushionID;
import hr.fer.kd.zavrsni.model.table.Pocket.PocketID;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Table {

	public final double LENGTH;

	public final double WIDTH;

	public final Color COLOR; // TODO ne koristi se nigdje

	public final double BALL_RADIUS;

	public final double ADOTX;
	public final double ADOTY;
	public final double BDOTX;
	public final double BDOTY;

	public final MyVector POSITION_BW;
	public final MyVector POSITION_B1;
	public final MyVector POSITION_B2;
	public final MyVector POSITION_B3;
	public final MyVector POSITION_B4;
	public final MyVector POSITION_B5;
	public final MyVector POSITION_B6;
	public final MyVector POSITION_B7;
	public final MyVector POSITION_B8;
	public final MyVector POSITION_B9;
	public final MyVector POSITION_B10;
	public final MyVector POSITION_B11;
	public final MyVector POSITION_B12;
	public final MyVector POSITION_B13;
	public final MyVector POSITION_B14;
	public final MyVector POSITION_B15;

	public final double MAX_X;
	public final double MIN_X;
	public final double MAX_Y;
	public final double MIN_Y;

	public final double CORNER_POCKET_SIZE;
	public final double MIDDLE_POCKET_SIZE;

	public final Map<CushionID, Cushion> cushions;

	public final Map<PocketID, Pocket> pockets;

	public Table(double LENGTH, double WIDTH, Color COLOR, double R) {
		super();
		this.LENGTH = LENGTH;
		this.WIDTH = WIDTH;
		this.COLOR = COLOR;
		this.BALL_RADIUS = R;

		ADOTX = 0.27167f * LENGTH;
		ADOTY = 0;
		BDOTX = -ADOTX;
		BDOTY = 0;

		double R3 = R * Math.sqrt(3);

		POSITION_BW = new MyVector(ADOTX, ADOTY);
		POSITION_B1 = new MyVector(BDOTX, BDOTY);
		POSITION_B2 = new MyVector(BDOTX-2*R3, BDOTY-2*R);
		POSITION_B3 = new MyVector(BDOTX-4*R3, BDOTY-4*R);
		POSITION_B4 = new MyVector(BDOTX-4*R3, BDOTY);
		POSITION_B5 = new MyVector(BDOTX-4*R3, BDOTY+4*R);
		POSITION_B6 = new MyVector(BDOTX-2*R3, BDOTY+2*R);
		POSITION_B7 = new MyVector(BDOTX-3*R3, BDOTY-R);
		POSITION_B8 = new MyVector(BDOTX-2*R3, BDOTY);
		POSITION_B9 = new MyVector(BDOTX-3*R3, BDOTY+R);
		POSITION_B10 = new MyVector(BDOTX-R3, BDOTY+R);
		POSITION_B11 = new MyVector(BDOTX-3*R3, BDOTY+3*R);
		POSITION_B12 = new MyVector(BDOTX-4*R3, BDOTY+2*R);
		POSITION_B13 = new MyVector(BDOTX-4*R3, BDOTY-2*R);
		POSITION_B14 = new MyVector(BDOTX-3*R3, BDOTY-3*R);
		POSITION_B15 = new MyVector(BDOTX-R3, BDOTY-R);

		MAX_X = LENGTH/2;
		MIN_X = -MAX_X;
		MAX_Y = WIDTH/2;
		MIN_Y = -MAX_Y;

		CORNER_POCKET_SIZE = 0.03f * LENGTH;
		MIDDLE_POCKET_SIZE = 0.02386781f * LENGTH;

		Map<CushionID, Cushion> map1 = new HashMap<>();
		map1.put(CushionID.UL, new Cushion(CushionID.UL, MIN_Y, MIN_X+CORNER_POCKET_SIZE, -MIDDLE_POCKET_SIZE));
		map1.put(CushionID.UR, new Cushion(CushionID.UR, MIN_Y, MIDDLE_POCKET_SIZE, MAX_X-CORNER_POCKET_SIZE));
		map1.put(CushionID.R, new Cushion(CushionID.R, MAX_X, MIN_Y+CORNER_POCKET_SIZE, MAX_Y-CORNER_POCKET_SIZE));
		map1.put(CushionID.DR, new Cushion(CushionID.DR, MAX_Y, MAX_X-CORNER_POCKET_SIZE, MIDDLE_POCKET_SIZE));
		map1.put(CushionID.DL, new Cushion(CushionID.DL, MAX_Y, -MIDDLE_POCKET_SIZE, MIN_X+CORNER_POCKET_SIZE));
		map1.put(CushionID.L, new Cushion(CushionID.L, MIN_X, MAX_Y-CORNER_POCKET_SIZE, MIN_Y+CORNER_POCKET_SIZE));
		cushions = Collections.unmodifiableMap(map1);

		Map<PocketID, Pocket> map2 = new HashMap<>();
		map2.put(PocketID.UL, new Pocket(PocketID.UL, new MyVector(MIN_X+R, MIN_Y+R)));
		map2.put(PocketID.UM, new Pocket(PocketID.UM, new MyVector(0, MIN_Y)));
		map2.put(PocketID.UR, new Pocket(PocketID.UR, new MyVector(MAX_X-R, MIN_Y+R)));
		map2.put(PocketID.DR, new Pocket(PocketID.DR, new MyVector(MAX_X-R, MAX_Y-R)));
		map2.put(PocketID.DM, new Pocket(PocketID.DM, new MyVector(0, MAX_Y)));
		map2.put(PocketID.DL, new Pocket(PocketID.DL, new MyVector(MIN_X+R, MAX_Y-R)));
		pockets = Collections.unmodifiableMap(map2);
	}

}
