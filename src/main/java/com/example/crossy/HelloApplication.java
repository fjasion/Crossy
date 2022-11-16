package com.example.crossy;

import Utils.Loader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import Entities.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        CrosswordDictionary dictionary = new CrosswordDictionary();
        dictionary.load("src/main/resources/example_data.txt");
        for(var x:dictionary.getWordEntityList())
            System.out.println(x.getWord() + "   " + x.getDefinition());
        Crossword crossword = new Crossword(10);
        crossword.generateFromDictionary(dictionary,6);
        //crossword.printSolvedBoard();
        System.out.println();
        //crossword.printUnsolvedBoard();
        System.out.println();
        crossword.printCrossword();

        var board = Loader.loadSolutionMapFromFile("src/main/resources/example_solution.txt");
        int boardSize=10;
        for(int i = 0; i< boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board.containsKey(i * boardSize + j))
                    System.out.print(board.get(i * boardSize + j));
                else
                    System.out.print('#');
            }
            System.out.println();
        }
        //System.out.println(crossword.isValidSolution(board));*/
    }

    //public static void main(String[] args) {
    //    launch();
    //}
}