package pong;

import java.util.LinkedList;
import java.util.List;

import Model.Ball_old;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Mechanics {
	public List<Ball_old> ballList = new LinkedList<Ball_old>();
	int numberOfBalls;
	
	public static Group grp = new Group();

	final int PADDLE_SPEED = 10;
	int fireOpacity = 0;

	DoubleProperty player1Y = new SimpleDoubleProperty();
	DoubleProperty fire1UpY = new SimpleDoubleProperty();
	DoubleProperty fire1DownY = new SimpleDoubleProperty();
	DoubleProperty player2Y = new SimpleDoubleProperty();
	DoubleProperty fire2UpY = new SimpleDoubleProperty();
	DoubleProperty fire2DownY = new SimpleDoubleProperty();
	double topBound, bottomBound;

	Timeline t;

	@FXML
	BorderPane pane;
	@FXML
	ImageView player1;
	@FXML
	ImageView player2;
	@FXML
	Line top;
	@FXML
	Line bot;
	@FXML
	ImageView fire1Up;
	@FXML
	ImageView fire1Down;
	@FXML
	ImageView fire2Up;
	@FXML
	ImageView fire2Down;
	@FXML
	Button play;
	@FXML
	Button quit;
	@FXML TextField field;

	public void initialize() {
		player1Y.set(player1.getLayoutY());
		player1.layoutYProperty().bind(player1Y);

		fire1UpY.set(fire1Up.getLayoutY());
		fire1Up.layoutYProperty().bind(fire1UpY);
		hideFire(fire1Up);
		fire1DownY.set(fire1Down.getLayoutY());
		fire1Down.layoutYProperty().bind(fire1DownY);
		hideFire(fire1Down);

		player2Y.set(player2.getLayoutY());
		player2.layoutYProperty().bind(player2Y);

		fire2UpY.set(fire2Up.getLayoutY());
		fire2Up.layoutYProperty().bind(fire2UpY);
		hideFire(fire2Up);
		fire2DownY.set(fire2Down.getLayoutY());
		fire2Down.layoutYProperty().bind(fire2DownY);
		hideFire(fire2Down);

		topBound = PADDLE_SPEED + 25;
		bottomBound = 770 - player1.getFitHeight() - fire1Down.getFitHeight()
				+ 10;
	}

	// adding balls
	public void addBall() {
		Ball_old ball = new Ball_old(Color.WHITE);
		ballList.add(ball);
		Main.root.getChildren().add(ball);
	}

	// keyboard events
	public void keyPressedHandler(KeyEvent event) {
		KeyCode keyCode = event.getCode();
		switch (keyCode) {
		case Q:
			Platform.exit();
			break;
		case P:
			player1Y.set(304);
			fire1UpY.set(250);
			fire1DownY.set(499);
			player2Y.set(304);
			fire2UpY.set(250);
			fire2DownY.set(499);
			for (Ball_old ball : ballList) {
				ball.centerXProperty().set(500);
				ball.centerYProperty().set(385);
				ball.ballMove();
			}
			break;
		case UP:
			moveUp();
			fireOpacity = 1;
			hideFire(fire1Down);
			break;
		case DOWN:
			moveDown();
			fireOpacity = 1;
			hideFire(fire1Up);
			break;
		case W:
			moveUp2();
			fireOpacity = 1;
			hideFire(fire2Down);
			break;
		case S:
			moveDown2();
			fireOpacity = 1;
			hideFire(fire2Up);
			break;
		}
	}

	// handling fire animations
	public void keyReleasedHandler(KeyEvent e) {
		KeyCode keyCode = e.getCode();
		switch (keyCode) {
		case UP:
			fireOpacity = 0;
			hideFire(fire1Down);
			break;
		case DOWN:
			fireOpacity = 0;
			hideFire(fire1Up);
			break;
		case W:
			fireOpacity = 0;
			hideFire(fire2Down);
			break;
		case S:
			fireOpacity = 0;
			hideFire(fire2Up);
			break;
		}
	}

	public void hideFire(ImageView fire) {
		fire.setOpacity(fireOpacity);
	}

	// handling menu items - mouse click events
	public void menuSelector(ActionEvent e) {
		MenuItem clickedMenu = (MenuItem) e.getTarget();
		String choice = clickedMenu.getText();
		if ("Play New Game (P)".equals(choice)) {
			for (Ball_old ball : ballList) {
				ball.ballMove();
			}
		}
		if ("Quit (Q)".equals(choice)) {
			Platform.exit();
		}
	}
	
	//getting number of balls from text field
	private void input(TextField t){
		numberOfBalls = Integer.parseInt(t.getText());
	}

	// handling first screen buttons
	public void buttonClicked(MouseEvent e) {
		play.addEventHandler(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						for(int i = 0; i < numberOfBalls; i++){
							addBall();
						}
						for (Ball_old ball : ballList) {
							ball.ballMove();
						}
					}
				});
		quit.addEventHandler(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						Platform.exit();
					}
				});
	}

	// paddle movement up
	private void moveUp() {
		if (player1Y.get() > topBound) {
			player1Y.set(player1Y.get() - PADDLE_SPEED);
			fire1DownY.set(fire1DownY.get() - PADDLE_SPEED);
			fire1UpY.set(fire1UpY.get() - PADDLE_SPEED);
		}
	}

	// paddle movement down
	private void moveDown() {
		if (player1Y.get() < bottomBound) {
			player1Y.set(player1Y.get() + PADDLE_SPEED);
			fire1DownY.set(fire1DownY.get() + PADDLE_SPEED);
			fire1UpY.set(fire1UpY.get() + PADDLE_SPEED);
		}
	}

	// paddle movement up player2
	private void moveUp2() {
		if (player2Y.get() > topBound) {
			player2Y.set(player2Y.get() - PADDLE_SPEED);
			fire2DownY.set(fire2DownY.get() - PADDLE_SPEED);
			fire2UpY.set(fire2UpY.get() - PADDLE_SPEED);
		}
	}

	// paddle movement down player2
	private void moveDown2() {
		if (player2Y.get() < bottomBound) {
			player2Y.set(player2Y.get() + PADDLE_SPEED);
			fire2DownY.set(fire2DownY.get() + PADDLE_SPEED);
			fire2UpY.set(fire2UpY.get() + PADDLE_SPEED);
		}
	}

}
