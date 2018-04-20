package hr.fer.kd.zavrsni.graphics.pockets;

import hr.fer.kd.zavrsni.graphics.GraphicsUtil;
import hr.fer.kd.zavrsni.model.table.Table;
import hr.fer.kd.zavrsni.model.table.Pocket.PocketID;

import java.util.Set;

import javax.media.j3d.BranchGroup;

public class GPocketSet extends BranchGroup {

	public GPocketSet(Table table) {
		super();

		Set<PocketID> pocketIDs = table.pockets.keySet();
		for (PocketID pocketID : pocketIDs) {
			GPocket gPocket = new GPocket(table, table.pockets.get(pocketID));
			addChild(gPocket);
		}

		GraphicsUtil.enablePicking(this);
	}

}
