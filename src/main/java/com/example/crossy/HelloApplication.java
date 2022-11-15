package com.example.crossy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import Entities.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        CrosswordDictionary dictionary = new CrosswordDictionary();
        dictionary.load("src/main/resources/example_data.txt");
        for(var x:dictionary.getWordEntityList())
            System.out.println(x.getWord() + "   " + x.getDefinition());
        CrosswordEntity crossword = new CrosswordEntity(10);
        crossword.construct(dictionary,6);
        crossword.printSolvedBoard();
        System.out.println();
        crossword.printUnsolvedBoard();
        System.out.println();
        crossword.printCrossword();
    }

    public static void main(String[] args) {
        launch();
    }
}