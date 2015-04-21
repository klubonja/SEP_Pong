package pong;

import Model.*;

import java.util.LinkedList;
import java.util.List;





import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
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
	
	private ParallelTransition parallel = new ParallelTransition();
	private Paddle player1;
	private Paddle player2;
	private Paddle player3;
	private Paddle player4;
	private Group root = new Group();
	private List<Ball> ballList = new LinkedList<Ball>();
	public LinkedList<Paddle> paddleList = new LinkedList<Paddle>();
	
	Paddle paddle1 = new Paddle(10, 125, 20, 100, Color.BLUE);
	Paddle paddle2 = new Paddle(580, 125, 20, 100, Color.ORANGE);
	
	public Controller(int playerNumber, int ballCount) {
		this.player1 = paddle1;
		this.player2 = paddle2;
		root.setFocusTraversable(true);
		root.getChildren().addAll(player1, player2);
		
		addBall();
		
		Scene scene = new Scene(root, 600, 350);
		scene.setFill(Color.GREEN);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@SuppressWarnings("incomplete-switch")
			@Override
			public void handle(KeyEvent evt) {
				
				KeyCode keyCode = evt.getCode();
			
				switch(keyCode){
				
				case UP:
					player1.moveUp(); break;
				case DOWN:
					player1.moveDown(); break;
				case Q:
					Platform.exit();
					}
				
			}	
				
				
			
		});
		
		setScene(scene);
		setResizable(false);
		show();
		root.requestFocus();

	}
	
	public void addBall() {
		Ball neu = new Ball(); 
		neu.setCenterX(600 / 2);
		neu.setCenterY(350 / 2);
		root.getChildren().add(neu);
		ballList.add(neu);
	}
	
}