package hr.fer.kd.zavrsni.model.balls;

import hr.fer.kd.mymath.model.MyVector;

import java.awt.Color;

public class Ball {

	private int number;

	private MyVector lastPosition;

	private MyVector position;

	private MyVector velocity;

	private boolean potted;

	private Color color; // TODO ne koristi se nigdje

	private double radius;

	public Ball(Ball ball) {
		this(ball, false);
	}

	public Ball(Ball ball, boolean all) {
		this(ball, ball.getPosition());
		if (all) {
			this.velocity = ball.velocity;
			this.lastPosition = ball.lastPosition;
		}
	}

	public Ball(Ball ball, MyVector position) {
		this(ball.getNumber(), position, ball.getRadius(), ball.getColor(), ball.isPotted());
	}

	public Ball(int number, MyVector position, double radius, Color color, boolean potted) {
		if (number < 0 || number > 15) {
			throw new RuntimeException("Undefined ball number!");
		}

		this.number = number;
		this.lastPosition = position;
		this.position = position;
		this.velocity = new MyVector();
		this.radius = radius;
		this.color = color;
		this.potted = potted;
	}

	public boolean isSolid() {
		if (number == 0) {
			throw new RuntimeException("Cue ball!");
		}
		if (number == 8) {
			throw new RuntimeException("8-ball!");
		}
		return number < 8;
	}

	public boolean isStripe() {
		return !isSolid();
	}

	public int getNumber() {
		return number;
	}

	public MyVector getLastPosition() {
		return lastPosition;
	}

	public void resetLastPosition() {
		lastPosition = position;
	}

	public MyVector getPosition() {
		return position;
	}

	public void setPosition(MyVector position) {
		this.position = position;
	}

	public boolean isPotted() {
		return potted;
	}

	public void setPotted(boolean potted) {
		this.potted = potted;
	}

	public MyVector getVelocity() {
		return velocity;
	}

	public void setVelocity(MyVector velocity) {
		this.velocity = velocity;
	}

	public double getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}

	public void setAll(MyVector position, MyVector velocity, boolean potted) {
		setPosition(position);
		setVelocity(velocity);
		setPotted(potted);
	}

	@Override
	public String toString() {
		return "new Ball(" + number + ", new MyVector(" + position.getX() + ", " + position.getY() + "), "
				+ radius + ", new Color(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "), "
				+ potted + ");";
	}

}
