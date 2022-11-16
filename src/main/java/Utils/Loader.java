package Utils;

import Containers.BoardRepresentation;
import Entities.DictionaryEntity;
import Enums.ReprezentationType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Loader {


    public static BoardRepresentation loadSolutionMapFromFile(String path){
        BoardRepresentation board = null;
        int cntr=0;
        try {
            File file = new File(path);
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
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return board;
    }
}
