package hr.fer.kd.zavrsni.model.table;



public class Cushion {

	/**
	 * Enumeracija koja popisuje ID-eve mantinela.
	 * Navedene su u krug pocevsi od gornje lijeve
	 * (gledajuci stol horizontalno tako da je s desne strane pocetna tocka bijele.)
	 * @author Karlo
	 *
	 */
	public static enum CushionID {

		UL(true),
		UR(true),
		R(false),
		DR(true),
		DL(true),
		L(false);

		private boolean horizontal;

		private CushionID(boolean horizontal) {
			this.horizontal = horizontal;
		}

		public boolean isHorizontal() {
			return horizontal;
		}

	}

	private CushionID id;

	private double level;

	private double point1;

	private double point2;

	public Cushion(CushionID id, double level, double point1, double point2) {
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

}
