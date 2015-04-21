package pong;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Pong extends Application {
	
	Stage primaryStage;
	BorderPane root;
	Controller selectedGameStart; 
	
	public static void main(String[] args) {
		launch(args);
	} 

	@Override
	public void start(Stage primaryStage) throws Exception {
			this.primaryStage = primaryStage;
			this.root = new BorderPane();
		    
		    
		    TextField playerNumber = new TextField();
		    playerNumber.setPrefWidth(30);
		    TextField ballNumber = new TextField();
		    ballNumber.setPrefWidth(30);
		    Label selectPlayer = new Label("Number of Players");
		    Label selectBalls = new Label("Number of Balls");
		    Button startGame = new Button("Play");
		    
		    HBox top = new HBox(20);
		    top.getChildren().addAll(selectPlayer, playerNumber, selectBalls, ballNumber, startGame);
		    root.setTop(top);
		    //top.setFocusTraversable(true);
			
			
			
			startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					int numberOfPlayers = Integer.parseInt(playerNumber.getText());
					int numberOfBalls = Integer.parseInt(ballNumber.getText());
					System.out.println("Playing with " + numberOfPlayers + " players and " + numberOfBalls + " balls.");
					selectedGameStart = new Controller(numberOfPlayers, numberOfBalls);
				}
			});
			
			
			Scene scene = new Scene(root, 400, 50);
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
		
			primaryStage.show();
			//top.requestFocus();
		}  

	

	
}

