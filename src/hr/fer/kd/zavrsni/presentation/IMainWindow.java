package hr.fer.kd.zavrsni.presentation;

import java.awt.Component;

import javax.swing.JMenuBar;

public interface IMainWindow {

	void setPreview(Component component);

	void setMenuBar(JMenuBar menuBar);

	int confirmDialog(Component message, String title, int width, int height);

	void setRightBar(Component component);

}
