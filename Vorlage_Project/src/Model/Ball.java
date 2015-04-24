package Model;


import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
		
		public final static double radius = 20;
		public final static double speed = 5;
		public final double BALL_INCR=5;
		
		//Vector x and y of the ball's speed
		private double xSpeed;
		private double ySpeed;
		
		
		//Constructor called in Controller which directs to the real constructor
		public Ball() {
			this(speed, speed, Color.RED);
		}
		
		public Ball(double xSpeed, double ySpeed, Color color) {
			super();
			setRadius(radius);
			setFill(color);
			this.xSpeed = xSpeed;
			this.ySpeed = ySpeed;
		}
		
		//Changes the direction of the speed vector in the opposite way
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
			super.setCenterX(getCenterX() + xSpeed);
			super.setCenterY(getCenterY() + ySpeed);
			
			// bottom/top Collision
			if(getCenterY()>= 350 - getRadius()||getCenterY()<= 0 + getRadius()){
				ySpeed *= -1;
			}
			
			// rechts/links Collision
			if(getCenterX()>= 600 - getRadius()||getCenterX()<= 0 + getRadius()){
				xSpeed *= -1;
			}
			
		/*	if (getCenterY() + getRadius() * getScaleY() > 350) {
				setCenterY(350 - getRadius() * getScaleY());
				ySpeed = -ySpeed;
			}
			if (getCenterY() - getRadius() * getScaleY() < 0) {
				setCenterY(getRadius() * getScaleY());
				ySpeed = -ySpeed;
			}*/
		}
		
		
		public double getXSpeed() {
			return xSpeed;
		}
		
		public double getYSpeed() {
			return ySpeed;
		}

	}
