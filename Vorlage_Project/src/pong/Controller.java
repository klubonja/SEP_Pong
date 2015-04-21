package pong;

import Model.*;

import java.util.LinkedList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller extends Stage {

	private Paddle player1;
	private Paddle player2;
	private Paddle player3;
	private Paddle player4;
	private List<Ball> ballList = new LinkedList<Ball>();
	private Group root = new Group();
	
	Paddle paddle1 = new Paddle(10, 125, 20, 100, Color.BLUE);
	Paddle paddle2 = new Paddle(580, 125, 20, 100, Color.ORANGE);
	Paddle paddle3 = new Paddle(250, 10, 100, 20, Color.YELLOW);
	Paddle paddle4 = new Paddle(250, 330, 100, 20, Color.VIOLET);
	

	public Controller(int playerNumber, int ballCount) {
		
		this.player1 = paddle1;
		this.player2 = paddle2;
		this.player3 = paddle3;
		this.player4 = paddle4;
		
		switch(playerNumber){
		
		case 1:
			root.getChildren().addAll(player1); break;
		case 2:
			root.getChildren().addAll(player1, player2); break;
		case 3:
			root.getChildren().addAll(player1, player2, player3); break;
		case 4: 
			root.getChildren().addAll(player1, player2, player3, player4); break;	
		}
		
		root.setFocusTraversable(true);
		
		addBall();

		Duration moveBalls = Duration.millis(16);
		final KeyFrame moveBallsFrame = new KeyFrame(moveBalls, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for (Ball ball : ballList) {
					ball.move();
				}
				//checkCollisions();
			}
		});
		Timeline bmove = new Timeline();
		bmove.setCycleCount(Animation.INDEFINITE);
		bmove.getKeyFrames().add(moveBallsFrame);
		bmove.play();
		
		
		
		Scene pongBoard = new Scene(root, 600, 350);
		pongBoard.setFill(Color.GREEN);
		pongBoard.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@SuppressWarnings("incomplete-switch")
			@Override
			public void handle(KeyEvent evt) {
				
				KeyCode keyCode = evt.getCode();
			
				switch(keyCode){
				
				case UP:
					player1.moveUp(); break;
				case DOWN:
					player1.moveDown(); break;
				case B:
					addBall(); break;
				case Q:
					Platform.exit(); break;
					}
				
			}	
				
				
			
		});
		
		setScene(pongBoard);
		setResizable(false);
		show();
		root.requestFocus();

	}
	
	public void addBall() {
		Ball neu = new Ball(); 
		neu.setCenterX(600 / 2);
		neu.setCenterY(350 / 2);
		ballList.add(neu);
		root.getChildren().add(neu);
	}
	
}