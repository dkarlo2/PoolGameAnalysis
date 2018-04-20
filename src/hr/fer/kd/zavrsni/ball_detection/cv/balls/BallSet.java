package hr.fer.kd.zavrsni.ball_detection.cv.balls;

import hr.fer.kd.mymath.model.MyVector;
import hr.fer.kd.zavrsni.ball_detection.balls.model.CVBall;
import hr.fer.kd.zavrsni.ball_detection.balls.model.ICVBall;
import hr.fer.kd.zavrsni.ball_detection.cv.CVColor;
import hr.fer.kd.zavrsni.ball_detection.cv.CVUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Scalar;

public class BallSet {

	private static final double WHITE_PART = 0.2;
	private static final double STRIPE_PART = 0.6;
	private static final double SOLID_PART = 0.8;

	private static final Scalar YELLOW_SOLID = new Scalar(36, 207, 224);
	private static final Scalar YELLOW_STRIPE = new Scalar(45, 223, 225);
	private static final Scalar BLUE_SOLID = new Scalar(170, 117, 35);
	private static final Scalar BLUE_STRIPE = new Scalar(165, 147, 55);
	private static final Scalar RED_SOLID = new Scalar(82, 84, 210);
	private static final Scalar RED_STRIPE = new Scalar(89, 101, 165);
	private static final Scalar PINK_SOLID = new Scalar(130, 155, 228);
	private static final Scalar PINK_STRIPE = new Scalar(100, 140, 200);
	private static final Scalar ORANGE_SOLID = new Scalar(51, 143, 220);
	private static final Scalar ORANGE_STRIPE = new Scalar(43, 173, 233);
	private static final Scalar GREEN_SOLID = new Scalar(91, 147, 51);
	private static final Scalar GREEN_STRIPE = new Scalar(140, 177, 127);
	private static final Scalar BROWN_SOLID = new Scalar(56, 130, 170);
	private static final Scalar BROWN_STRIPE = new Scalar(71, 175, 214);

	private static final List<Scalar> COLORS = Arrays.asList(
			YELLOW_SOLID, BLUE_SOLID, RED_SOLID, PINK_SOLID, ORANGE_SOLID, GREEN_SOLID, BROWN_SOLID,
			YELLOW_STRIPE, BLUE_STRIPE, RED_STRIPE, PINK_STRIPE, ORANGE_STRIPE, GREEN_STRIPE, BROWN_STRIPE);

	private List<BallDetector> cvBalls = new ArrayList<>();

	private double tableLength;
	private double tableWidth;

	public BallSet(double tL, double tW) {
		tableLength = tL;
		tableWidth = tW;
	}

	public void add(BallDetector ball) {
		cvBalls.add(ball);
	}

	public int size() {
		return cvBalls.size();
	}

	public Map<Integer, ICVBall> calculatePositions() {
		Map<Integer, ICVBall> balls = new HashMap<>();

		double radius = 0;

		for (BallDetector ball : cvBalls) {
			radius += ball.getRadius();
		}

		radius /= cvBalls.size();

		for (BallDetector ball : cvBalls) {
			MyVector pos = ball.getPosition();
			pos = pos.sub(tableLength / 2, tableWidth / 2);

			System.out.println("Ball " + ball.getColor().val[2] + " " +
					ball.getColor().val[1] + " " + ball.getColor().val[0] + " " + ball.getColorPart());
			System.out.println("Position: " + pos);

			if (ball.getColorPart() < WHITE_PART) {
				balls.put(0, new CVBall(0, pos, CVColor.WHITE, 1, radius));
				System.out.println("White!");
				continue;
			}

			Scalar ballColor = ball.getColor();

			if (ballColor.val[0] < 100 && ballColor.val[1] < 100 && ballColor.val[2] < 100) {
				balls.put(8, new CVBall(8, pos, CVColor.BLACK, 1, radius));
				System.out.println("Black!");
				continue;
			}

			int number = findColor(ballColor);

			if (number == -1) {
				System.out.println("Can't find the ball!");
				continue;
			}

			if (number < 7) {
				number++;
				if (ball.getColorPart() < STRIPE_PART) {
					number += 8;
				}
			} else {
				number += 2;
				if (ball.getColorPart() > SOLID_PART) {
					number -= 8;
				}
			}

			balls.put(number, new CVBall(number, pos, new CVColor(ballColor), 1, radius));
			System.out.println("Ball " + number + "!");
		}

		return balls;
	}

	private static int findColor(Scalar ballColor) {
		double min = Double.MAX_VALUE;
		int color = -1;
		for (int i = 0; i < COLORS.size(); i++) {
			double d = distance(ballColor, COLORS.get(i));
			if (d < min) {
				min = d;
				color = i;
			}
		}
		if (min < 10) {
			return color;
		} else {
			return -1;
		}
	}

	private static double distance(Scalar color1, Scalar color2) {
		Scalar hsv1 = CVUtil.convertBGR2HSV(color1);
		Scalar hsv2 = CVUtil.convertBGR2HSV(color2);

		double hue = Math.abs(hsv1.val[0] - hsv2.val[0]);

		return Math.min(hue, 255-hue);
	}

}
