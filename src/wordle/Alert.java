package wordle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Alert {

    private final static Alert instance = new Alert();

    private final VBox box;
    private final Stage stage;

    private Alert(){
        box = new VBox(5);
        stage = new Stage(StageStyle.TRANSPARENT);
        stage.initModality(Modality.NONE);
        stage.initOwner(Wordle.inst().getStage());
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 600);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        root.setCenter(box);
        root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        box.setAlignment(Pos.CENTER);
        //Initiating the coordinates of the scene and calculating the start position.
        stage.show();
        calculatePos();
        //
        Wordle.inst().getStage().xProperty().addListener((observable, oldValue, newValue) -> {
            calculatePos();
        });
        Wordle.inst().getStage().yProperty().addListener((observable, oldValue, newValue) -> {
            calculatePos();
        });
        Wordle.inst().getStage().widthProperty().addListener((observable, oldValue, newValue) -> {
            calculatePos();
        });
        Wordle.inst().getStage().heightProperty().addListener((observable, oldValue, newValue) -> {
            calculatePos();
        });
    }

    public static Alert inst(){
        return instance;
    }

    private void calculatePos(){
        stage.setX(Wordle.inst().getStage().getX() + (Wordle.inst().getStage().getWidth()/2 - stage.getWidth()/2));
        stage.setY(Wordle.inst().getStage().getY() + (Wordle.inst().getStage().getHeight()/2 - stage.getHeight()/2));
    }

    public void alert(String text){
        Label label = new Label(text);
        label.setFont(new Font(20));
        label.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(7), null)));
        label.setPadding(new Insets(10));
        label.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(7), BorderStroke.MEDIUM)));
        box.getChildren().add(label);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), new KeyValue(label.opacityProperty(), 1)),
                new KeyFrame(Duration.millis(1000), new KeyValue(label.opacityProperty(), 1)),
                new KeyFrame(Duration.millis(1300), new KeyValue(label.opacityProperty(), 0))
                );
        timeline.playFromStart();
        stage.show();
        Wordle.inst().getStage().requestFocus();
        timeline.setOnFinished(e -> {
            box.getChildren().remove(label);
            if(box.getChildren().isEmpty()){
                stage.hide();
            }
        });
    }

    public void end(String word, String text, Color color){
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(7), null)));
        vbox.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(7), BorderStroke.MEDIUM)));
        Label result = new Label(text);
        result.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 20));
        result.setTextFill(color);
        Label wordLabel = new Label("Ordet är: ");
        wordLabel.setFont(new Font(20));
        Text wordText = new Text(word.toUpperCase());
        wordText.setFont(new Font(20));
        wordText.setFill(color);
        TextFlow textFlow = new TextFlow(wordLabel, wordText);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        Label continueLabel = new Label("Tryck på en tangent för att fortsätta");
        continueLabel.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 20));
        continueLabel.setWrapText(true);
        continueLabel.setTextAlignment(TextAlignment.CENTER);

        vbox.getChildren().add(result);
        vbox.getChildren().add(textFlow);
        vbox.getChildren().add(continueLabel);
        this.box.getChildren().add(vbox);
        stage.show();
        EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                box.getChildren().remove(vbox);
                stage.hide();
                Wordle.inst().setupGameBoard();
                stage.hide();
                stage.removeEventFilter(KeyEvent.KEY_PRESSED, this);
            }
        };
        stage.addEventFilter(KeyEvent.KEY_PRESSED, handler);
    }
}
