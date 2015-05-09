package vereinigung;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle extends ImageView{
	
	 private ImageView player = new ImageView();
	private ImageView fireUp = new ImageView();
	private ImageView fireDown = new ImageView();
	public final int PADDLE_SPEED =10;
	
	
	public Paddle(int x, int y, Image img, int fireUpX, int fireUpY,
			Image fireUp, int fireDownX, int fireDownY, Image fireDown) {
		this.player.setImage(img);
		this.player.setX(x);
		this.player.setY(y);
		this.fireUp.setImage(fireUp);
		this.fireUp.setOpacity(0);
		this.fireUp.setX(fireUpX);
		this.fireUp.setY(fireUpY);
		this.fireDown.setImage(fireDown);
		this.fireDown.setOpacity(0);
		this.fireDown.setX(fireDownX);
		this.fireDown.setY(fireDownY);
		}
	
	public void moveUp() {
		fireDown.setOpacity(100);
		player.setY(player.getY() - PADDLE_SPEED);
		fireUp.setY(fireUp.getY() - PADDLE_SPEED);
		fireDown.setY(fireDown.getY() - PADDLE_SPEED);
		if (player.getY() < 0) {
			player.setY(0);
			fireUp.setY(-57);
			fireDown.setY(196);
		}
	}

	public void moveDown() {
		fireUp.setOpacity(100);
		player.setY(player.getY() + PADDLE_SPEED);
		fireUp.setY(fireUp.getY() + PADDLE_SPEED);
		fireDown.setY(fireDown.getY() + PADDLE_SPEED);
		if (player.getY() + player.getFitHeight() > 570) {
			player.setY(570 - player.getFitHeight());
			fireUp.setY(570 - player.getFitHeight() - 57);
			fireDown.setY(769);
		}
	}

	public void fadeFire(ImageView img){
		img.setOpacity(0);
	}
	
	public ImageView getPlayer(){
		return this.player;
	}
	
	public ImageView getFireDown(){
		return this.fireDown;
	}
	
	public ImageView getFireUp(){
		return this.fireUp;
	}
	
	

}
