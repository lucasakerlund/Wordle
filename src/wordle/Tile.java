package wordle;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Tile extends StackPane {

    enum TileState{
        CORRECT, ALMOST, INCORRECT
    }

    private int x;
    private int y;

    private TileState state;

    private Rectangle rec;
    private Text text;

    public Tile(int x, int y, int tileWidth, int tileHeight){
        this.x = x;
        this.y = y;

        rec = new Rectangle(tileWidth, tileHeight, Color.RED);
        text = new Text("");
        text.setFont(Font.font(null, FontWeight.BOLD, 40));
        text.setFill(Color.WHITE);

        getChildren().addAll(rec, text);
        draw(Color.web("#121213"), Color.GRAY);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public TileState getState(){
        return state;
    }

    public void setState(TileState state){
        this.state = state;
        switch(state){
            case CORRECT:
                draw(Color.GREEN, Color.GREEN);
                break;
            case ALMOST:
                draw(Color.ORANGE, Color.ORANGE);
                break;
            case INCORRECT:
                draw(Color.GRAY, Color.GRAY);
                break;
        }
    }

    public String getText(){
        return text.getText();
    }

    public void setText(String text){
        this.text.setText(text);
    }

    public void setTextFont(int size){
        text.setFont(new Font(size));
    }

    private void draw(Color fill, Color stroke){
        rec.setFill(fill);
        rec.setStroke(stroke);
        rec.setStrokeWidth(1);
        rec.setStrokeType(StrokeType.CENTERED);
    }

}
