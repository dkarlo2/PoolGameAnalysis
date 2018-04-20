package hr.fer.kd.zavrsni.actions;


public abstract class PGAbstractAction {

	private String name;

	public PGAbstractAction(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
