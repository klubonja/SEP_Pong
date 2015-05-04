package vereinigung;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.LinkedList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Controller extends Stage {
	final Image gestreifteRemulanerHintergrund2 = new Image("/Hintergrund2.png");
	final Image p1 = new Image("/Player1.png");
	final Image p2 = new Image("/Player2.png");
	final Image fireUp = new Image("/Player1FeuerUp.png");
	final Image fireDown = new Image("/Player1FeuerDown.png");
	final Image ballImage = new Image("/Ball.png");

	// die Punkte von player2
	final StringProperty resultLeft = new SimpleStringProperty("0");
	// die Punkte von player1
	final StringProperty resultRight = new SimpleStringProperty("0");
	// die Nachricht mit dem Geweinner
	final StringProperty winMsg = new SimpleStringProperty("");

	private Label resultL; // die Punkte von player2
	private Label resultR; // die Punkte von player1
	private Label win; // die Nachricht mit dem Geweinner

	private Paddle player2;
	private Paddle player1;
	private List<Ball> ballList = new LinkedList<Ball>();
	private Group root = new Group();
	private boolean allowPlayer2 = false;
	private boolean player1Mouse = false;
	private boolean player2Mouse = false;
	Timeline t;

	// constructor
	public Controller(boolean players, int balls) {
		player2 = new Paddle(10, 304, p1, 10, 247, fireUp, 10, 502, fireDown);
		player1 = new Paddle(940, 304, p2, 940, 247, fireUp, 940, 502, fireDown);
		ImageView background2 = new ImageView();
		background2.setImage(gestreifteRemulanerHintergrund2);
		if (!players) {
			allowPlayer2 = true;
		}
		Scene pongBoard = new Scene(root, 1000, 770);

		pongBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.SECONDARY) {
					player1Mouse = true;
				}
				if (e.getButton() == MouseButton.PRIMARY) {
					player2Mouse = true;
				}
			}
		});

		pongBoard.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (player1Mouse) {
					PointerInfo a = MouseInfo.getPointerInfo();
					Point b = a.getLocation();
					SimpleDoubleProperty y = new SimpleDoubleProperty();
					y.set((int) b.getY() - 80);
					player1.player.yProperty().bind(y);
					player1.fireUp.yProperty().bind(y);
					player1.fireDown.yProperty().bind(y);
					if (y.get() > 574) {
						y.set(574);
					}
				}
				if (player2Mouse) {
					PointerInfo a = MouseInfo.getPointerInfo();
					Point b = a.getLocation();
					SimpleDoubleProperty y = new SimpleDoubleProperty();
					y.set((int) b.getY() - 80);
					player2.player.yProperty().bind(y);
					player1.fireUp.yProperty().bind(y);
					player1.fireDown.yProperty().bind(y);
					if (y.get() > 574) {
						y.set(574);
					}
				}
			}
		});

		// behandelt die ereignisse, die durch den knopfdruck ausgeloest werden
		pongBoard.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@SuppressWarnings("incomplete-switch")
			@Override
			public void handle(KeyEvent evt) {
				KeyCode keyCode = evt.getCode();
				switch (keyCode) {
				case UP:
					if (!player1Mouse) {
						player1.moveUp();
					}
					break;
				case DOWN:
					if (!player1Mouse) {
						player1.moveDown();
					}
					break;
				case W:
					if (allowPlayer2 || !player2Mouse) {
						player2.moveUp();
					}
					break;
				case S:
					if (allowPlayer2 || !player2Mouse) {
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
			win = new Label("");
			win.setTranslateX(150);
			win.setTranslateY(305);
			win.setFont(Font.font("Consolas", 80));
			win.setTextFill(Color.WHITE);
			win.textProperty().bind(winMsg);
			root.getChildren().addAll(background2, player2.player,
					player2.fireUp, player2.fireDown, player1.player,
					player1.fireUp, player1.fireDown, resultL, resultR, win);
		}
		root.setFocusTraversable(true);

		for (int i = 0; i < balls; i++) {
			addBall();
		}
		KeyFrame keyFrame = new KeyFrame(new Duration(10), event -> bounce());
		t = new Timeline(keyFrame);
		t.setCycleCount(Timeline.INDEFINITE);
		t.play();

		pongBoard.setCursor(Cursor.NONE);
		this.setScene(pongBoard);
		this.setResizable(false);
		this.initStyle(StageStyle.UNDECORATED);
		this.show();
		this.root.requestFocus();
	}

	// fuegt einen Ball hinzu
	public void addBall() {
		Ball newBall = new Ball(480, 365, ballImage);
		ballList.add(newBall);
		root.getChildren().add(newBall.ballImageView);
	}

	// ueberprueft, ob der Ball aus dem Fenster geflogen ist und ob einer der
	// Spieler 21 Punkte erreicht hat
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
				winMsg.set("Red player wins!");
			}
			if (Integer.parseInt(resultRight.get()) >= 21) {
				winMsg.set("Green player wins!");
			}
		}
	}

	// lets ball move and bounce off the top and bottom
	public void bounce() {
		for (Ball ball : ballList) {
			checkScore();
			ball.ballImageView.setX(ball.ballImageView.getX()
					+ ball.getXSpeed());
			ball.ballImageView.setY(ball.ballImageView.getY()
					+ ball.getYSpeed());
			if (collision(ball)) {
				ball.setXSpeed(ball.getXSpeed() * -1);
				ballBounceOffPlayer(player1);
				ballBounceOffPlayer(player2);
			} else if (ball.ballImageView.getY() > 750
					|| ball.ballImageView.getY() < 20) {
				ball.setYSpeed(ball.getYSpeed() * -1);
			}
		}
	}

	// checks for collision
	public boolean collision(Ball ball) {
		if (ball.ballImageView.intersects(player2.player.getBoundsInParent())
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
						&& ball.ballImageView.getY() <= player.player.getY() + 98) {
					if (ball.getYSpeed() > 0) {
						ball.setYSpeed(ball.getYSpeed() * -1);
					}
				}
				if (ball.ballImageView.getY() >= player.player.getY() + 98
						&& ball.ballImageView.getY() <= player.player.getY() + 196) {
					if (ball.getYSpeed() < 0) {
						ball.setYSpeed(ball.getYSpeed() * -1);
					}
				}
			}
		}
	}
}
