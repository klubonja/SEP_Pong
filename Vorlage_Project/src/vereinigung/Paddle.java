package vereinigung;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle extends ImageView {
	ImageView player = new ImageView();
	public final int PADDLE_SPEED = 10;

	public Paddle(int x, int y, Image img) {
		player.setImage(img);
		player.setX(x);
		player.setY(y);
	}
	
	public void moveUp() {
		player.setY(player.getY() - PADDLE_SPEED);
		if (player.getY() < 0) {
			player.setY(0);
		}
	}
	
	
	public void moveDown() {
		player.setY(player.getY() + PADDLE_SPEED);
		if (player.getY() + player.getFitHeight() > 350) {
			player.setY(350 - player.getFitHeight());
		}
	}
	
	public void moveRight(){
		player.setX(player.getX() + PADDLE_SPEED);
		
		
	}
	
	public void moveLeft(){
		player.setX(player.getX() - PADDLE_SPEED);
		
	}

}
