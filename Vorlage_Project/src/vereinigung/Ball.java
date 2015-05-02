package vereinigung;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends ImageView {
	Timeline t;
	ImageView ballImageView = new ImageView();

	// Vector x and y of the ball's speed
	private int xSpeed = 3;
	private int ySpeed = 1;

	public Ball(int x, int y, Image img) {
		ballImageView.setImage(img);
		ballImageView.setX(x);
		ballImageView.setY(y);
	}

	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getXSpeed() {
		return this.xSpeed;
	}

	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public int getYSpeed() {
		return this.ySpeed;
	}
}
