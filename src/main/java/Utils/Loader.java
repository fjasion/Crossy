package Utils;

import Containers.BoardRepresentation;
import Entities.Crossword;
import Entities.CrosswordDictionary;
import Entities.DictionaryEntity;
import Enums.ReprezentationType;
import Main.CONFIG;
import UI.AlertBox;
import UI.PathSelectionWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Loader {

    public static void saveCrossword(Crossword crossword){
        try {
            String filename = PathSelectionWindow.getPath("Save Crossword","Save as:","filename");
            Serializer.saveCrosssword(crossword,filename + CONFIG.CROSSWORD_FILE_EXTENSION);
            System.err.println("Successfully saved");
        } catch (IOException exception) {
            AlertBox.display("ERROR","Unable to save crossroad: " + exception.getMessage());
        }
    }

    public static Crossword loadCrossword(){
        Crossword crossword=null;
        try {
            String filename = PathSelectionWindow.getPath("Load Crossword","Load file:","filename");
            crossword = Serializer.loadCrossword(filename + CONFIG.CROSSWORD_FILE_EXTENSION);
            System.err.println("Maybe successfully loaded");
        } catch (Exception exception) {
            AlertBox.display("ERROR","Unable to load crossroad: " + exception.getMessage());
        }
        return crossword;
    }

    public static CrosswordDictionary loadDictionary(){
        CrosswordDictionary dictionary = new CrosswordDictionary();
        try {
            String filename = PathSelectionWindow.getPath("Load Dictionary","Load file:","filename");
            dictionary.load(filename + ".txt");
            System.err.println("Maybe successfully loaded");
        } catch (Exception exception) {
            AlertBox.display("ERROR","Unable to load dictionary: " + exception.getMessage());
            return null;
        }
        return dictionary;
    }
    public static BoardRepresentation loadSolutionFromFile(){
        BoardRepresentation board = null;
        int cntr=0;
        String filename = PathSelectionWindow.getPath("Load Solution","Load file:","filename");
        try {
            File file = new File(filename+".txt");
            Scanner scanner = new Scanner(file);
            board = new BoardRepresentation(Integer.parseInt(scanner.nextLine(),10), ReprezentationType.SOLVED);
            while(scanner.hasNextLine())
            {
                for(char c:scanner.nextLine().toCharArray()){
                    if(c != '#')
                        board.insert(cntr,c);
                    cntr++;
                }
            }
        }
        catch (FileNotFoundException exception){
            AlertBox.display("ERROR","Unable to load solution: " + exception.getMessage());
        }
        return board;
    }
}
