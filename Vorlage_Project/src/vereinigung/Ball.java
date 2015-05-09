package vereinigung;


import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;


public class Ball extends Circle {
	
	// Vector x and y of the ball's speed
	private double xSpeed; 
	private double ySpeed;
	private final int RADIUS = 18;
	
	/**
	 * Constructor of the class Ball.
	 * @param centerX Coordinate X of the ball center.
	 * @param centerY Coordinate Y of the ball center.
	 */
	public Ball(int centerX, int centerY) {
		super();
		setCenterX(centerX);
		setCenterY(centerY);
		setRadius(RADIUS);
		setXSpeed(2);
		setYSpeed(1);
		randomizeYSpeed();
		Stop[] stops = new Stop[] { new Stop(0, Color.LIME), new Stop(1, Color.GREENYELLOW)};
		LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		setFill(lg1);
	}
	
	public void randomizeYSpeed() {
		Random random = new Random();
		ySpeed = ySpeed + random.nextDouble() - 1;
	}

	public void setXSpeed(double x) {
		this.xSpeed = x;
	}

	public double getXSpeed() {
		return this.xSpeed;
	}

	public void setYSpeed(double y) {
		this.ySpeed = y;
	}

	public double getYSpeed() {
		return this.ySpeed;
	}
	
	
	public double getX(){
		return getCenterX();
	}
	
	public double getY(){
		return getCenterY();
	}
	
	public void setX(double x){
		setCenterX(x);
	}
	
	public void setY(double y){
		setCenterY(y);
	}
}
