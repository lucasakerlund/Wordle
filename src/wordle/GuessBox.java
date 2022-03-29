package wordle;

import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GuessBox extends VBox {

    public static final int BOARD_TILE_SIZE = 70;

    private int amountOfLetters;
    private int amountOfGuesses;

    private String word;

    private Tile[][] grid;
    private int tileSpacing = 5;

    private Tile targetedTile;
    private int currentRow = 0;

    private boolean canType = true;

    public GuessBox(int amountOfLetters, int amountOfGuesses, String word){
        grid = new Tile[amountOfGuesses][amountOfLetters];
        this.amountOfLetters = amountOfLetters;
        this.amountOfGuesses = amountOfGuesses;
        this.word = word;
        setAlignment(Pos.CENTER);

        setSpacing(tileSpacing);
        generateGrid();
        addKeyListener();
    }

    private void generateGrid(){
        for(int row = 0; row < amountOfGuesses; row++){
            HBox box = new HBox(tileSpacing);
            box.setAlignment(Pos.CENTER);
            for(int column = 0; column < amountOfLetters; column++){
                Tile tile = new Tile(row, column, BOARD_TILE_SIZE, BOARD_TILE_SIZE);
                box.getChildren().add(tile);
                grid[row][column] = tile;
            }
            getChildren().add(box);
        }
        targetedTile = grid[0][0];
    }

    public void guess(){
        if(grid[currentRow][targetedTile.getY()].getText().equals("")){
            Alert.inst().alert("Too few letters");
            return;
        }
        String guess = "";
        for(int column = 0; column < amountOfLetters; column++){
            guess+=grid[currentRow][column].getText();
        }
        if(!Wordle.inst().getDataHandler().getWords().contains(guess.toLowerCase())){
            Alert.inst().alert("Not a valid word");
            return;
        }
        StringBuilder remainings = new StringBuilder(word.toUpperCase());
        //check greens
        for(int column = 0; column < amountOfLetters; column++){
            Tile tile = grid[currentRow][column];
            if(tile.getText().toUpperCase().charAt(0) == word.toUpperCase().charAt(column)){
                tile.setState(Tile.TileState.CORRECT);
                remainings.setCharAt(column, '0');
            }
        }
        //
        //check orange and gray
        for(int column = 0; column < amountOfLetters; column++){
            Tile tile = grid[currentRow][column];
            if(tile.getState() == Tile.TileState.CORRECT){
                continue;
            }
            int indexOfAlmostChar = remainings.indexOf(tile.getText());
            if(indexOfAlmostChar == -1){
                tile.setState(Tile.TileState.INCORRECT);
            }else {
                tile.setState(Tile.TileState.ALMOST);
                remainings.deleteCharAt(indexOfAlmostChar);
            }
        }
        //
        //Update Keyboard
        for(int column = 0; column < amountOfLetters; column++){
            String letter = grid[currentRow][column].getText();
            if(letter.toUpperCase().charAt(0) == word.toUpperCase().charAt(column)){
                Wordle.inst().getKeyboard().getTiles().get(letter).setState(Tile.TileState.CORRECT);
            }else{
                if(Wordle.inst().getKeyboard().getTiles().get(letter).getState() == Tile.TileState.CORRECT){
                    continue;
                }
                if(word.toUpperCase().indexOf(letter.toUpperCase()) != -1){
                    Wordle.inst().getKeyboard().getTiles().get(letter).setState(Tile.TileState.ALMOST);
                }else {
                    Wordle.inst().getKeyboard().getTiles().get(letter).setState(Tile.TileState.INCORRECT);
                }
            }
        }
        if(guess.equalsIgnoreCase(word)){
            Alert.inst().end(word, "Du vann!", Color.GREEN);
            canType = false;
            return;
        }
        //
        if(currentRow < amountOfGuesses-1){
            currentRow++;
            targetedTile = grid[currentRow][0];
        }else {
            Alert.inst().end(word, "Du förlora!", Color.RED);
            canType = false;
        }
    }

    public String getRow(int row){
        return "";
    }

    public void addLetter(String letter){
        if (!targetedTile.getText().equals("")) {
            return;
        }
        targetedTile.setText(letter.toUpperCase());
        if (targetedTile.getY() < amountOfLetters - 1) {
            targetedTile = grid[targetedTile.getX()][targetedTile.getY()+1];
        }
    }

    public void erase(){
        if (targetedTile.getY() > 0) {
            if (targetedTile.getText().equals("")) {
                targetedTile = grid[targetedTile.getX()][targetedTile.getY()-1];
            }
            targetedTile.setText("");
        }
    }

    public void addKeyListener() {
        Wordle.inst().getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if(!canType){
                return;
            }
            if(e.getText().matches("[a-zA-ZåäöÅÄÖ]+")){
                addLetter(e.getText());
            }
            if (e.getCode() == KeyCode.BACK_SPACE) {
                erase();
            }
            if(e.getCode() == KeyCode.ENTER){
                guess();
            }
        });
    }
}
