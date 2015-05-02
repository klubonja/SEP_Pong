package pong;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BallCollision extends Application {

    public static Circle ball;
    public static Pane table;
    public static Rectangle paddle1;
    public static Rectangle paddle2;

    @Override
    public void start(final Stage primaryStage) {

        table = new Pane();
        final Scene scene = new Scene(table, 800, 600);
        primaryStage.setTitle("Pong");
        primaryStage.setScene(scene);
        primaryStage.show();
     
        //Erstellt und platziert den Ball
        ball = new Circle(10, Color.RED);
        ball.relocate(400, 300);
        
        //Erstellt Schläger
        paddle1 = new Rectangle(10, 50, Color.BLUE);
        paddle2 = new Rectangle(10, 50, Color.DARKGREEN);
        paddle1.relocate(50, 275);
        paddle2.relocate(750, 275);       
        
        // alles aufs Spielfeld hinzufügen
        table.getChildren().addAll(ball);
        table.getChildren().addAll(paddle1);
        table.getChildren().addAll(paddle2);
        
        // startet das "Spiel"/ die Ball Bewegung
        moveTheBall();
        
    }
    //Ball Bewegung
    private void moveTheBall(){
        final Timeline loop = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
        	//Verschiebung in X und Y Richtung
            double deltaX = 3;
            double deltaY = 3;

            public void handle(final ActionEvent t) {
                ball.setLayoutX(ball.getLayoutX() + deltaX);
                ball.setLayoutY(ball.getLayoutY() + deltaY);

                final Bounds bounds = table.getBoundsInLocal();
                final boolean atRightBorder = ball.getLayoutX() >= (bounds.getMaxX() - ball.getRadius());
                final boolean atLeftBorder = ball.getLayoutX() <= (bounds.getMinX() + ball.getRadius());
                final boolean atBottomBorder = ball.getLayoutY() >= (bounds.getMaxY() - ball.getRadius());
                final boolean atTopBorder = ball.getLayoutY() <= (bounds.getMinY() + ball.getRadius());
                
                // Kollision oben/unten -> prallt ab, Kollision rechts/links -> "Spiel startet auf der Verliererseite"
                if (atRightBorder) {
                	ball.relocate(730, 300);
                	deltaX *= -1;   	
                }
                if (atLeftBorder){
                	ball.relocate(60, 300);
                	deltaX *= -1;
                }
                if (atBottomBorder || atTopBorder) {
                    deltaY *= -1;
                }
            }
        }));
        
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}