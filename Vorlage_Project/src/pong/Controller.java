package pong;

import Model.*;

import java.util.LinkedList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
	
	Paddle paddle1 = new Paddle(10, 125, 20, 100, Color.BLUE);
	Paddle paddle2 = new Paddle(570, 125, 20, 100, Color.ORANGE);
	Paddle paddle3 = new Paddle(250, 10, 100, 20, Color.YELLOW);
	Paddle paddle4 = new Paddle(250, 320, 100, 20, Color.VIOLET);
	
	final SimpleStringProperty resultLeft = new SimpleStringProperty("0");
	final SimpleStringProperty resultRight = new SimpleStringProperty("0");
	

	public Controller(int playerNumber, int ballCount) {
		
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
			resultL.setTranslateX(290);
			resultL.setTranslateY(300);
			resultL.textProperty().bind(resultLeft);
			Label resultR = new Label("0");
			resultR.setTranslateX(310);
			resultR.setTranslateY(300);
			resultR.textProperty().bind(resultRight);
			root.getChildren().addAll(resultL, resultR);
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
		}
		}
	}
			
	private void checkCollision() {
		for (Ball ball : ballList) {
			if (pl1 && ball.getBoundsInParent().intersects(player1.getBoundsInParent())) {
				ball.collisionHorizontal();
				ball.move();
				continue;
			}
			else if (pl2 && ball.getBoundsInParent().intersects(player2.getBoundsInParent())) {
				ball.collisionHorizontal();
				ball.move();
				continue;
			}
			else if (pl3 && ball.getBoundsInParent().intersects(player3.getBoundsInParent())) {
				ball.collisionVertical();
				ball.move();
				continue;
			}
			else if (pl4 && ball.getBoundsInParent().intersects(player4.getBoundsInParent())) {
				ball.collisionVertical();
				ball.move();
				continue;
			}
		}
		return;
	}
			

	
}
