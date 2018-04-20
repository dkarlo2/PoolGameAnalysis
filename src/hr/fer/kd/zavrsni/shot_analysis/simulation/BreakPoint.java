package hr.fer.kd.zavrsni.shot_analysis.simulation;

import java.util.ArrayList;
import java.util.List;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.graphics.painter.Shape;
import hr.fer.kd.zavrsni.model.balls.Ball;
import hr.fer.kd.zavrsni.model.table.Pocket;
import hr.fer.kd.zavrsni.shot_analysis.model.ACushion;

public class BreakPoint {

	public static enum BreakPointID {
		START,
		CUSHION_HIT,
		BALL_HIT,
		BALL_POTTED,
		ALL_STOPPED;
	}

	private BreakPointID bpid;

	private Object collObject;

	private Ball ball;

	private double time;

	private Resolver resolver;

	private List<Shape> shapes = new ArrayList<>();

	public BreakPoint(BreakPointID bpid) {
		this(null, bpid, null);
	}

	public BreakPoint(Ball ball, BreakPointID bpid, Object collObject) {
		this(ball, -1, bpid, collObject);
	}

	public BreakPoint(Ball ball, double time, BreakPointID bpid, Object collObject) {
		super();
		this.ball = ball;
		this.time = time;
		this.bpid = bpid;
		this.collObject = collObject;
	}

	public Ball getBall() {
		return ball;
	}

	public double getTime() {
		return time;
	}

	public BreakPointID getBpid() {
		return bpid;
	}

	public Object getCollObject() {
		return collObject;
	}

	public Resolver getResolver() {
		return resolver;
	}

	public void setResolver(Resolver resolver) {
		this.resolver = resolver;
	}

	public void addShape(Shape shape) {
		shapes.add(shape);
	}

	public List<Shape> getShapes() {
		return shapes;
	}

	public boolean isSameAs(BreakPoint other) {
		if (bpid != other.bpid) {
			return false;
		}

		switch (bpid) {
		case BALL_HIT:
			return ball.getNumber() == other.ball.getNumber()
			&& ((Ball) collObject).getNumber() == ((Ball) other.collObject).getNumber();
		case CUSHION_HIT:
			return ball.getNumber() == other.ball.getNumber()
			&& ((ACushion) collObject).getId() == ((ACushion) other.collObject).getId();
		case START:
			return ball.getNumber() == other.ball.getNumber()
			&& ((MyVector) collObject).equals((MyVector) other.collObject);
		case BALL_POTTED:
			return ball.getNumber() == other.ball.getNumber()
			&& ((Pocket) collObject).getID() == ((Pocket) other.collObject).getID();
		default:
			return true;
		}
	}

	@Override
	public String toString() {
		String col = null;
		switch (bpid) {
		case START:
			col = String.valueOf((MyVector)collObject);
			break;
		case CUSHION_HIT:
			col = String.valueOf(((ACushion)collObject).getId());
			break;
		case BALL_HIT:
			col = String.valueOf(((Ball)collObject).getNumber());
			break;
		case BALL_POTTED:
			col = String.valueOf(((Pocket)collObject).getID());
			break;
		case ALL_STOPPED:
			col = "";
			break;
		}
		if (bpid == BreakPointID.ALL_STOPPED) {
			return "ALL STOPPED";
		} else {
			return ball.getNumber() + " " + bpid + " " + col + " " + time;
		}
	}

	public static boolean allTheSame(List<BreakPoint> first, List<BreakPoint> second) {
		if (first.size() != second.size()) {
			return false;
		}

		for (int i = 0; i < first.size(); i++) {
			if (!first.get(i).isSameAs(second.get(i))) {
				return false;
			}
		}

		return true;
	}

}
