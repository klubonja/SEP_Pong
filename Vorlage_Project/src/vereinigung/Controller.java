package vereinigung;

import java.util.LinkedList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Controller extends Stage {
	final Image gestreifteRemulanerHintergrund2 = new Image(
			Main.class.getResourceAsStream("Hintergrund2.png"));
	final Image p1 = new Image(Main.class.getResourceAsStream("Player1.png"));;
	final Image p2 = new Image(Main.class.getResourceAsStream("Player2.png"));;
	final Image fireUp = new Image(
			Main.class.getResourceAsStream("Player1FeuerUp.png"));
	final Image fireDown = new Image(
			Main.class.getResourceAsStream("Player1FeuerDown.png"));
	final Image ballImage = new Image(
			Main.class.getResourceAsStream("Ball.png"));;

	final StringProperty resultLeft = new SimpleStringProperty("0");
	final StringProperty resultRight = new SimpleStringProperty("0");
	final StringProperty winMsg = new SimpleStringProperty("");

	private Paddle player2;
	private Paddle player1;
	private List<Ball> ballList = new LinkedList<Ball>();
	Timeline t;
	private Group root = new Group();

	public Controller(boolean players, int balls) {
		player2 = new Paddle(10, 304, p1, 10, 247, fireUp, 10, 502, fireDown);
		player1 = new Paddle(940, 304, p2, 940, 247, fireUp, 940, 502, fireDown);
		ImageView background2 = new ImageView();
		background2.setImage(gestreifteRemulanerHintergrund2);
		if (players) {
			root.getChildren().addAll(background2, player2, player2.fireUp,
					player2.fireDown);
		} else {
			root.getChildren().addAll(background2, player2.player,
					player2.fireUp, player2.fireDown, player1.player,
					player1.fireUp, player1.fireDown);
		}

		Scene pongBoard = new Scene(root, 1000, 770);
		pongBoard.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@SuppressWarnings("incomplete-switch")
			@Override
			public void handle(KeyEvent evt) {
				KeyCode keyCode = evt.getCode();
				switch (keyCode) {
				case UP:
					player1.moveUp();
					break;
				case DOWN:
					player1.moveDown();
					break;
				case W:
					player2.moveUp();
					break;
				case S:
					player2.moveDown();
					break;
				case B:
					addBall();
					bounce();
					break;
				case Q:
					Platform.exit();
					break;
				}
			}
		});

		pongBoard.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@SuppressWarnings("incomplete-switch")
			@Override
			public void handle(KeyEvent e) {
				
				KeyCode code = e.getCode();
				switch (code) {
				case UP:
					player1.fadeFire(player1.fireDown);
					break;
				case DOWN:
					player1.fadeFire(player1.fireUp);
					break;
				case W:
					player2.fadeFire(player2.fireDown);
					break;
				case S:
					player2.fadeFire(player2.fireUp);
					break;
				}
			}
		});

		if (true) {
			Label resultL = new Label("0");
			resultL.setTranslateX(450);
			resultL.setTranslateY(685);
			resultL.setTextFill(Color.WHITE);
			resultL.setFont(Font.font("Consolas", FontWeight.BOLD, 50));
			resultL.textProperty().bind(resultLeft);
			Label resultR = new Label("0");
			resultR.setTranslateX(550);
			resultR.setTranslateY(685);
			resultR.setTextFill(Color.WHITE);
			resultR.setFont(Font.font("Consolas", FontWeight.BOLD, 50));
			resultR.textProperty().bind(resultRight);
			Label win = new Label("Player 1 Wins");
			win.setTranslateX(195);
			win.setTranslateY(160);
			win.setFont(Font.font("Consolas", 30));
			win.textProperty().bind(winMsg);
			root.getChildren().addAll(resultL, resultR, win);
		}
		root.setFocusTraversable(true);

		for (int i = 0; i < balls; i++) {
			addBall();
		}
		bounce();
		checkScore();

		this.setScene(pongBoard);
		this.setResizable(false);
		this.initStyle(StageStyle.UNDECORATED);
		this.show();
		this.root.requestFocus();
	}

	public void addBall() {
		Ball newBall = new Ball(480, 365, ballImage);
		ballList.add(newBall);
		root.getChildren().add(newBall.ballImageView);
	}

	public void checkScore() {
		KeyFrame kfr = new KeyFrame(new Duration(100), event -> {
			for (Ball ball : ballList) {
				if (ball.getX() + 10 >= 1000) {
					ballList.remove(ball);
					resultLeft.set("" + (Integer.parseInt(resultLeft.get()) + 1));
				}
				if (ball.getX() + 10 <= -30) {
					ballList.remove(ball);
					resultRight.set("" + (Integer.parseInt(resultRight.get()) + 1));
				}

				if (Integer.parseInt(resultLeft.get()) >= 21) {
					winMsg.set("Player 1 wins");
				}
			}
		});
		Timeline timer = new Timeline(kfr);
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();
	}

	// lets ball move and bounce off the top and bottom
	public void bounce() {
		for (Ball ball : ballList) {
			KeyFrame keyFrame = new KeyFrame(new Duration(10),
					event -> {
						ball.ballImageView.setX(ball.ballImageView.getX()
								+ ball.xSpeed);
						ball.ballImageView.setY(ball.ballImageView.getY()
								+ ball.ySpeed);
						if (collision()) {
							ball.t.stop();
							ball.xSpeed *= -1;
							bounce();
						} else if (ball.ballImageView.getY() > 750
								|| ball.ballImageView.getY() < 20) {
							ball.t.stop();
							ball.ySpeed *= -1;
							bounce();
						}
					});
			ball.t = new Timeline(keyFrame);
			ball.t.setCycleCount(Timeline.INDEFINITE);
			ball.t.play();
		}
	}

	public boolean collision() {
		for (Ball ball : ballList) {
			if (ball.ballImageView.intersects(player2.player
					.getBoundsInParent())
					|| ball.ballImageView.intersects(player1.player
							.getBoundsInParent())) {
				return true;
			}
		}
		return false;
	}
}
