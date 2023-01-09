package Main;

import Containers.BoardRepresentation;
import CustomExceptions.GenerationTimeExceeded;
import Entities.CrosswordDictionary;
import Entities.Crossword;
import Enums.ReprezentationType;
import UI.AlertBox;
import UI.CrosswordWindow;
import UI.PrintCrosswordBox;
import Utils.Loader;
import Utils.Serializer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Crossy extends Application {
    Crossword crossword=null;
    CrosswordDictionary dictionary = null;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Crossy");
        dictionary = new CrosswordDictionary();
        dictionary.load("example_data.txt");

        TextField boardSizeTextField = new TextField();
        boardSizeTextField.setPromptText("board size");

        TextField expectedCluesTextBox = new TextField();
        expectedCluesTextBox.setPromptText("expected clues");

        Button dictionaryButton = new Button("Import dictionary");
        Button generateButton = new Button("Generate crossword");
        Button saveButton = new Button("Save crossword");
        Button loadButton = new Button("Load crossword");
        Button checkButton = new Button("Check crossword");
        Button consolePrintButton = new Button("Console print options");
        Button betaPrintButton = new Button("Beta print");

        dictionaryButton.setOnAction(e-> dictionary = Loader.loadDictionary());

        generateButton.setOnAction(e-> {
            crossword = new Crossword(Integer.parseInt(boardSizeTextField.getText()));
            try {
                crossword.generateFromDictionary(dictionary, Integer.parseInt(expectedCluesTextBox.getText()));
            }
            catch (GenerationTimeExceeded exception){
                AlertBox.display("ERROR","Crossword max generation time exceeded \n try decreasing number of words/increasing board or dictionary size");
            }
        });
        saveButton.setOnAction(e->Loader.saveCrossword(crossword));
        loadButton.setOnAction(e->crossword = Loader.loadCrossword());
        checkButton.setOnAction(e->{
            BoardRepresentation solution = Loader.loadSolutionFromFile();
            if(crossword.isValidSolution(solution))
                AlertBox.display("Solution","Valid solution");
            else
                AlertBox.display("Solution","Invalid solution");
        });
        consolePrintButton.setOnAction(e->PrintCrosswordBox.display(crossword));
        betaPrintButton.setOnAction(e-> CrosswordWindow.displaySolvable(crossword,ReprezentationType.UNSOLVED));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(boardSizeTextField,expectedCluesTextBox,dictionaryButton,generateButton,saveButton,loadButton,checkButton,consolePrintButton,betaPrintButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,600,500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
