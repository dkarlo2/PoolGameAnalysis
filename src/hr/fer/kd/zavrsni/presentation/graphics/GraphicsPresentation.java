package hr.fer.kd.zavrsni.presentation.graphics;

import hr.fer.kd.zavrsni.actions.PGRegularAction;
import hr.fer.kd.zavrsni.graphics.IGraphicsPresentation;
import hr.fer.kd.zavrsni.graphics.keyboard.GKeys;
import hr.fer.kd.zavrsni.graphics.picker.ISpeedSupply;
import hr.fer.kd.zavrsni.presentation.IMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.media.j3d.Canvas3D;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.KeyStroke;

public class GraphicsPresentation implements IGraphicsPresentation {

	private IMainWindow window;

	private JMenu menu;

	private JSlider speedSlider;

	public GraphicsPresentation(IMainWindow window) {
		super();
		this.window = window;
	}

	@Override
	public void newPreview(String name) {
		JMenuBar menuBar = new JMenuBar();
		menu = new JMenu(name);
		menuBar.add(menu);
		window.setMenuBar(menuBar);
		window.setPreview(null);
	}

	@Override
	public ISpeedSupply getISpeedSupply() {
		speedSlider = new JSlider(JSlider.VERTICAL, 1, 100, 1);
		speedSlider.setValue(50);
		window.setRightBar(speedSlider);
		return new ISpeedSupply() {

			@Override
			public int getSpeed() {
				return speedSlider.getValue();
			}
		};
	}

	@Override
	public void setCanvas(Canvas3D canvas) {
		window.setPreview(canvas);
	}

	@Override
	public void addRegularAction(final PGRegularAction action, GKeys key) {
		AbstractAction swingAction = new AbstractAction(action.getName()) {

			private static final long serialVersionUID = 4587686059354789214L;

			@Override
			public void actionPerformed(ActionEvent e) {
				action.actionPerformed();
			}
		};

		determineKey(swingAction, key);

		menu.add(new JMenuItem(swingAction));
	}

	private void determineKey(AbstractAction action, GKeys key) {
		if (key == null) {
			return;
		}

		KeyStroke keyStroke;
		switch (key) {
		case V:
			keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, 0);
			break;
		case ESCAPE:
			keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
			break;
		case CTRL_Z:
			keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK);
			break;
		default:
			throw new RuntimeException("Unknown graphic key!");
		}
		action.putValue(Action.ACCELERATOR_KEY, keyStroke);
	}

}
