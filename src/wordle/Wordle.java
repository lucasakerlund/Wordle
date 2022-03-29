package wordle;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Wordle extends Application {

    public static final int AMOUNT_OF_LETTERS = 5;
    public static final int AMOUNT_OF_GUESSES = 6;

    public static Wordle instance;

    private Scene scene;

    private Stage stage;
    private BorderPane root;
    private DataHandler dataHandler;
    private GuessBox guessBox;
    private Keyboard keyboard;

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        this.stage = stage;
        this.dataHandler = new DataHandler();
        root = new BorderPane();
        scene = new Scene(root, GuessBox.BOARD_TILE_SIZE*AMOUNT_OF_LETTERS+400, GuessBox.BOARD_TILE_SIZE*AMOUNT_OF_GUESSES+400);
        root.setBackground(new Background(new BackgroundFill(Color.web("#121213"), null, null)));
        setupGameBoard();
        stage.setScene(scene);
        stage.show();
    }

    public static Wordle inst(){
        return instance;
    }

    public void setupGameBoard(){
        VBox box = new VBox(50);
        box.setAlignment(Pos.CENTER);
        guessBox = new GuessBox(AMOUNT_OF_LETTERS, AMOUNT_OF_GUESSES, dataHandler.getRandomWord());
        keyboard = new Keyboard();

        Text headerText = new Text("ORDLE");
        headerText.setFill(Color.GREEN);
        headerText.setFont(Font.font(null, FontWeight.BOLD, null, 50));

        box.getChildren().add(headerText);
        box.getChildren().add(guessBox);
        box.getChildren().add(keyboard);
        root.setCenter(box);
    }

    public Scene getScene(){
        return scene;
    }

    public Stage getStage(){
        return stage;
    }

    public DataHandler getDataHandler(){
        return dataHandler;
    }

    public GuessBox getGuessBox(){
        return guessBox;
    }

    public Keyboard getKeyboard(){
        return keyboard;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
    }
}
