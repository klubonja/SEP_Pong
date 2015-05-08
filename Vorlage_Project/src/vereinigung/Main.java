package vereinigung;

import vereinigung.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	

	SimpleStringProperty footerError = new SimpleStringProperty("");
	boolean players;
	Controller newGame;

	public void start(Stage stage) throws Exception {

		GridPane gp = new GridPane();
		Scene scene = new Scene(gp, 1000, 650);
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(20);
		gp.setHgap(35);
		gp.setPadding(new Insets(20));
		gp.getRowConstraints().add(new RowConstraints(150));
		gp.getRowConstraints().add(new RowConstraints(50));
		gp.getRowConstraints().add(new RowConstraints(50));
		gp.getRowConstraints().add(new RowConstraints(50));
		gp.getRowConstraints().add(new RowConstraints(150));
		gp.getRowConstraints().add(new RowConstraints(50));
		gp.setMinHeight(600);
		gp.setMinWidth(1000);
		gp.setStyle("-fx-background-color: radial-gradient(radius 100%, lightgreen, lightseagreen)");

		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(1.0);
		dropShadow.setBlurType(BlurType.GAUSSIAN);
		
		DropShadow buttonShadow = new DropShadow();
		buttonShadow.setRadius(13);
	
		// ueberschrift
		Label title = new Label("Ping-Pong");
		title.setEffect(new Glow(0.7));
		title.setStyle("-fx-font-size: 80");
		
		// untertitel
		Label author = new Label("Gestreifte Remulaner 2015");
		author.setEffect(new Glow(0.5));
		author.setStyle("-fx-font-size: 38");
		// label fuer die auswahl von spielern
		
		Label selectPlayer = new Label("Select number of PLAYERS");
		selectPlayer.setEffect(dropShadow);
		selectPlayer.setStyle("-fx-font-size: 23");
		// label fuer die auswahl von baellen
		
		Label selectBalls = new Label("Select number of BALLS");
		selectBalls.setEffect(dropShadow);
		selectBalls.setStyle("-fx-font-size: 23");
		
		// hbox fuer die radio buttons fuer die anzahl der spieler
		HBox playerNumber = new HBox(50);
		ToggleGroup group = new ToggleGroup();
		
		// erster radio button
		RadioButton playerNumber1 = new RadioButton();
		playerNumber1.setText("1 Player");
		playerNumber1.setStyle("-fx-font-size: 30");
		playerNumber1.setEffect(dropShadow);
		playerNumber1.setToggleGroup(group);
		
		// zweiter radio button
		RadioButton playerNumber2 = new RadioButton();
		playerNumber2.setText("2 Players");
		playerNumber2.setStyle("-fx-font-size: 30");
		playerNumber2.setEffect(dropShadow);
		playerNumber2.setToggleGroup(group);
		playerNumber.getChildren().addAll(playerNumber1, playerNumber2);

		// text feld zur eingabe der anzahl an baelle
		TextField ballNumber = new TextField();
		ballNumber.setEffect(buttonShadow);
		ballNumber.setPromptText("e.g. 5");
		ballNumber.setPrefWidth(50);
		
		// button play
		Button startGame = new Button("Play");
		startGame.setEffect(dropShadow);
		
		startGame.setMinSize(150, 150);
		startGame.setTextFill(Color.WHITE);
		startGame.setStyle("-fx-font-size: 30 ; -fx-background-color: radial-gradient(radius 100%, lightskyblue, cornflowerblue)");
		startGame.addEventHandler(MouseEvent.MOUSE_ENTERED, 
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            startGame.setEffect(buttonShadow);
			        }
			});
			//Removing the shadow when the mouse cursor is off
			startGame.addEventHandler(MouseEvent.MOUSE_EXITED, 
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            startGame.setEffect(null);
			        }
			});
		
		// button quit
		Button quitGame = new Button("Quit");
		quitGame.setTextFill(Color.WHITE);
		quitGame.setStyle("-fx-font-size: 30 ; -fx-background-color: radial-gradient(radius 100%, coral, crimson)");
		quitGame.setMinSize(150, 150);
		quitGame.addEventHandler(MouseEvent.MOUSE_ENTERED, 
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            quitGame.setEffect(buttonShadow);
			        }
			});
			//Removing the shadow when the mouse cursor is off
			quitGame.addEventHandler(MouseEvent.MOUSE_EXITED, 
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            quitGame.setEffect(null);
			        }
			});
		quitGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Platform.exit();
			}
		});
		// fehlermeldung
		Label error = new Label("");
		error.setStyle("-fx-font-size: 25");
		error.setTextFill(Color.RED);
		error.textProperty().bind(footerError);

		GridPane.setConstraints(title, 0, 0);
		GridPane.setColumnSpan(title, 3);
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setValignment(title, VPos.BOTTOM);
		GridPane.setConstraints(author, 0, 1);
		GridPane.setColumnSpan(author, 3);
		GridPane.setHalignment(author, HPos.CENTER);
		GridPane.setValignment(author, VPos.TOP);
		GridPane.setConstraints(selectPlayer, 0, 2);
		GridPane.setConstraints(playerNumber, 1, 2);
		GridPane.setConstraints(selectBalls, 0, 3);
		GridPane.setConstraints(ballNumber, 1, 3);
		GridPane.setConstraints(startGame, 0, 4);
		GridPane.setConstraints(quitGame, 1, 4);
		GridPane.setHalignment(quitGame, HPos.RIGHT);
		GridPane.setConstraints(error, 0, 5);
		GridPane.setColumnSpan(error, 3);
		GridPane.setHalignment(error, HPos.CENTER);
		gp.getChildren().addAll(title, author, selectPlayer,
				playerNumber, selectBalls, ballNumber, startGame, quitGame,
				error);

		startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (playerNumber1.isSelected()) {
					players = true;
					
					int numberOfBalls;
					try {
						numberOfBalls = Integer.parseInt(ballNumber.getText());
						if (numberOfBalls > 0){
							newGame = new Controller(players,
									numberOfBalls);
							stage.close();
						}
					}catch (IllegalArgumentException e1) {
						footerError.setValue("Please select the number of balls!");
					}
					
				}else if (playerNumber2.isSelected()) {
					players = false;
					
					int numberOfBalls;
					try {
						numberOfBalls = Integer.parseInt(ballNumber.getText());
						if (numberOfBalls > 0){
							newGame = new Controller(players,
									numberOfBalls);
							stage.close();
						}
					}catch (IllegalArgumentException e1) {
						footerError.setValue("Please select the number of balls!");
					}
					
				}else if (!playerNumber1.isSelected()
						&& !playerNumber2.isSelected()) {
					footerError.set("Please select a playing mode!");
				}
			}
		});
		
		stage.setResizable(false);
		stage.setTitle("Pong");
		stage.setScene(scene);
		//stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
