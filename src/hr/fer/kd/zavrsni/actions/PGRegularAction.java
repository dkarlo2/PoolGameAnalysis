package hr.fer.kd.zavrsni.actions;

public abstract class PGRegularAction extends PGAbstractAction {

	public PGRegularAction(String name) {
		super(name);
	}

	public abstract void actionPerformed();

}
