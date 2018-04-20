package hr.fer.kd.zavrsni.presentation.cv;

import hr.fer.kd.zavrsni.actions.PGRegularAction;
import hr.fer.kd.zavrsni.ball_detection.ICVPresentation;
import hr.fer.kd.zavrsni.ball_detection.cv.CVKeys;
import hr.fer.kd.zavrsni.ball_detection.table.CVGetPointsAction;
import hr.fer.kd.zavrsni.presentation.IMainWindow;
import hr.fer.kd.zavrsni.presentation.cv.image_display.MatImageDisplayer;
import hr.fer.kd.zavrsni.presentation.cv.painter.RectanglePainter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public class CVPresentation implements ICVPresentation {

	private static final int CONFIRM_DIALOG_WIDTH = 600;

	private IMainWindow window;

	private MatImageDisplayer display;

	private PointsTranslator pointsTranslator;

	private JMenu menu;

	public CVPresentation(IMainWindow window) {
		this.window = window;
	}

	@Override
	public void newPreview(String name) {
		JMenuBar menuBar = new JMenuBar();
		menu = new JMenu(name);
		menuBar.add(menu);
		window.setMenuBar(menuBar);
		display = new MatImageDisplayer();
		window.setPreview(display);
	}

	@Override
	public void setImage(Mat image) {
		display.display(image);
		window.setPreview(display);
	}

	@Override
	public void getInputPoints(Point[] suggestedPoints, final CVGetPointsAction action, CVKeys key) {
		pointsTranslator = new PointsTranslator(suggestedPoints, display);

		AbstractAction swingAction = new AbstractAction(action.getName()) {

			private static final long serialVersionUID = -4868160796045114617L;

			@Override
			public void actionPerformed(ActionEvent e) {
				action.actionPerformed(pointsTranslator.getPoints());
			}
		};

		determineKey(swingAction, key);

		menu.add(new JMenuItem(swingAction));
	}

	@Override
	public void setSuggestedPoints(Point[] suggestedPoints) {
		pointsTranslator.setPoints(suggestedPoints);
	}

	@Override
	public void addRegularAction(final PGRegularAction action, CVKeys key) {
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

	@Override
	public boolean confirmImageDialog(String message, Mat image, Point[] points) {
		MatImageDisplayer mid = new MatImageDisplayer(image);
		mid.setPainter(new RectanglePainter(points, mid));

		if (window.confirmDialog(mid, message,
				CONFIRM_DIALOG_WIDTH, CONFIRM_DIALOG_WIDTH * image.rows() / image.cols()) == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void drawTableOutline(Point[] points) {
		display.setPainter(new RectanglePainter(points, display, Color.WHITE));
	}

	private void determineKey(AbstractAction action, CVKeys key) {
		if (key == null) {
			return;
		}

		int keyEvent;
		switch (key) {
		case ENTER:
			keyEvent = KeyEvent.VK_ENTER;
			break;
		case R:
			keyEvent = KeyEvent.VK_R;
			break;
		default:
			throw new RuntimeException("Unknown cv key!");
		}
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyEvent, 0));
	}

}
