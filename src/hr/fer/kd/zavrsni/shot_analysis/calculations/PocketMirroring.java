package hr.fer.kd.zavrsni.shot_analysis.calculations;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.model.table.Cushion.CushionID;
import hr.fer.kd.zavrsni.model.table.Pocket;
import hr.fer.kd.zavrsni.shot_analysis.model.ACushion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PocketMirroring {

	private Pocket pocket;

	private Map<CushionID, ACushion> cushions;

	public PocketMirroring(Pocket pocket, Map<CushionID, ACushion> cushions) {
		super();
		this.pocket = pocket;
		this.cushions = cushions;
	}

	public List<PocketMirrored> getPoints(int numCushions) {
		List<PocketMirrored> points = new ArrayList<>();

		getPoints(points, cushions, pocket.getPoint(), numCushions, new ArrayList<ACushion>());

		return points;
	}

	private static void getPoints(List<PocketMirrored> points, Map<CushionID, ACushion> cushions,
			MyVector point, int numCushions, List<ACushion> lastCushions) {
		if (numCushions <= 0) {
			points.add(new PocketMirrored(point, lastCushions));
			return;
		}

		for (CushionID cid : cushions.keySet()) {
			if (!lastCushions.isEmpty() && cid == lastCushions.get(lastCushions.size() - 1).getId()) {
				continue;
			}

			ACushion c = cushions.get(cid);

			Map<CushionID, ACushion> cMirrored = new ACushionMirroring(cushions).getCushions(c);

			MyVector pMirrored;
			if (c.isHorizontal()) {
				pMirrored = new MyVector(point.getX(), 2 * c.getLevel() - point.getY());
			} else {
				pMirrored = new MyVector(2 * c.getLevel() - point.getX(), point.getY());
			}

			List<ACushion> lc = new ArrayList<>(lastCushions);
			lc.add(c);
			getPoints(points, cMirrored, pMirrored, numCushions - 1, lc);
		}
	}

	public static class PocketMirrored {

		private MyVector point;

		private List<ACushion> cushions;

		public PocketMirrored(MyVector point, List<ACushion> cushions) {
			super();
			this.point = point;
			this.cushions = cushions;
		}

		public MyVector getPoint() {
			return point;
		}

		public List<ACushion> getCushions() {
			return cushions;
		}

	}

}
