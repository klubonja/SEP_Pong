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

	private Label resultL;
	private Label resultR;
	private Label win;

	private Paddle player2;
	private Paddle player1;
	private List<Ball> ballList = new LinkedList<Ball>();
	private Group root = new Group();
	private boolean allowPlayer2 = false;

	public Controller(boolean players, int balls) {
		player2 = new Paddle(10, 304, p1, 10, 247, fireUp, 10, 502, fireDown);
		player1 = new Paddle(940, 304, p2, 940, 247, fireUp, 940, 502, fireDown);
		ImageView background2 = new ImageView();
		background2.setImage(gestreifteRemulanerHintergrund2);
		if (!players) {
			allowPlayer2 = true;
		}
		Scene pongBoard = new Scene(root, 1000, 770);

		// behandelt die ereignisse, die durch den knopfdruck ausgeloest werden
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
					if (allowPlayer2) {
						player2.moveUp();
					}
					break;
				case S:
					if (allowPlayer2) {
						player2.moveDown();
					}
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

		// behandelt die ereignisse, die nach dem knopfdruck ausgeloest werden
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
			resultL = new Label("0");
			resultL.setTranslateX(450);
			resultL.setTranslateY(685);
			resultL.setTextFill(Color.WHITE);
			resultL.setFont(Font.font("Consolas", FontWeight.BOLD, 50));
			resultL.textProperty().bind(resultLeft);
			resultR = new Label("0");
			resultR.setTranslateX(550);
			resultR.setTranslateY(685);
			resultR.setTextFill(Color.WHITE);
			resultR.setFont(Font.font("Consolas", FontWeight.BOLD, 50));
			resultR.textProperty().bind(resultRight);
			win = new Label("Player 1 Wins");
			win.setTranslateX(195);
			win.setTranslateY(160);
			win.setFont(Font.font("Consolas", 30));
			win.textProperty().bind(winMsg);
			root.getChildren().addAll(background2, player2.player,
					player2.fireUp, player2.fireDown, player1.player,
					player1.fireUp, player1.fireDown, resultL, resultR, win);
		}
		root.setFocusTraversable(true);

		for (int i = 0; i < balls; i++) {
			addBall();
		}
		bounce();

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
		for (Ball ball : ballList) {
			if (ball.ballImageView.getX() >= 1000) {
				ballList.remove(ball);
				resultLeft.set("" + (Integer.parseInt(resultLeft.get()) + 1));
			}
			if (ball.ballImageView.getX() <= -30) {
				ballList.remove(ball);
				resultRight.set("" + (Integer.parseInt(resultRight.get()) + 1));
			}

			if (Integer.parseInt(resultLeft.get()) >= 21) {
				winMsg.set("Player 2 wins");
			}
			if (Integer.parseInt(resultRight.get()) >= 21) {
				winMsg.set("Player 1 wins");
			}
			System.out.println(ballList.size());
		}
	}

	// lets ball move and bounce off the top and bottom
	public void bounce() {
		for (Ball ball : ballList) {
			checkScore();
			KeyFrame keyFrame = new KeyFrame(new Duration(10), event -> {
				ball.ballImageView.setX(ball.ballImageView.getX()
						+ ball.getXSpeed());
				ball.ballImageView.setY(ball.ballImageView.getY()
						+ ball.getYSpeed());
				if (collision(ball)) {
					ball.t.stop();
					ball.setXSpeed(ball.getXSpeed() * -1);
					ballBounceOffPlayer(player1);
					ballBounceOffPlayer(player2);
					bounce();
				} else if (ball.ballImageView.getY() > 750
						|| ball.ballImageView.getY() < 20) {
					ball.t.stop();
					ball.setYSpeed(ball.getYSpeed() * -1);
					bounce();
				}
			});
			ball.t = new Timeline(keyFrame);
			ball.t.setCycleCount(Timeline.INDEFINITE);
			ball.t.play();
		}
	}

	// checks for collision
	public boolean collision(Ball ball) {
			if (ball.ballImageView.intersects(player2.player
					.getBoundsInParent())
					|| ball.ballImageView.intersects(player1.player
							.getBoundsInParent())) {
				return true;
			}
		return false;
	}

	// redirects the ball depending on the part of the paddle that got hit
	public void ballBounceOffPlayer(Paddle player) {
		for (Ball ball : ballList) {
			if (collision(ball)) {

				if (ball.ballImageView.getY() >= player.player.getY()
						&& ball.ballImageView.getY() < player.player.getY() + 98) {
					if (ball.getYSpeed() > 0) {
						ball.setYSpeed(ball.getYSpeed() * -1);
					}
				}
				if (ball.ballImageView.getY() > player.player.getY() + 98
						&& ball.ballImageView.getY() < player.player.getY() + 196) {
					if (ball.getYSpeed() < 0) {
						ball.setYSpeed(ball.getYSpeed() * -1);
					}
				}
			}
		}
	}
}
