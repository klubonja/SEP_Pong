package Model;


import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
		
		public final static int BALL_INCR = 4;
		public final static double RADIUS = 20;

		
		//Vector x and y of the ball's speed
		private double xSpeed;
		private double ySpeed;
		
		
		//Constructor called in Controller which directs to the real constructor
		public Ball() {
			this(BALL_INCR, 0, Color.RED);
			//randomizeXSpeed();
			randomizeYSpeed();
		}
		
		public Ball(double xSpeed, double ySpeed, Color color) {
			super();
			setRadius(RADIUS);
			setFill(color);
			setCenterX(300);
			setCenterY(175);
			this.xSpeed = xSpeed;
			this.ySpeed = ySpeed;
		}
		
		public double getXSpeed() {
			return this.xSpeed;
		}
		
		public double getYSpeed() {
			return this.ySpeed;
		}
		
		//Changes the direction of the speed vector in the oposite way
		public void changeXDirection() {
			this.xSpeed = -xSpeed;
		}
		
		public void changeYDirection(){
			this.ySpeed = -ySpeed;
		}
		
		
		//Gives a new random direction to the y vector
		public void randomizeYSpeed() {
			Random random = new Random();
			this.ySpeed = ySpeed - random.nextDouble() - 0.5;
		}
		
		public void randomizeXSpeed() {
			Random random = new Random();
			this.xSpeed = xSpeed + random.nextDouble() - 0.5;
		}
		
		//Collision is implemented as a complete change of direction with random y-component
		public void collisionHorizontal() {
			changeXDirection();
			randomizeYSpeed();
		}
		
		public void collisionVertical() {
			changeYDirection();
			randomizeXSpeed();
		}
		
		
		public void move() {
			this.setCenterX(getCenterX() + xSpeed);
			this.setCenterY(getCenterY() + ySpeed);
			if (getCenterY() + getRadius() > 350) {
				setCenterY(350 - getRadius());
				ySpeed = -ySpeed;
			}
			if (getCenterY() - getRadius() < 0) {
				setCenterY(getRadius());
				ySpeed = -ySpeed;
			}
		}

	}

