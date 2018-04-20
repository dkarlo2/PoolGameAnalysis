package hr.fer.kd.zavrsni.model.table;

import hr.fer.kd.mymath.model.MyVector;

public class Pocket {

	/**
	 * Enumeracija koja popisuje ID-eve rupa.
	 * Navedene su u krug pocevsi od gornje lijeve
	 * (gledajuci stol horizontalno tako da je s desne strane pocetna tocka bijele.)
	 * @author Karlo
	 *
	 */
	public static enum PocketID {
		UL, UM, UR, DR, DM, DL;
	}

	private PocketID id;

	private MyVector point;

	public Pocket(PocketID id, MyVector point) {
		super();
		this.id = id;
		this.point = point;
	}

	public PocketID getID() {
		return id;
	}

	public MyVector getPoint() {
		return point;
	}

}
