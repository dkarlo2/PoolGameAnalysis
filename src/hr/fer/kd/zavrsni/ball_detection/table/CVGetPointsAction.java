package hr.fer.kd.zavrsni.ball_detection.table;

import hr.fer.kd.zavrsni.actions.PGAbstractAction;

import org.opencv.core.Point;

public abstract class CVGetPointsAction extends PGAbstractAction {

	public CVGetPointsAction(String name) {
		super(name);
	}

	public abstract void actionPerformed(Point[] points);

}
