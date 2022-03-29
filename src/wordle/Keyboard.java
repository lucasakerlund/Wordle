package wordle;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class Keyboard extends VBox {

    private final String[][] layout = {
            {"Q","W","E","R","T","Y","U","I","O","P","Å"},
            {"A","S","D","F","G","H","J","K","L","Ö","Ä"},
            {"ENTER","Z","X","C","V","B","N","M","ERASE"}
    };

    private HashMap<String, Tile> tiles;

    public Keyboard(){
        this.tiles = new HashMap<>();
        setSpacing(5);
        setAlignment(Pos.CENTER);
        generateLayout();
    }

    private void generateLayout(){
        for(int row = 0; row < layout.length; row++){
            HBox box = new HBox(5);
            box.setAlignment(Pos.CENTER);
            for(int column = 0; column < layout[row].length; column++){
                String text = layout[row][column];
                if(text.equalsIgnoreCase("enter")){
                    Tile tile = new Tile(row, column, 80, 40);
                    tile.setText(text);
                    tile.setTextFont(20);
                    tile.setOnMousePressed(e -> Wordle.inst().getGuessBox().guess());
                    box.getChildren().add(tile);
                    tiles.put(text, tile);
                }else if(layout[row][column].equalsIgnoreCase("erase")){
                    Tile tile = new Tile(row, column, 80, 40);
                    tile.setText(text);
                    tile.setTextFont(20);
                    tile.setOnMousePressed(e -> Wordle.inst().getGuessBox().erase());
                    box.getChildren().add(tile);
                    tiles.put(text, tile);
                }else{
                    Tile tile = new Tile(row, column, 40, 40);
                    tile.setText(text);
                    tile.setTextFont(20);
                    tile.setOnMousePressed(e -> Wordle.inst().getGuessBox().addLetter(text));
                    box.getChildren().add(tile);
                    tiles.put(text, tile);
                }
            }
            getChildren().add(box);
        }
    }

    public HashMap<String, Tile> getTiles(){
        return tiles;
    }

}
