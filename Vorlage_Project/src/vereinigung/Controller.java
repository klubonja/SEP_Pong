package vereinigung;

import java.util.LinkedList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
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
import Model.Ball_old;
import Model.Paddle_old;

public class Controller extends Stage {
	final Image gestreifteRemulanerHintergrund2 = new Image(
			Main.class.getResourceAsStream("Hintergrund2.png"));
	final Image p1 = new Image(Main.class.getResourceAsStream("Player1.png"));;
	final Image p2 = new Image(Main.class.getResourceAsStream("Player2.png"));;
	final Image ballImage = new Image(
			Main.class.getResourceAsStream("Ball.png"));;

	final StringProperty resultLeft = new SimpleStringProperty("0");
	final StringProperty resultRight = new SimpleStringProperty("0");
	final StringProperty winMsg = new SimpleStringProperty("");

	private Paddle player1;
	private Paddle player2;
	private List<Ball> ballList = new LinkedList<Ball>();
	private Group root = new Group();

	public Controller(boolean players, int balls) {
		player1 = new Paddle(10, 304, p1);
		player2 = new Paddle(940, 304, p2);
		ImageView background2 = new ImageView();
		background2.setImage(gestreifteRemulanerHintergrund2);
		if (players) {
			root.getChildren().addAll(background2, player1);
		} else {
			root.getChildren().addAll(background2, player1.player, player2.player);
		}

		final KeyFrame moveBallsFrame = new KeyFrame(new Duration(16),
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						for (Ball ball : ballList) {
							ball.bounce();
						}
						checkCollision();
						checkScore();
					}
				});

		Timeline bmove = new Timeline();
		bmove.setCycleCount(Animation.INDEFINITE);
		bmove.getKeyFrames().add(moveBallsFrame);
		bmove.play();

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
					break;
				case Q:
					Platform.exit();
					break;
				}
			}
		});

		if (true) {
			Label resultL = new Label("0");
			resultL.setTranslateX(250);
			resultL.setTranslateY(300);
			resultL.setTextFill(Color.WHITE);
			resultL.setFont(Font.font("Consolas", FontWeight.BOLD, 30));
			resultL.textProperty().bind(resultLeft);
			Label resultR = new Label("0");
			resultR.setTranslateX(330);
			resultR.setTranslateY(300);
			resultR.setTextFill(Color.WHITE);
			resultR.setFont(Font.font("Consolas", FontWeight.BOLD, 30));
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

		this.setScene(pongBoard);
		this.setResizable(false);
		this.initStyle(StageStyle.UNDECORATED);
		this.show();
		this.root.requestFocus();
	}

	public void addBall() {
		Ball newBall = new Ball(480, 365, ballImage);
		ballList.add(newBall);
		root.getChildren().add(newBall.ball);
	}

	public void checkScore() {
		for (Ball ball : ballList) {
			if (ball.getX()+10 >= 630) {
				ballList.remove(ball);
				resultLeft.set("" + (Integer.parseInt(resultLeft.get()) + 1));
			}
			if (ball.getX()+10 <= -30) {
				ballList.remove(ball);
				resultRight.set("" + (Integer.parseInt(resultRight.get()) + 1));
			}

			if (Integer.parseInt(resultLeft.get()) >= 21) {
				winMsg.set("Player 1 wins");
			}
		}
	}

	public void checkCollision() {
		for (Ball ball : ballList) {
			if (ball.getLayoutBounds().intersects(player1.getBoundsInParent())) {
				ball.collision();
				continue;
			} else if (ball.getLayoutBounds().intersects(
					player2.getBoundsInParent())) {
				ball.collision();
				continue;
			}
		}
	}
}
