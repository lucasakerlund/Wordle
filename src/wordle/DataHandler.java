package wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DataHandler {

    private final String wordFile = "src/wordle/words.txt";

    private List<String> words;

    public DataHandler(){
        this.words = new ArrayList<>();
        initWords();
    }

    private void initWords(){
        try {
            Scanner input = new Scanner(new File(wordFile));
            while(input.hasNextLine()){
                String word = input.nextLine();
                words.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> getWords(){
        return words;
    }

    public String getRandomWord(){
        Random r = new Random();

        return words.get(r.nextInt(words.size()));
    }

}
