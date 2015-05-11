package networking;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
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
import vereinigung.BallView;
import vereinigung.Controller;
import vereinigung.Paddle;
import vereinigung.View2;

/**
 * Die Klasse Controller sorgt dafuer, dass alles in dem Spiel richtig ablaeuft.
 * Die Logik des Spiels ist in dieser Klasse enthalten.
 * 
 * @author GestreifteRemulaner
 *
 */
public class PongClient extends Application {
	private String host;
	@SuppressWarnings("unused")
	private int port;
	private Socket socket;
	private final String DEFAULT_HOST = "localhost";
	Controller game;

	private int updater = 304;
	private int receivedBall = 0;

	/**
	 * Erstellt ein Client Socket mit host und port.
	 * 
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	public void connect(String host, int port) throws IOException {
		this.host = host;
		this.port = port;
		socket = new Socket(host, port);
		System.out.println("Client has been connected..");
	}

	/**
	 * Hoert auf Nachrichten von dem Server und nimmt sie an.
	 * 
	 * @throws IOException
	 */
	public void approve() throws IOException {
		new Thread() {
			public void run() {
				while (true) {
					try {
						@SuppressWarnings("unused")
						int received = receive();
					} catch (IOException e) {
					}
				}
			}
		}.start();
		startBall();
	}

	/**
	 * Liest die naechste Zeile und macht daraus ein Int.
	 * 
	 * @return
	 * @throws IOException
	 */
	public int receive() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream(), "UTF-8"));
		String line = in.readLine();
		int message = Integer.parseInt(line);
		if (message == -1337) {
			receivedBall = message;
		} else {
			updater = message;
		}
		return message;
	}

	/**
	 * Schickt eine Nachricht zum Server.
	 * 
	 * @param message
	 *            Die Nachricht.
	 * @throws IOException
	 */
	public void send(int message) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(
				socket.getOutputStream(), "UTF-8");
		out.write(message + "\n");
		out.flush();
		System.out.println("Sent to server: " + " " + message);
	}

	/**
	 * Getter fuer den Host.
	 * 
	 * @return
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * Getter fuer den Client Socket.
	 * 
	 * @return
	 */
	public Socket getSocket() {
		return this.socket;
	}

	Paddle paddle = new Paddle(); // Model (Spieler)
	View2 newGame; // View 2 (das eigentliche Spiel)
	// Ist dann wahr, wenn man mit der rechten Maustaste drueckt
	private boolean player1Mouse = false;
	// Ist dann wahr, wenn man mit der linken Maustaste drueckt
	private boolean player2Mouse = false;
	// Ist dann wahr, wenn ein Spieler gewonnen hat
	private boolean gameOver = false;
	private Boolean gameStart = true;
	Timeline timer;

	/**
	 * Eine Methode, die dazu da ist, um das Spiel zu starten und die Rolle des
	 * Presenters zu uebernehmen.
	 * 
	 * @param stage
	 *            Das Fenster.
	 */
	public void start(Stage stage) {
		newGame = new View2(false);
		addBall();
		playGame();
		update();
	}

	/**
	 * Eine Methode, die dann aufgerufen wird, wenn das Spiel losgehen kann. Die
	 * Methode erstellt das Spielbrett und verwaltet das eigentliche Spiel.
	 */
	public void playGame() {
		new Thread() {
			public void run() {
				try {
					// InetAddress addr = InetAddress.getLocalHost();
					// String ip = "138.246.2.135";
					connect("localhost", 7777);
					System.out.println("I am this far.");
					approve();
				}

				catch (ConnectException e) {
					System.err.println(host + " connect refused");
					return;
				}

				catch (UnknownHostException e) {
					System.err.println(host + " Unknown host");
					host = DEFAULT_HOST;
					return;
				}

				catch (NoRouteToHostException e) {
					System.err.println(host + " Unreachable");
					return;

				}

				catch (IllegalArgumentException e) {
					System.err.println(host + " wrong port");
					return;
				}

				catch (IOException e) {
					System.err.println(host + ' ' + e.getMessage());
					System.err.println(e);
				} finally {
					// try {
					// socket.close();
					// } catch (IOException e) {
					// e.printStackTrace();
					// }
				}
			}
		}.start();
		newGame.keyFrame = new KeyFrame(new Duration(10), event -> bounce());
		newGame.t = new Timeline(newGame.keyFrame);
		newGame.t.setCycleCount(Timeline.INDEFINITE);

		newGame.pongBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.SECONDARY) {
					player1Mouse = true;
					newGame.pongBoard.setCursor(Cursor.NONE);
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
				case B:
					try {
						send(-1337);
					} catch (IOException e) {
						e.printStackTrace();
					}
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
				}
			}
		});

		newGame.resultL.textProperty().bind(newGame.resultLeft);
		newGame.resultR.textProperty().bind(newGame.resultRight);
		newGame.win.textProperty().bind(newGame.winMsg);
	}

	/**
	 * Startet die Spielanimation.
	 */
	public void startBall() {
		if (gameStart) {
			newGame.t.play();
			gameStart = false;
		}
	}

	/**
	 * Fuegt einen Ball hinzu und laesst den Ball pulsieren.
	 */
	public void addBall() {
		if (!gameOver || receivedBall > 0) {
			BallView newBall = new BallView(480, 365, 10);
			newBall.centerXProperty().bindBidirectional(
					newBall.ballModel.getCenterXProperty());
			newBall.centerYProperty().bindBidirectional(
					newBall.ballModel.getCenterYProperty());
			newBall.setFill(Color.WHITE);
			newGame.ballList.add(newBall);
			newGame.root.getChildren().add(newBall);
			scaleTrns(newBall);
			receivedBall = 0;
		}
	}

	/**
	 * Aendert die Position des Gegners und fuegt einen Ball hinzu, wenn der
	 * Gegner einen Ball hinzufuegen will.
	 */
	public void updatePosition() {
		newGame.player2.setY(updater);
		if (receivedBall == -1337) {
			addBall();
		}
	}

	/**
	 * Startet eine Timeline, die dafuer sorgt, dass die Aenderungen, die von
	 * dem Gegner verursacht sind, auf dem eigenen Bildschirm erscheinen.
	 */
	public void update() {
		timer = new Timeline(new KeyFrame(new Duration(10),
				ae -> updatePosition()));
		timer.setCycleCount(Animation.INDEFINITE);
		timer.play();
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
			fireUp.setY(-57);
			fireDown.setY(196);
		}
		try {
			send((int) newGame.player1.getY());
		} catch (IOException e) {
			e.printStackTrace();
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
		try {
			send((int) newGame.player1.getY());
		} catch (IOException e) {
			e.printStackTrace();
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
