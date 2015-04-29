package Model;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle_old extends Rectangle {
	
	public final int PADDLE_MOVEMENT = 15;
	

	public Paddle_old(double x, double y, double width, double height, Color color){
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
	
	public void moveRight(){
		setX(getX() + PADDLE_MOVEMENT);
		
		
	}
	
	public void moveLeft(){
		setX(getX() - PADDLE_MOVEMENT);
		
	}
	
}
