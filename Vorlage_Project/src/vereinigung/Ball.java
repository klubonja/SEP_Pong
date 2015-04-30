package vereinigung;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends ImageView {
	Timeline t;
	ImageView ballImageView = new ImageView();
	int radius = 20;
	
	// Vector x and y of the ball's speed
	public int xSpeed = 3;
	public int ySpeed = 1;

	public Ball(int x, int y, Image img) {
		ballImageView.setImage(img);
		ballImageView.setX(x);
		ballImageView.setY(y);
	}
}
