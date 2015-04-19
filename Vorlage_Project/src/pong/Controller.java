package pong;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.*;

import java.util.Random;

public class Controller {
		


		 final int PADDLE_MOVEMENT_INCREMENT = 7;
		 final int BALL_MOVEMENT_INCREMENT = 3;

		 double centerTableY;

		 DoubleProperty currentPaddleY = new SimpleDoubleProperty();
		 DoubleProperty currentComputerPaddleY = new SimpleDoubleProperty();
		 double initialComputerPaddleY;

		 DoubleProperty ballCenterX = new SimpleDoubleProperty();
		 DoubleProperty ballCenterY = new SimpleDoubleProperty();

		 double allowedPaddleTopY;
		 double allowedPaddleBottomY;

		 Timeline timeline;

		 @FXML
		 Rectangle table;
		 @FXML  Rectangle paddle1;
		 @FXML  Rectangle paddle;
		 @FXML  Circle ball;

		 public void initialize()
		 {

		     currentPaddleY.set(paddle.getLayoutY());
		     paddle.layoutYProperty().bind(currentPaddleY);

		     ballCenterX.set(ball.getCenterX());
		     ballCenterY.set(ball.getCenterY());

		     ball.centerXProperty().bind(ballCenterX);
		     ball.centerYProperty().bind(ballCenterY);


		     initialComputerPaddleY = paddle1.getLayoutY();
		     currentComputerPaddleY.set(initialComputerPaddleY);
		     paddle1.layoutYProperty().bind(currentComputerPaddleY);


		     allowedPaddleTopY = PADDLE_MOVEMENT_INCREMENT;
		     allowedPaddleBottomY = table.getHeight() - paddle.getHeight() - PADDLE_MOVEMENT_INCREMENT;

		     centerTableY = table.getHeight()/2;
		 }
		 @SuppressWarnings("incomplete-switch")
		public void keyReleasedHandler(KeyEvent event){

		     KeyCode keyCode = event.getCode();

		     switch (keyCode){
		         case UP:
		             process_key_Up();
		             break;
		         case DOWN:
		             process_key_Down();
		             break;
		         case Q:
		             Platform.exit(); // Terminate the application
		             break;
		         case S:
		             process_key_S();
		             break;
		     }
		 }


		 private void process_key_Up() {

		     if (currentPaddleY.get() > allowedPaddleTopY) {
		         currentPaddleY.set(currentPaddleY.get() - PADDLE_MOVEMENT_INCREMENT);
		     }
		 }

		 private void process_key_Down() {

		     if (currentPaddleY.get()< allowedPaddleBottomY) {
		         currentPaddleY.set(currentPaddleY.get() + PADDLE_MOVEMENT_INCREMENT);
		     }
		 }


		 private void process_key_S() {

		     ballCenterY.set(currentPaddleY.doubleValue() + paddle.getHeight()/2);
		     ballCenterX.set(paddle.getLayoutX());

		     moveTheBall();
		 }

		 private void moveTheBall(){

		     Random randomYGenerator = new Random();
		     double randomYincrement = randomYGenerator.nextInt(BALL_MOVEMENT_INCREMENT);

		     final boolean isServingFromTop = (ballCenterY.get() <= centerTableY)?true:false;

		     KeyFrame keyFrame = new KeyFrame(new Duration(100), event -> {

		         if (ballCenterX.get() >= -20) {

		             ballCenterX.set(ballCenterX.get() - BALL_MOVEMENT_INCREMENT);

		             if (isServingFromTop) {
		                 ballCenterY.set(ballCenterY.get() + randomYincrement);

		                 currentComputerPaddleY.set( currentComputerPaddleY.get() + 0.5);

		             } else {
		                 ballCenterY.set(ballCenterY.get() - randomYincrement);

		                 currentComputerPaddleY.set(currentComputerPaddleY.get() - 0.5);
		             }


		         } else {
		             timeline.stop();

		             currentComputerPaddleY.set(initialComputerPaddleY);
		         }
		     });

		     timeline = new Timeline(keyFrame);
		     timeline.setCycleCount(Timeline.INDEFINITE);

		     timeline.play();

		}
		 
		}
		
	

