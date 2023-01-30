package Main;
import Entities.CrosswordDictionary;
import Entities.Crossword;
import Enums.ReprezentationType;
import UI.AlertBox;
import UI.CrosswordWindow;
import UI.GenerateCrosswordBox;
import UI.PrintCrosswordBox;
import Utils.Loader;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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

        Label gameTitle = new Label("CROSSY");
        gameTitle.setFont(new Font(40));

        Button dictionaryButton = new Button("Import dictionary");
        Button generateButton = new Button("Generate crossword");
        Button saveButton = new Button("Save crossword");
        Button loadButton = new Button("Load crossword");
        //Button checkButton = new Button("Check crossword");
        Button consolePrintButton = new Button("Console print options");
        Button solveButton = new Button("Solve crossword");

        dictionaryButton.setOnAction(e-> {
            CrosswordDictionary dict = Loader.loadDictionary();
            if(dict != null)
                dictionary = dict;
        });

        generateButton.setOnAction(e-> {
            Crossword c = GenerateCrosswordBox.generate(dictionary);
            if(c != null)
                crossword = c;
        });

        saveButton.setOnAction(e->Loader.saveCrossword(crossword));
        loadButton.setOnAction(e->crossword = Loader.loadCrossword());
        /*checkButton.setOnAction(e->{
            BoardRepresentation solution = Loader.loadSolutionFromFile();
            if(crossword.isValidSolution(solution))
                AlertBox.display("Solution","Valid solution");
            else
                AlertBox.display("Solution","Invalid solution");
        });*/
        consolePrintButton.setOnAction(e->PrintCrosswordBox.display(crossword));
        solveButton.setOnAction(e->{
            if(crossword == null)
                AlertBox.display("No crossword available. Generate or load one");
            else
                CrosswordWindow.displaySolvable(crossword,ReprezentationType.UNSOLVED);
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(gameTitle,generateButton,solveButton,saveButton,loadButton,consolePrintButton,dictionaryButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,600,500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
