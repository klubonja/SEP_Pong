package pong;
	
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Pong extends Application {
	
	Stage primaryStage;
	GridPane root;
	Controller selectedGameStart; 
    SimpleStringProperty footerError = new SimpleStringProperty();
	
	public static void main(String[] args) {
		launch(args);
	} 

	@Override
	public void start(Stage primaryStage) throws Exception {
			this.primaryStage = primaryStage;
			this.root = new GridPane();
			root.setVgap(10);
			root.setHgap(10);
			root.setPadding(new Insets(10));
			root.setAlignment(Pos.CENTER);
			root.setStyle("-fx-background-color: radial-gradient( radius 100%, snow, skyblue, mediumturquoise)");
		    
		    Label title = new Label("PingPong");
		    title.setStyle("-fx-font-size: 36");
		    Label author = new Label("GestreifteRemulaner 2015");
		    author.setStyle("-fx-font-size: 20");
		    Label selectPlayer = new Label("Select number of PLAYERS");
		    Label selectBalls = new Label("Select number of BALLS");
		    TextField playerNumber = new TextField();
		    playerNumber.setPrefWidth(30);
		    TextField ballNumber = new TextField();
		    ballNumber.setPrefWidth(30);
		    Button startGame = new Button("Play");
		    Label error = new Label("");
		    error.textProperty().bind(footerError);
		    
		    GridPane.setConstraints(title, 0, 0);
		    GridPane.setConstraints(author, 0, 1);
		    GridPane.setConstraints(selectPlayer, 0, 2);
		    GridPane.setConstraints(playerNumber, 1, 2);
		    GridPane.setConstraints(selectBalls, 0, 3);
		    GridPane.setConstraints(ballNumber, 1, 3);
		    GridPane.setConstraints(startGame, 0, 4);
		    GridPane.setConstraints(error, 0,5);
		    
		   root.getChildren().addAll(title, author, selectPlayer, playerNumber, selectBalls, ballNumber, startGame, error);
		 
		   
			startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					int numberOfPlayers = Integer.parseInt(playerNumber.getText());
					int numberOfBalls = Integer.parseInt(ballNumber.getText());
					if(numberOfPlayers<=4 && numberOfPlayers>0){
					System.out.println("Playing with " + numberOfPlayers + " players and " + numberOfBalls + " balls.");
					selectedGameStart = new Controller(numberOfPlayers, numberOfBalls);}
					else footerError.set("Invalid number of players.");
				}
			});
			
			
			Scene scene = new Scene(root, 600, 300);
			//primaryStage.initStyle(StageStyl+e.UNDECORATED);
			primaryStage.setTitle("PingPong");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
		    primaryStage.show();
		}  

	

	
}

