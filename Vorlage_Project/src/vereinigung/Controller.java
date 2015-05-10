package vereinigung;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.File;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Die Klasse Controller sorgt dafuer, dass alles in dem Spiel richtig ablaeuft.
 * Die Logik des Spiels ist in dieser Klasse enthalten.
 * 
 * @author GestreifteRemulaner
 *
 */
public class Controller extends Application {
	Paddle paddle = new Paddle(); // Model (Spieler)
	View1 view; // View 1 (Intro)
	View2 newGame; // View 2 (das eigentliche Spiel)
	// Ist dann wahr, wenn man mit der rechten Maustaste drueckt
	private boolean player1Mouse = false;
	// Ist dann wahr, wenn man mit der linken Maustaste drueckt
	private boolean player2Mouse = false;
	// Ist dann wahr, wenn ein Spieler gewonnen hat
	private boolean gameOver = false;
	private boolean leftClick = false;
	private boolean rightClick = false;

	/**
	 * Eine Methode, die dazu da ist, um das Spiel zu starten und die Rolle des
	 * Presenters zu uebernehmen.
	 * 
	 * @param stage
	 *            Das Fenster.
	 */
	public void start(Stage stage) {
		view = new View1();
		view.startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (view.playerNumber1.isSelected()) {
					view.players = true;

					int numberOfBalls = 0;
					try {
						numberOfBalls = Integer.parseInt(view.ballNumber
								.textProperty().get());
						if (numberOfBalls > 0) {
							newGame = new View2(view.players);
							for (int i = 0; i < numberOfBalls; i++) {
								addBall();
							}
							playGame();
							view.close();
						}
					} catch (IllegalArgumentException e1) {
						view.footerError.setValue("The input "
								+ view.ballNumber.getText()
								+ " is not a number!");
					}

				} else if (view.playerNumber2.isSelected()) {
					view.players = false;

					int numberOfBalls = 0;
					try {
						numberOfBalls = Integer.parseInt(view.ballNumber
								.textProperty().get());
						if (numberOfBalls > 0) {
							newGame = new View2(view.players);
							for (int i = 0; i < numberOfBalls; i++) {
								addBall();
							}
							view.close();
							playGame();
						}
					} catch (IllegalArgumentException e1) {
						view.footerError.setValue("The input "
								+ view.ballNumber.getText()
								+ " is not a number!");
					}

				} else if (!view.playerNumber1.isSelected()
						&& !view.playerNumber2.isSelected()) {
					view.footerError.setValue("Please select a playing mode!");
				}
			}
		});

		view.startGame.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						view.startGame.setEffect(new Bloom());
					}
				});
		// Removing the shadow when the mouse cursor is off
		view.startGame.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						view.startGame.setEffect(view.buttonShadow);
					}
				});

		view.quitGame.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						view.quitGame.setEffect(new Bloom());
					}
				});
		// Removing the shadow when the mouse cursor is off
		view.quitGame.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						view.quitGame.setEffect(view.buttonShadow);
					}
				});
		view.quitGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Platform.exit();
			}
		});
	}

	/**
	 * Eine Methode, die dann aufgerufen wird, wenn das Spiel losgehen kann. Die
	 * Methode erstellt das Spielbrett und verwaltet das eigentliche Spiel.
	 */
	public void playGame() {
		newGame.keyFrame = new KeyFrame(new Duration(10), event -> bounce());
		newGame.t = new Timeline(newGame.keyFrame);
		newGame.t.setCycleCount(Timeline.INDEFINITE);
		newGame.t.play();

		newGame.pongBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.SECONDARY) {
					if (!leftClick) {
						rightClick = true;
						player1Mouse = true;
						newGame.pongBoard.setCursor(Cursor.NONE);
					}
				}
				if (e.getButton() == MouseButton.PRIMARY) {
					if (!rightClick) {
						leftClick = true;
						player2Mouse = true;
						newGame.pongBoard.setCursor(Cursor.NONE);
					}
				}
			}
		});

		newGame.pongBoard.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (player1Mouse) {
					PointerInfo a = MouseInfo.getPointerInfo();
					Point b = a.getLocation();
					SimpleDoubleProperty y = new SimpleDoubleProperty();
					y.set((int) b.getY() - 80);
					newGame.player1.yProperty().bind(y);
					newGame.fireUp1.yProperty().bind(y);
					newGame.fireDown1.yProperty().bind(y);
					if (y.get() > 574) {
						y.set(574);
					}
				}
				if (player2Mouse) {
					PointerInfo a = MouseInfo.getPointerInfo();
					Point b = a.getLocation();
					SimpleDoubleProperty y = new SimpleDoubleProperty();
					y.set((int) b.getY() - 80);
					newGame.player2.yProperty().bind(y);
					newGame.fireUp2.yProperty().bind(y);
					newGame.fireDown2.yProperty().bind(y);
					if (y.get() > 574) {
						y.set(574);
					}
				}
			}
		});

		// behandelt die ereignisse, die durch den knopfdruck ausgeloest werden
		newGame.pongBoard.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@SuppressWarnings("incomplete-switch")
			@Override
			public void handle(KeyEvent evt) {
				KeyCode keyCode = evt.getCode();
				switch (keyCode) {
				case UP:
					if (!player1Mouse) {
						moveUp(newGame.player1, newGame.fireUp1,
								newGame.fireDown1, paddle.getSpeed());
					}
					break;
				case DOWN:
					if (!player1Mouse) {
						moveDown(newGame.player1, newGame.fireUp1,
								newGame.fireDown1, paddle.getSpeed());
					}
					break;
				case W:
					if (newGame.allowPlayer2 || !player2Mouse) {
						moveUp(newGame.player2, newGame.fireUp2,
								newGame.fireDown2, paddle.getSpeed());
					}
					break;
				case S:
					if (newGame.allowPlayer2 || !player2Mouse) {
						moveDown(newGame.player2, newGame.fireUp2,
								newGame.fireDown2, paddle.getSpeed());
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
		newGame.pongBoard.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@SuppressWarnings("incomplete-switch")
			@Override
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				switch (code) {
				case UP:
					fadeFire(newGame.fireDown1);
					break;
				case DOWN:
					fadeFire(newGame.fireUp1);
					break;
				case W:
					fadeFire(newGame.fireDown2);
					break;
				case S:
					fadeFire(newGame.fireUp2);
					break;
				}
			}
		});

		newGame.resultL.textProperty().bind(newGame.resultLeft);
		newGame.resultR.textProperty().bind(newGame.resultRight);
		newGame.win.textProperty().bind(newGame.winMsg);
	}

	/**
	 * Fuegt einen Ball hinzu und laesst den Ball pulsieren.
	 */
	public void addBall() {
		if (!gameOver) {
			BallView newBall = new BallView(480, 365, 10);
			newBall.centerXProperty().bindBidirectional(
					newBall.ballModel.getCenterXProperty());
			newBall.centerYProperty().bindBidirectional(
					newBall.ballModel.getCenterYProperty());
			newBall.setFill(Color.WHITE);
			newGame.ballList.add(newBall);
			newGame.root.getChildren().add(newBall);
			scaleTrns(newBall);
		}
	}

	/**
	 * Ueberprueft, ob der Ball aus der linken bzw. rechten Seite aus dem Spiel
	 * geflogen ist und regelt die Punkte der Spieler.
	 * 
	 * Sobald ein Spieler das Spiel gewonnen hat(21 Punkte erreicht hat),
	 * erscheint eine Nachricht mit dem Gewinner und ein Button, der ein neues
	 * Spiel erlaubt.
	 */
	public void checkScore() {
		for (Iterator<BallView> iterator = newGame.ballList.iterator(); iterator
				.hasNext();) {
			BallView ball = iterator.next();
			if (ball.getCenterX() >= 1020) {
				ballOutSound();
				try {
					iterator.remove();
				} catch (ConcurrentModificationException e) {
				}
				newGame.resultLeft.set(""
						+ (Integer.parseInt(newGame.resultLeft.get()) + 1));
			}
			if (ball.getCenterX() <= -40) {
				ballOutSound();
				try {
					iterator.remove();
				} catch (ConcurrentModificationException e) {
				}
				newGame.resultRight.set(""
						+ (Integer.parseInt(newGame.resultRight.get()) + 1));
			}

			if (Integer.parseInt(newGame.resultLeft.get()) >= 21) {
				gameOver = true;
				applauseSound();
				newGame.pongBoard.setCursor(Cursor.DEFAULT);
				newGame.winMsg.set("Green player wins!");
				restartGameButton();
			}
			if (Integer.parseInt(newGame.resultRight.get()) >= 21) {
				gameOver = true;
				applauseSound();
				newGame.pongBoard.setCursor(Cursor.DEFAULT);
				newGame.winMsg.set("Red player wins!");
				restartGameButton();
			}
		}
	}

	/**
	 * Sobald ein Spieler das Spiel gewonnen hat, erscheint ein Button, der ein
	 * neues Spiel erlaubt.
	 */
	public void restartGameButton() {
		newGame.root.getChildren().add(newGame.restart);
		newGame.restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				newGame.pongBoard.setCursor(Cursor.NONE);
				gameOver = false;
				newGame.root.getChildren().remove(newGame);
				newGame.resultLeft.set("0");
				newGame.resultRight.set("0");
				newGame.winMsg.set("");
				addBall();
				newGame.root.getChildren().remove(newGame.restart);
			}
		});
	}

	/**
	 * Laesst den Ball von dem oberen und unteren Rand des Spielfeldes abprallen
	 * und ueberprueft, ob es zur Collision mit einem Spieler gekommen ist.
	 */
	public void bounce() {
		for (Iterator<BallView> iterator = newGame.ballList.iterator(); iterator
				.hasNext();) {
			BallView ball = iterator.next();
			checkScore();
			ball.setCenterX(ball.getCenterX() + ball.ballModel.getXSpeed());
			ball.setCenterY(ball.getCenterY() + ball.ballModel.getYSpeed());
			if (collision(ball)) {
				ball.ballModel.setXSpeed(ball.ballModel.getXSpeed() * -1);
				ballBounceOffPlayer(newGame.player1);
				ballBounceOffPlayer(newGame.player2);
			} else if (ball.getCenterY() > 750 || ball.getCenterY() < 20) {
				ball.ballModel.setYSpeed(ball.ballModel.getYSpeed() * -1);
			}
		}
	}

	/**
	 * Eine Hilfsmethode, die ueberprueft, ob der Ball einen der Spieler
	 * getroffen hat.
	 * 
	 * @param ball
	 *            Selbsterklaerend.
	 * @return True, falls der Ball einen Spieler getroffen hat. Sonst false.
	 */
	public boolean collision(Circle ball) {
		if (ball.intersects(newGame.player2.getBoundsInParent())
				|| ball.intersects(newGame.player1.getBoundsInParent())) {
			return true;
		}
		return false;
	}

	/**
	 * Ueberprueft, wo der Ball den Spieler getroffen hat und sorgt dafuer, dass
	 * der Ball in eine bestimmte Richtung abprallt.
	 * 
	 * @param player
	 */
	public void ballBounceOffPlayer(ImageView player) {
		for (BallView ball : newGame.ballList) {
			bounceSound();
			if (collision(ball)) {
				if (ball.getCenterY() >= player.getY()
						&& ball.getCenterY() <= player.getY() + 98) {
					if (ball.ballModel.getYSpeed() > 0) {
						ball.ballModel.setYSpeed(ball.ballModel.getYSpeed()
								* -1);
					}
				}
				if (ball.getCenterY() >= player.getY() + 98
						&& ball.getCenterY() <= player.getY() + 196) {
					if (ball.ballModel.getYSpeed() < 0) {
						ball.ballModel.setYSpeed(ball.ballModel.getYSpeed()
								* -1);
					}
				}
			}
		}
	}

	/**
	 * Laesst den Ball pulsieren.
	 * 
	 * @param ball
	 *            Selbsterklaerend. .
	 */
	public void scaleTrns(Circle ball) {

		ScaleTransition grow;
		grow = new ScaleTransition();
		grow.setDuration(Duration.millis(3000));
		grow.setCycleCount(Animation.INDEFINITE);
		grow.setNode(ball);
		grow.setToX(2f);
		grow.setToY(2f);
		grow.setAutoReverse(true);
		grow.play();
	}

	/**
	 * Eine Methode die den Spieler nach oben beschlaeunigt.
	 * 
	 * @param player
	 *            Der Schlaeger.
	 * @param fireUp
	 *            Das Feuer ueber dem Schlaeger.
	 * @param fireDown
	 *            Das Feuer unter dem Schlaeger.
	 * @param speed
	 *            Die Geschwindigkeit, mit der sich der Spieler bewegt.
	 */
	public void moveUp(ImageView player, ImageView fireUp, ImageView fireDown,
			int speed) {
		fireDown.setOpacity(100);
		player.setY(player.getY() - speed);
		fireDown.setY(fireDown.getY() - speed);
		fireUp.setY(fireUp.getY() - speed);
		if (player.getY() < 0) {
			player.setY(0);
			fireDown.setY(-57);
			fireUp.setY(196);
		}
	}

	/**
	 * Eine Methode die den Spieler nach unten beschlaeunigt.
	 * 
	 * @param player
	 *            Der Schlaeger.
	 * @param fireUp
	 *            Das Feuer ueber dem Schlaeger.
	 * @param fireDown
	 *            Das Feuer unter dem Schlaeger.
	 * @param speed
	 *            Die Geschwindigkeit, mit der sich der Spieler bewegt.
	 */
	public void moveDown(ImageView player, ImageView fireUp,
			ImageView fireDown, int speed) {
		fireUp.setOpacity(100);
		player.setY(player.getY() + speed);
		fireUp.setY(fireUp.getY() + speed);
		fireDown.setY(fireDown.getY() + speed);
		if (player.getY() + player.getFitHeight() > 570) {
			player.setY(570 - player.getFitHeight());
			fireUp.setY(570 - player.getFitHeight() - 57);
			fireDown.setY(769);
		}
	}

	/**
	 * Eine Hilfsmethode, die das Feuer von den Schlaegern verdeckt.
	 * 
	 * @param img
	 *            Das Bildelement von dem Feuer.
	 */
	public void fadeFire(ImageView img) {
		img.setOpacity(0);
	}

	/**
	 * Laesst ein applaudierendes Geraeusch spielen, sobald einer der Spieler
	 * gewonnen hat.
	 */
	public static void applauseSound() {
		String sound1 = "media/Applause.mp3";
		Media mediaFile1 = new Media(new File(sound1).toURI().toString());
		MediaPlayer mediaplayer1 = new MediaPlayer(mediaFile1);
		mediaplayer1.setAutoPlay(true);
		mediaplayer1.setVolume(0.5);
	}

	/**
	 * Laesst ein Geraeusch spielen, sobald ein Ball einen Schlaeger erwischt.
	 */
	public void bounceSound() {
		String sound2 = "media/BEEPDROP.mp3";
		Media mediaFile2 = new Media(new File(sound2).toURI().toString());
		MediaPlayer mediaplayer2 = new MediaPlayer(mediaFile2);
		mediaplayer2.setAutoPlay(true);
		mediaplayer2.setVolume(0.5);
	}

	/**
	 * Laesst ein Geraeusch spielen, sobald ein Ball aus dem Fenster fliegt.
	 */
	public void ballOutSound() {
		String sound3 = "media/BEEPARCA.mp3";
		Media mediaFile3 = new Media(new File(sound3).toURI().toString());
		MediaPlayer mediaplayer3 = new MediaPlayer(mediaFile3);
		mediaplayer3.setAutoPlay(true);
		mediaplayer3.setVolume(0.5);
	}

	/**
	 * Die main-Methode ist dazu da, damit das Spiel mit der launch() Methode
	 * zum Laufen kommt.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
