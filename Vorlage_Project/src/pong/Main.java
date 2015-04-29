package pong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
	static Group root;
	
	public void start(Stage stage) throws Exception{
		root = FXMLLoader.load(getClass().getResource("Pong.fxml"));
		
		stage.setTitle("Pong Spiel 2.0");
		stage.setScene(new Scene(root, 1000, 770));
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setResizable(false);
		stage.show();
		root.requestFocus();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
