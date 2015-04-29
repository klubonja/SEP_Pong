package vereinigung;

import vereinigung.Controller;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	final Image gestreifteRemulanerHintergrund1 = new Image(
			Main.class.getResourceAsStream("Hintergrund1.png"));

	SimpleStringProperty footerError = new SimpleStringProperty();
	boolean players;

	Controller selectedGameStart;

	public void start(Stage stage) throws Exception {
		ImageView background1 = new ImageView();
		background1.setImage(gestreifteRemulanerHintergrund1);

		Group root = new Group();
		Scene scene = new Scene(root, 1000, 770);
		GridPane gp = new GridPane();
		gp.getRowConstraints().add(new RowConstraints(310));
		gp.getRowConstraints().add(new RowConstraints(50));
		gp.getRowConstraints().add(new RowConstraints(50));
		gp.getRowConstraints().add(new RowConstraints(50));
		gp.getRowConstraints().add(new RowConstraints(310));
		gp.setMinHeight(770);
		gp.setMinWidth(1000);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(10.0);
		dropShadow.setBlurType(BlurType.THREE_PASS_BOX);

		// ueberschrift
		Label title = new Label("Ping-Pong");
		title.setEffect(new Glow());
		title.setStyle("-fx-font-size: 90");
		// untertitel
		Label author = new Label("Gestreifte Remulaner ©2015");
		author.setEffect(new Glow());
		author.setStyle("-fx-font-size: 45");
		// label fuer die auswahl von spielern
		Label selectPlayer = new Label("Select number of PLAYERS");
		selectPlayer.setEffect(dropShadow);
		selectPlayer.setStyle("-fx-font-size: 25");
		// label fuer die auswahl von baellen
		Label selectBalls = new Label("Select number of BALLS");
		selectBalls.setEffect(dropShadow);
		selectBalls.setStyle("-fx-font-size: 25");
		// hbox fuer die radio buttons fuer die anzahl der spieler
		HBox playerNumber = new HBox(50);
		// erster radio button
		RadioButton playerNumber1 = new RadioButton();
		playerNumber1.setText("1 Player");
		playerNumber1.setStyle("-fx-font-size: 30");
		playerNumber1.setEffect(dropShadow);
		// zweiter radio button
		RadioButton playerNumber2 = new RadioButton();
		playerNumber2.setText("2 Player");
		playerNumber2.setStyle("-fx-font-size: 30");
		playerNumber2.setEffect(dropShadow);
		playerNumber.getChildren().addAll(playerNumber1, playerNumber2);
		if (playerNumber1.isSelected()) {
			playerNumber2.setSelected(false);
		}
		if (playerNumber2.isSelected()) {
			playerNumber1.setSelected(false);
		}
		// text feld zur eingabe der anzahl an baelle
		TextField ballNumber = new TextField();
		ballNumber.setEffect(dropShadow);
		ballNumber.setPromptText("e.g. 5");
		ballNumber.setPrefWidth(30);
		// button play
		Button startGame = new Button("Play");
		startGame.setEffect(dropShadow);
		startGame.setStyle("-fx-font-size: 30");
		startGame.setMinSize(150, 150);
		// button quit
		Button quitGame = new Button("Quit");
		quitGame.setEffect(dropShadow);
		quitGame.setStyle("-fx-font-size: 30");
		quitGame.setMinSize(150, 150);
		// fehler meldung
		Label error = new Label("");
		error.textProperty().bind(footerError);

		GridPane.setConstraints(title, 2, 0);
		GridPane.setConstraints(author, 2, 1);
		GridPane.setConstraints(selectPlayer, 0, 2);
		gp.setColumnSpan(selectPlayer, 2);
		GridPane.setConstraints(playerNumber, 2, 2);
		gp.setColumnSpan(playerNumber, 2);
		GridPane.setConstraints(selectBalls, 0, 3);
		gp.setColumnSpan(selectBalls, 2);
		GridPane.setConstraints(ballNumber, 2, 3);
		gp.setColumnSpan(ballNumber, 2);
		GridPane.setConstraints(startGame, 1, 4);
		GridPane.setConstraints(quitGame, 4, 4);
		GridPane.setConstraints(error, 5, 4);

		startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (playerNumber1.isSelected() && !playerNumber2.isSelected()) {
					players = true;
				} else if (playerNumber1.isSelected()
						&& playerNumber2.isSelected()) {
					footerError.set("Please select only one option.");
				} else if (!playerNumber1.isSelected()
						&& !playerNumber2.isSelected()) {
					footerError.set("Please select only one option.");
				} else if (!playerNumber1.isSelected()
						&& playerNumber2.isSelected()) {
					players = false;
				}
				int numberOfBalls = Integer.parseInt(ballNumber.getText());
				if (numberOfBalls > 0) {
					selectedGameStart = new Controller(players, numberOfBalls);
					stage.close();
				}
			}
		});

		/*
		 * root.getChildren().add(background2); root.getChildren().add(player1);
		 * root.getChildren().add(player2);
		 */
		gp.getChildren().addAll(background1, title, author, selectPlayer,
				playerNumber, selectBalls, ballNumber, startGame, quitGame,
				error);
		root.getChildren().addAll(background1, gp);
		stage.setTitle("Pong");
		stage.setScene(scene);
		// stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
