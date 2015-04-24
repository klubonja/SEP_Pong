package pong;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Mechanics {
	int windowWidth = 1000;
	int windowHeight = 770;
	final int PADDLE_SPEED = 10;
	int fireOpacity = 0;
	int ballSpeedX = 12;
	int ballSpeedY = 8;

	DoubleProperty player1Y = new SimpleDoubleProperty();
	DoubleProperty fire1UpY = new SimpleDoubleProperty();
	DoubleProperty fire1DownY = new SimpleDoubleProperty();
	DoubleProperty player2Y = new SimpleDoubleProperty();
	DoubleProperty fire2UpY = new SimpleDoubleProperty();
	DoubleProperty fire2DownY = new SimpleDoubleProperty();
	DoubleProperty ballCenterX = new SimpleDoubleProperty();
	DoubleProperty ballCenterY = new SimpleDoubleProperty();
	double topBound, bottomBound;

	Timeline t;

	@FXML
	BorderPane pane;
	@FXML
	ImageView player1;
	@FXML
	ImageView player2;
	@FXML
	Circle ball;
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
		bottomBound = windowHeight - player1.getFitHeight()
				- fire1Down.getFitHeight() + 10;

		ballCenterX.set(ball.getCenterX());
		ball.centerXProperty().bind(ballCenterX);
		ballCenterY.set(ball.getCenterY());
		ball.centerYProperty().bind(ballCenterY);
	}

	// keyboard events
	public void keyPressedHandler(KeyEvent event) {
		KeyCode keyCode = event.getCode();
		switch (keyCode) {
		case E:
			Platform.exit();
			break;
		case N:
			ballCenterX.set(500);
			ballCenterY.set(385);
			player1Y.set(304);
			fire1UpY.set(250);
			fire1DownY.set(499);
			player2Y.set(304);
			fire2UpY.set(250);
			fire2DownY.set(499);
			ballMove();
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
		if ("New Game (N)".equals(choice)) {
			ballMove();
		}
		if ("Exit (E)".equals(choice)) {
			Platform.exit();
		}
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

	// ball movement
	private void ballMove() {
		KeyFrame keyFrame = new KeyFrame(new Duration(50), event -> {
			ballCenterX.set(ballCenterX.get() + ballSpeedX);
			ballCenterY.set(ballCenterY.get() + ballSpeedY);
			if (collision(player2) || collision(player1) || hitBounds(top)
					|| hitBounds(bot)) {
				t.stop();
				bounce();
			}
		});
		t = new Timeline(keyFrame);
		t.setCycleCount(Timeline.INDEFINITE);
		t.play();
	}

	// collision
	private boolean collision(ImageView player) {
		if (ball.intersects(player.getBoundsInParent())) {
			return true;
		} else {
			return false;
		}
	}

	private boolean hitBounds(Line l) {
		if (ball.intersects(l.getBoundsInParent())) {
			return true;
		} else {
			return false;
		}
	}

	// ball bounce
	private void bounce() {
		double ballOutOfBounds = windowWidth + 20;
		KeyFrame keyFrame = new KeyFrame(new Duration(5), event -> {
			if (collision(player1) || collision(player2) || hitBounds(top)
					|| hitBounds(bot)) {
				t.stop();
				if (hitBounds(top) || hitBounds(bot)) {
					ballSpeedY *= -1;
				}
				if (collision(player1) || collision(player2)) {
					ballSpeedX *= -1;
				}
				ballMove();
			} else {
				t.stop();
			}
		});
		t = new Timeline(keyFrame);
		t.setCycleCount(Timeline.INDEFINITE);
		t.setAutoReverse(true);
		t.play();
	}
}
