package Model;


import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
		
		public final static int BALL_INCR=5;
		public final static double RADIUS = 20;
		
		
		//Vector x and y of the ball's speed
		private double xSpeed;
		private double ySpeed;
		
		
		//Constructor called in Controller which directs to the real constructor
		public Ball() {
			this(BALL_INCR, 0, Color.RED);
		}
		
		public Ball(double xSpeed, double ySpeed, Color color) {
			super();
			setRadius(RADIUS);
			setFill(color);
			this.xSpeed = xSpeed;
			this.ySpeed = ySpeed;
		}
		
		//Changes the direction of the speed vector in the oposite way
		public void changeDirection() {
			this.xSpeed = -xSpeed;
		}
		
		
		//Gives a new random direction to the y vector
		public void randomizeYSpeed() {
			Random random = new Random();
			ySpeed = ySpeed + random.nextDouble() - .5;
		}
		
		//Collision is implemented as a complete change of direction with random y-component
		public void collision() {
			changeDirection();
			randomizeYSpeed();
		}
		
		
		public void move() {
			Random randomGenerator = new Random();
			int xBallIncr = randomGenerator.nextInt(100);
			int yBallIncr = randomGenerator.nextInt(100);
			this.setCenterX(getCenterX() + xBallIncr);
			this.setCenterY(getCenterY() + yBallIncr);
			if (getCenterY() + getRadius() > 350) {
				setCenterY(350 - getRadius());
				ySpeed = -ySpeed;
			}
			if (getCenterY() - getRadius() < 0) {
				setCenterY(getRadius());
				ySpeed = -ySpeed;
			}
		}
		
		
		public double getXSpeed() {
			return xSpeed;
		}
		
		public double getYSpeed() {
			return ySpeed;
		}

	}

