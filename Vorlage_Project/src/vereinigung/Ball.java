package vereinigung;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends ImageView {
	ImageView ball = new ImageView();
	int radius = 20;
	
	// Vector x and y of the ball's speed
	public int xSpeed = 7;
	public int ySpeed = 7;

	public Ball(int x, int y, Image img) {
		ball.setImage(img);
		ball.setX(x);
		ball.setY(y);
	}

	public void collision() {
		this.xSpeed = -xSpeed;
	}

	// lets ball move and bounce off the top and bottom
	public void bounce() {
		if(ball.getY() > 750 || ball.getY() < 20){
			ySpeed *= -1;
		}
	}
}
