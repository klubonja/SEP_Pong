package welcome;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/**
 * 
 * @author nedkochulev
 *
 */
public class Main extends Application {

	final static int WIDTH = 1000;
	final static int HEIGHT = 800;

	final static Image BACKGROUND = new Image (Main.class.getResource("Hintergrund.png").toString());
	
	public void start(Stage stage){

		// ImageView dient dazu, dass man sozusagen einen Zeiger zu einem
		// vordefinierten Image verwendet,
		// um das Bild auf dem Bildschirm anzuzeigen.
		final ImageView background = new ImageView(BACKGROUND);
		
		final Ball ball = new Ball();
		final Paddle player1 = new Paddle();

		final Group root = new Group(background, ball.grp, player1.grp);

		Scene scene = new Scene(root, WIDTH, HEIGHT);

		stage.setTitle("Pong Spiel 1.0");
		stage.setScene(scene);
		stage.show();
	}

	// Die main Methode, in der wir nur die launch Methode brauchen.
	public static void main(String[] args) {
		launch(args);
	}
}
