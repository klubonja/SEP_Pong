package vereinigung;

import java.util.LinkedList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Die Klasse View2 repraesentiert das Spiel selbst, in dem man mit den
 * Schlaegern und dem Ball spielen kann.
 * 
 * @author GestreifteRemulaner
 *
 */
public class View2 extends Stage {
	// Hintergrundbild
	final Image gestreifteRemulanerHintergrund2 = new Image("/Hintergrund2.png");
	// Linker Schlaeger
	final Image p1 = new Image("/Player1.png");
	// Rechter Schlaeger
	final Image p2 = new Image("/Player2.png");
	final Image player1FireUp = new Image("/Player1FeuerUp.png");
	final Image player1FireDown = new Image("/Player1FeuerDown.png");
	final Image player2FireUp = new Image("/Player2FeuerUp.png");
	final Image player2FireDown = new Image("/Player2FeuerDown.png");

	public ImageView player1 = new ImageView(p1); // Der rechte Spieler
	public ImageView player2 = new ImageView(p2); // Der linke Spieler
	public ImageView fireUp1 = new ImageView(player1FireUp);
	public ImageView fireDown1 = new ImageView(player1FireDown);
	public ImageView fireUp2 = new ImageView(player2FireUp);
	public ImageView fireDown2 = new ImageView(player2FireDown);

	// Die Punkte von dem linken Spieler
	public final StringProperty resultLeft = new SimpleStringProperty("0");
	// Die Punkte von dem rechten Spieler
	public final StringProperty resultRight = new SimpleStringProperty("0");
	// Die Nachricht mit dem Geweinner
	public final StringProperty winMsg = new SimpleStringProperty("");

	public Label resultL; // die Punkte von player2
	public Label resultR; // die Punkte von player1
	public Label win; // die Nachricht mit dem Geweinner
	public Button restart;

	public Scene pongBoard;
	public Group root;
	// Ist dann wahr, wenn man den 2-Spieler Modus waehlt
	public boolean allowPlayer2 = false;

	// Eine Liste, die alle Baelle im Spiel enthaelt
	public List<BallView> ballList = new LinkedList<BallView>();

	public Timeline t;
	public KeyFrame keyFrame;

	/**
	 * Constructor fuer das Spiel.
	 * 
	 * @param players
	 *            Bestimmt den Spielmodus (1 oder 2 Spieler)
	 */
	public View2(boolean players) {
		fireUp1.setOpacity(0);
		fireDown1.setOpacity(0);
		fireUp2.setOpacity(0);
		fireDown2.setOpacity(0);
		root = new Group();
		player1.setX(940);
		player1.setY(304);
		fireUp1.setX(940);
		fireUp1.setY(247);
		fireDown1.setX(940);
		fireDown1.setY(502);
		player2.setX(10);
		player2.setY(304);
		fireUp2.setX(10);
		fireUp2.setY(247);
		fireDown2.setX(10);
		fireDown2.setY(502);
		ImageView background2 = new ImageView();
		background2.setImage(gestreifteRemulanerHintergrund2);
		if (!players) {
			allowPlayer2 = true;
		}
		pongBoard = new Scene(root, 1000, 770);

		resultL = new Label("0");
		resultL.setTranslateX(420);
		resultL.setTranslateY(685);
		resultL.setTextFill(Color.WHITE);
		resultL.setFont(Font.font("Consolas", FontWeight.BOLD, 50));
		resultR = new Label("0");
		resultR.setTranslateX(550);
		resultR.setTranslateY(685);
		resultR.setTextFill(Color.WHITE);
		resultR.setFont(Font.font("Consolas", FontWeight.BOLD, 50));
		win = new Label("");
		win.setTranslateX(150);
		win.setTranslateY(305);
		win.setFont(Font.font("Consolas", 80));
		win.setTextFill(Color.WHITE);

		restart = new Button("Restart Game");
		restart.setTranslateX(395);
		restart.setTranslateY(410);
		restart.setMinSize(150, 100);
		restart.setStyle("-fx-font-size: 30 ;"
				+ "-fx-background-color: radial-gradient(radius 100%, aliceblue, lightsteelblue);"
				+ "-fx-background-radius: 20");

		root.getChildren().addAll(background2, player1, fireUp1, fireDown1,
				player2, fireUp2, fireDown2, resultL, resultR, win);
		root.setFocusTraversable(true);

		pongBoard.setCursor(Cursor.NONE);
		this.setScene(pongBoard);
		this.setResizable(false);
		this.initStyle(StageStyle.UNDECORATED);
		this.show();
		this.root.requestFocus();
	}
}