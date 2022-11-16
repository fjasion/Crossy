package Utils;

import Entities.ClueEntity;
import Entities.Crossword;

import java.io.*;

public class Serializer {
    public static void saveCrosssword(Crossword crossword,String filename) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename+".crossy"));
        objectOutputStream.writeObject(crossword);
    }
    public static Crossword loadCrossword(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename+".crossy"));
        Crossword crossword = (Crossword) objectInputStream.readObject();
        return crossword;
    }

}
