package hr.fer.kd.zavrsni.presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainWindow extends JFrame implements IMainWindow {

	private static final long serialVersionUID = 969116463880372563L;

	private static final Point WINDOW_LOCATION = new Point(10, 10);
	private static final Dimension WINDOW_SIZE = new Dimension(800, 600);

	private Component component;
	private JMenuBar menuBar;
	private Component rightBar;

	public MainWindow() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}

		});

		setTitle("Sustav za analizu biljarske igre");
		setLocation(WINDOW_LOCATION);
		setSize(WINDOW_SIZE);

		initGUI();
	}

	private void exit() {
		System.exit(0);
	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
	}

	@Override
	public void setPreview(Component component) {
		if (this.component != null) {
			getContentPane().remove(this.component);
		}

		if (component != null) {
			getContentPane().add(component, BorderLayout.CENTER);
			this.component = component;
		}

		getContentPane().revalidate();
	}

	@Override
	public void setMenuBar(JMenuBar menuBar) {
		if (this.menuBar != null) {
			getContentPane().remove(this.menuBar);
		}

		if (menuBar != null) {
			getContentPane().add(menuBar, BorderLayout.PAGE_START);
			this.menuBar = menuBar;
		}

		getContentPane().revalidate();
	}

	@Override
	public void setRightBar(Component rightBar) {
		if (this.rightBar != null) {
			getContentPane().remove(this.rightBar);
		}

		if (rightBar != null) {
			getContentPane().add(rightBar, BorderLayout.LINE_END);
			this.rightBar = rightBar;
		}

		getContentPane().revalidate();
	}

	@Override
	public int confirmDialog(Component message, String title, int width, int height) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setLayout(new GridLayout(1, 1));
		panel.add(message);

		return JOptionPane.showOptionDialog(
				this, panel, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, null, 0);
	}

}
