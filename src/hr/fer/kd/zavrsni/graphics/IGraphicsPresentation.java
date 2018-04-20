package hr.fer.kd.zavrsni.graphics;

import hr.fer.kd.zavrsni.actions.PGRegularAction;
import hr.fer.kd.zavrsni.graphics.keyboard.GKeys;
import hr.fer.kd.zavrsni.graphics.picker.ISpeedSupply;

import javax.media.j3d.Canvas3D;

public interface IGraphicsPresentation {

	void newPreview(String name);

	void setCanvas(Canvas3D canvas);

	void addRegularAction(PGRegularAction action, GKeys key);

	ISpeedSupply getISpeedSupply();

}
