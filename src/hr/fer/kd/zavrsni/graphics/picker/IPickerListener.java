package hr.fer.kd.zavrsni.graphics.picker;

import java.util.LinkedList;
import java.util.List;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.model.balls.Ball;
import hr.fer.kd.zavrsni.model.table.Pocket;
import hr.fer.kd.zavrsni.shot_analysis.simulation.BreakPoint;

public interface IPickerListener {

	LinkedList<List<BreakPoint>> potPicked(Ball ball, Pocket pocket);

	List<BreakPoint> shotPicked(Ball ball, MyVector direction, int speed);

	void simulate(List<BreakPoint> breakPoints);

}
