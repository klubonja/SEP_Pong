package Model;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
	
	public final int PADDLE_MOVEMENT = 10;
	

	public Paddle(double x, double y, double width, double height, Color color){
		super(width,height,color);
		setX(x);
		setY(y);
		}
	
	
	
	
	public void moveUp() {
		setY(getY() - PADDLE_MOVEMENT);
		if (getY() < 0) {
			setY(0);
		}
	}
	
	
	public void moveDown() {
		setY(getY() + PADDLE_MOVEMENT);
		if (getY() + getHeight() > 350) {
			setY(350 - getHeight());
		}
	}
	
}
