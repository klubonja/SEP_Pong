package vereinigung;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Ball extends Circle {
	
	// Vector x and y of the ball's speed
	private DoubleProperty xSpeed = new SimpleDoubleProperty(5);
	private DoubleProperty ySpeed = new SimpleDoubleProperty(1);
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
		setFill(Color.FUCHSIA);
	}

	public void setXSpeed(double xSpeed) {
		this.xSpeed.set(xSpeed);
	}

	public double getXSpeed() {
		return this.xSpeed.get();
	}

	public void setYSpeed(double ySpeed) {
		this.ySpeed.set(ySpeed);
	}

	public double getYSpeed() {
		return this.ySpeed.get();
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
