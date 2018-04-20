package hr.fer.kd.zavrsni.shot_analysis.calculations;

import hr.fer.kd.zavrsni.model.table.Cushion.CushionID;
import hr.fer.kd.zavrsni.shot_analysis.model.ACushion;

import java.util.HashMap;
import java.util.Map;

public class ACushionMirroring {

	private Map<CushionID, ACushion> cushions;

	public ACushionMirroring(Map<CushionID, ACushion> cushions) {
		super();
		this.cushions = cushions;
	}

	public Map<CushionID, ACushion> getCushions(ACushion cushion) {
		Map<CushionID, ACushion> mirrored = new HashMap<>();

		for (CushionID cid : cushions.keySet()) {
			ACushion c = cushions.get(cid);

			ACushion cMirrored = null;
			if (cushion.isHorizontal()) {
				if (c.isHorizontal()) {
					cMirrored = new ACushion(cid, 2 * cushion.getLevel() - c.getLevel(),
							c.getPoint1(), c.getPoint2());
				} else {
					cMirrored = new ACushion(cid, c.getLevel(),
							2 * cushion.getLevel() - c.getPoint1(), 2 * cushion.getLevel() - c.getPoint2());
				}
			} else {
				if (c.isHorizontal()) {
					cMirrored = new ACushion(cid, c.getLevel(),
							2 * cushion.getLevel() - c.getPoint1(), 2 * cushion.getLevel() - c.getPoint2());
				} else {
					cMirrored = new ACushion(cid, 2 * cushion.getLevel() - c.getLevel(),
							c.getPoint1(), c.getPoint2());
				}
			}

			mirrored.put(cid, cMirrored);
		}

		return mirrored;
	}

}
