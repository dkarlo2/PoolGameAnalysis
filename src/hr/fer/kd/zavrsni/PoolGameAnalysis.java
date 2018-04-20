package hr.fer.kd.zavrsni;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.ball_detection.ICVPoolGameDetectedAction;
import hr.fer.kd.zavrsni.ball_detection.PoolGameCV;
import hr.fer.kd.zavrsni.ball_detection.balls.model.ICVBall;
import hr.fer.kd.zavrsni.ball_detection.table.model.ICVTable;
import hr.fer.kd.zavrsni.graphics.Graphics;
import hr.fer.kd.zavrsni.model.balls.Ball;
import hr.fer.kd.zavrsni.model.table.Table;
import hr.fer.kd.zavrsni.presentation.MainWindow;
import hr.fer.kd.zavrsni.presentation.cv.CVPresentation;
import hr.fer.kd.zavrsni.presentation.graphics.GraphicsPresentation;
import hr.fer.kd.zavrsni.shot_analysis.ShotAnalysis;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PoolGameAnalysis {

	private static final double TABLE_LENGTH = 1;

	private static MainWindow window;

	private static void startApplication() {
		window = new MainWindow();
		window.setVisible(true);

		// detectPoolGame();
		poolGameDetected(null, null);
	}

	private static void detectPoolGame() {
		new PoolGameCV().detectPoolGame("Pictures/image2_10822.png", new ICVPoolGameDetectedAction() {

			@Override
			public void actionPerformed(ICVTable table, Map<Integer, ICVBall> balls) {
				poolGameDetected(table, balls);
			}
		}, new CVPresentation(window));
	}

	private static void poolGameDetected(ICVTable cvtable, Map<Integer, ICVBall> cvballs) {
		/*double factor = TABLE_LENGTH / cvtable.getLength();

		Map<Integer, Ball> balls = new HashMap<>();
		List<Ball> ballsList = new ArrayList<>();

		double radius = 0;

		Set<Integer> numbers = cvballs.keySet();
		for (int number : numbers) {
			ICVBall cvball = cvballs.get(number);
			if (radius == 0) {
				radius = cvball.getRadius();
			} else if (cvball.getRadius() != radius) {
				throw new RuntimeException("Ball radius not correct!");
			}
			CVColor cvc = cvball.getColor();
			Color c = new Color(cvc.getRed(), cvc.getGreen(), cvc.getBlue());
			MyVector pos = cvball.getPosition();
			Ball ball = new Ball(number, new MyVector(pos.getX() * factor, pos.getY() * factor),
					radius * factor, c, false);

			balls.put(number, ball);
			ballsList.add(ball);
		}

		CVColor cvc = cvtable.getColor();
		Color c = new Color(cvc.getRed(), cvc.getGreen(), cvc.getBlue());
		Table table = new Table(cvtable.getLength() * factor, cvtable.getWidth() * factor, c, radius * factor);*/

		Table table = new Table(1, 0.54179761156505342551854179761157, new Color(92, 120, 110), 0.012515297518575153);
		Ball ball0 = new Ball(0, new MyVector(-0.3980075424261471, 0.12371369219884029), 0.012515297518575153, new Color(255, 255, 255), false);
		Ball ball1 = new Ball(1, new MyVector(0.24561282212445001, -0.2339230142750755), 0.012515297518575153, new Color(38, 199, 214), false);
		Ball ball2 = new Ball(2, new MyVector(-0.030942803268384677, 0.05331771482611873), 0.012515297518575153, new Color(163, 111, 30), false);
		Ball ball3 = new Ball(3, new MyVector(-0.11013827781269643, 0.13502733141945625), 0.012515297518575153, new Color(81, 81, 202), false);
		Ball ball4 = new Ball(4, new MyVector(-0.12710873664362038, -0.03342018586527033), 0.012515297518575153, new Color(122, 144, 217), false);
		Ball ball5 = new Ball(5, new MyVector(-0.25721558768070396, -0.01707826254660283), 0.012515297518575153, new Color(49, 140, 218), false);
		Ball ball6 = new Ball(6, new MyVector(-0.15979258328095539, -0.11261566040958211), 0.012515297518575153, new Color(87, 143, 47), false);
		Ball ball8 = new Ball(8, new MyVector(-0.32132620993086114, 0.0306904363848868), 0.012515297518575153, new Color(0, 0, 0), false);
		Ball ball9 = new Ball(9, new MyVector(-0.3892080452545569, -0.11387273143409499), 0.012515297518575153, new Color(54, 207, 205), false);
		Ball ball10 = new Ball(10, new MyVector(-0.3244688874921433, -0.09250252401737592), 0.012515297518575153, new Color(155, 148, 61), false);
		Ball ball11 = new Ball(11, new MyVector(-0.43006285355122564, 0.21925109006181956), 0.012515297518575153, new Color(84, 94, 152), false);
		Ball ball12 = new Ball(12, new MyVector(-0.4137209302325582, 0.033833113946169016), 0.012515297518575153, new Color(98, 137, 190), false);
		Ball ball13 = new Ball(13, new MyVector(-0.3445820238843495, 0.14319829307879), 0.012515297518575153, new Color(50, 167, 217), false);
		Ball ball14 = new Ball(14, new MyVector(-0.1007102451288498, 0.17525360420386857), 0.012515297518575153, new Color(131, 169, 121), false);
		Ball ball15 = new Ball(15, new MyVector(0.2097862979258328, 0.11868540810078876), 0.012515297518575153, new Color(72, 174, 208), false);

		Map<Integer, Ball> balls = new HashMap<>();
		balls.put(0, ball0);
		balls.put(1, ball1);
		/*balls.put(2, ball2);
		balls.put(3, ball3);
		balls.put(4, ball4);
		balls.put(5, ball5);
		balls.put(6, ball6);
		balls.put(8, ball8);
		balls.put(9, ball9);
		balls.put(10, ball10);
		balls.put(11, ball11);
		balls.put(12, ball12);
		balls.put(13, ball13);
		balls.put(14, ball14);
		balls.put(15, ball15);*/

		List<Ball> ballsList = Arrays.asList(new Ball[]{ball0, ball1/*, ball2, ball3, ball4, ball5, ball6, ball8, ball9,
				ball10, ball11, ball12, ball13, ball14, ball15*/});

		ShotAnalysis sa = new ShotAnalysis(balls, table);
		new Graphics().drawWorld(ballsList, table, sa, sa, new GraphicsPresentation(window));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | IllegalAccessException
						| InstantiationException | UnsupportedLookAndFeelException e) {
					// never mind
				}
				startApplication();
			}
		});
	}

}
