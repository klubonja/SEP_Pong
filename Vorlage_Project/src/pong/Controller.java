package pong;

import Model.*;

import java.util.LinkedList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Controller extends Stage {

	private Paddle player1;
	private boolean pl1;
	private Paddle player2;
	private boolean pl2;
	private Paddle player3;
	private boolean pl3;
	private Paddle player4;
	private boolean pl4;
	private List<Ball> ballList = new LinkedList<Ball>();
	private Group root = new Group();
	private Rectangle field = new Rectangle(600, 350, Color.GREEN);
	
	Paddle paddle1 = new Paddle(10, 125, 20, 100, Color.BLUE);
	Paddle paddle2 = new Paddle(570, 125, 20, 100, Color.ORANGE);
	Paddle paddle3 = new Paddle(250, 10, 100, 20, Color.YELLOW);
	Paddle paddle4 = new Paddle(250, 320, 100, 20, Color.VIOLET);
	
	final StringProperty resultLeft = new SimpleStringProperty("0");
	final StringProperty resultRight = new SimpleStringProperty("0");
	final StringProperty winMsg = new SimpleStringProperty("");
	

	public Controller(int playerNumber, int ballCount) {
		
		root.getChildren().add(field);
		
		this.player1 = paddle1;
		this.player2 = paddle2;
		this.player3 = paddle3;
		this.player4 = paddle4;
		
		switch(playerNumber){
		
		case 1:
			pl1=true;
			root.getChildren().addAll(player1); break;
		case 2:
			pl1=true;
			pl2=true;
			root.getChildren().addAll(player1, player2); break;
		case 3:
			pl1=true;
			pl2=true;
			pl3=true;
			root.getChildren().addAll(player1, player2, player3); break;
		case 4: 
			pl1=true;
			pl2=true;
			pl3=true;
			pl4=true;
			root.getChildren().addAll(player1, player2, player3, player4); break;	
		}
		
		if(!pl3 && !pl4){
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
		
		for(int i=0; i<ballCount; i++){
			addBall();
			}


		final KeyFrame moveBallsFrame = new KeyFrame(new Duration(16), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for (Ball ball : ballList) {
					ball.move();
				}
				
				checkCollision();
				checkScore();
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
		 		case W:
					player2.moveUp(); break;
				case S:
					player2.moveDown(); break;
				case RIGHT:
					player3.moveRight(); break;
				case LEFT:
					player3.moveLeft(); break;
				case A:
					player4.moveRight(); break;
				case D: 
					player4.moveLeft(); break;
				case B:
					addBall(); break;
				case Q:
					Platform.exit(); break;
					}
				
			}	
				
		});
		
		this.setScene(pongBoard);
		this.setResizable(false);
		this.initStyle(StageStyle.UNDECORATED);
		this.show();
		this.root.requestFocus();

	}
	
	
	public void addBall() {
		Ball newBall = new Ball(); 
		ballList.add(newBall);
		root.getChildren().add(newBall);
	}
	
	public void checkScore(){
		if(!pl3 && !pl4){
		for (Ball ball : ballList){
			if(ball.getCenterX()>=630){
				ballList.remove(ball);
				resultLeft.set("" + (Integer.parseInt(resultLeft.get()) + 1));
			}
			if(ball.getCenterX()<=-30){
				ballList.remove(ball);
				resultRight.set("" + (Integer.parseInt(resultRight.get()) + 1));
				}
			
			if(Integer.parseInt(resultLeft.get()) >= 21){
				winnerTransition(1);
			 }
			
			if(Integer.parseInt(resultLeft.get()) >= 21){
				winnerTransition(2);
			 }
		}
		}
	}
			
	private void checkCollision() {
		for (Ball ball : ballList) {
			if (pl1 && ball.getLayoutBounds().intersects(player1.getLayoutBounds())) {
				ball.collisionHorizontal();
				ball.move();
				continue;
			}
			else if (pl2 && ball.getLayoutBounds().intersects(player2.getLayoutBounds())) {
				ball.collisionHorizontal();
				ball.move();
				continue;
			}
			else if (pl3 && ball.getLayoutBounds().intersects(player3.getLayoutBounds())) {
				ball.collisionVertical();
				ball.move();
				continue;
			}
			else if (pl4 && ball.getLayoutBounds().intersects(player4.getLayoutBounds())) {
				ball.collisionVertical();
				ball.move();
				continue;
			}
		}
		return;
	}
	
	public void winnerTransition(int i){
		
		winMsg.set("Player " + i + " wins!");
		
		FillTransition ft = new FillTransition(Duration.millis(3000), field, Color.GREEN, Color.FUCHSIA);
	     ft.setCycleCount(10);
	     ft.setAutoReverse(true);
	     ft.play();
		
		
		
	}
			

	
}
