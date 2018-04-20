package hr.fer.kd.zavrsni.graphics.balls;

import hr.fer.kd.zavrsni.graphics.GraphicsUtil;
import hr.fer.kd.zavrsni.graphics.shotsimulator.BallSetContainer;
import hr.fer.kd.zavrsni.model.balls.Ball;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.BranchGroup;

public class GBallSet extends BranchGroup implements BallSetContainer {

	private List<GBall> children = new ArrayList<>();

	public GBallSet(List<Ball> balls) {
		super();

		for (Ball ball : balls) {
			if (!ball.isPotted()) {
				GBall gBall = new GBall(ball);
				children.add(gBall);
				gBall.setCapability(BranchGroup.ALLOW_DETACH);
				addChild(gBall);
			}
		}

		GraphicsUtil.enablePicking(this);

		setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	}

	@Override
	public void updateBallPositions() {
		for (GBall gBall : children) {
			boolean potted = gBall.updatePosition();
			if (potted && gBall.getParent() == this) {
				removeChild(gBall);
			}
			if (!potted && gBall.getParent() != this) {
				addChild(gBall);
			}
		}
	}

}
