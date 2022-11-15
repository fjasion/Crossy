package Utils;

import Entities.DictionaryEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Loader {


    public static HashMap<Integer,Character> loadSolutionMapFromFile(String path){
        HashMap<Integer,Character> map = new HashMap<>();
        int cntr=0;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                for(char c:scanner.nextLine().toCharArray()){
                    if(c != '#')
                        map.put(cntr,c);
                    cntr++;
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return map;
    }
}
