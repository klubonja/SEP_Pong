package vereinigung;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Die Klasse View1 repraesentiert das Intro des Spiels, wo man die Anzahl an
 * Spieler und Baelle auswaehlen kann.
 * 
 * @author GestreifteRemulaner
 *
 */
public class View1 extends Stage {
	public Stage stage;
	public DropShadow dropShadow;
	public DropShadow buttonShadow;
	public GridPane gp;
	public Scene scene;
	public Label title;
	public Label author;
	public Label selectPlayer;
	public HBox playerNumber;
	public ToggleGroup group;
	public RadioButton playerNumber1;
	public RadioButton playerNumber2;
	public Label selectBalls;
	public TextField ballNumber;
	public Button startGame;
	public Button quitGame;
	public Label error;

	public SimpleStringProperty footerError = new SimpleStringProperty("");
	public boolean players;

	/**
	 * Construcotr fuer das Intro (1 View).
	 */
	public View1() {
		gp = new GridPane();
		scene = new Scene(gp, 1000, 650);
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

		dropShadow = new DropShadow();
		dropShadow.setRadius(1.0);
		dropShadow.setBlurType(BlurType.GAUSSIAN);

		buttonShadow = new DropShadow();
		buttonShadow.setRadius(13);

		// Ueberschrift - Name des Spiels
		title = new Label("Ping-Pong");
		title.setEffect(new Glow(0.7));
		title.setStyle("-fx-font-size: 80");

		// Untertitel - Teamname
		author = new Label("Gestreifte Remulaner 2015");
		author.setEffect(new Glow(0.5));
		author.setStyle("-fx-font-size: 38");

		// Label fuer die Radio-Buttons
		selectPlayer = new Label("Select number of PLAYERS");
		selectPlayer.setEffect(dropShadow);
		selectPlayer.setStyle("-fx-font-size: 23");

		// LayoutManager fuer die Radio-Buttons
		playerNumber = new HBox(50);
		group = new ToggleGroup();

		// Radio-Button 1-Spieler Modus
		playerNumber1 = new RadioButton();
		playerNumber1.setText("1 Player");
		playerNumber1.setStyle("-fx-font-size: 30");
		playerNumber1.setEffect(dropShadow);
		playerNumber1.setToggleGroup(group);

		// Radio-Button 2-Spieler Modus
		playerNumber2 = new RadioButton();
		playerNumber2.setText("2 Players");
		playerNumber2.setStyle("-fx-font-size: 30");
		playerNumber2.setEffect(dropShadow);
		playerNumber2.setToggleGroup(group);
		playerNumber.getChildren().addAll(playerNumber1, playerNumber2);

		// Label fuer das Textfeld
		selectBalls = new Label("Select number of BALLS");
		selectBalls.setEffect(dropShadow);
		selectBalls.setStyle("-fx-font-size: 23");

		// Textfeld, in das man die Anzahl an Baelle eingibt
		ballNumber = new TextField();
		ballNumber.setEffect(buttonShadow);
		ballNumber.setPromptText("e.g. 5");
		ballNumber.setPrefWidth(50);

		// Button "Play"
		startGame = new Button("Play");
		startGame.setMinSize(150, 150);
		startGame.setTextFill(Color.BLACK);
		startGame.setEffect(buttonShadow);
		startGame
				.setStyle("-fx-font-size: 30 ;"
						+ "-fx-background-color: radial-gradient(radius 100%, cornflowerblue, deepskyblue);"
						+ "-fx-background-radius: 20");

		// Button "Quit"
		quitGame = new Button("Quit");
		quitGame.setMinSize(150, 150);
		quitGame.setTextFill(Color.BLACK);
		quitGame.setEffect(buttonShadow);
		quitGame.setStyle("-fx-font-size: 30 ; -fx-background-color: radial-gradient(radius 100%, darksalmon, deeppink);"
				+ "-fx-background-radius: 20");

		// Fehlermeldung bei inkorrekter Eingabe/fehlender Auswahl
		error = new Label("");
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
		gp.getChildren().addAll(title, author, selectPlayer, playerNumber,
				selectBalls, ballNumber, startGame, quitGame, error);

		this.setResizable(false);
		this.setTitle("Pong");
		this.setScene(scene);
		// this.stage.initStyle(StageStyle.UNDECORATED);
		this.show();
	}
}
