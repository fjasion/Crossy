package Main;

import Entities.CrosswordDictionary;
import Entities.Crossword;
import Enums.ReprezentationType;
import UI.AlertBox;
import UI.PrintCrosswordBox;
import Utils.Loader;
import Utils.Serializer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        Button dictionaryButton = new Button("Import dictionary");
        Button generateButton = new Button("Generate crossword");
        Button saveButton = new Button("Save crossword");
        Button loadButton = new Button("Load crossword");
        Button printButton = new Button("Print options");

        dictionaryButton.setOnAction(e-> dictionary = Loader.loadDictionary());

        generateButton.setOnAction(e-> {
            crossword = new Crossword(10);
            crossword.generateFromDictionary(dictionary, 8);
        });
        saveButton.setOnAction(e->Loader.saveCrossword(crossword));
        loadButton.setOnAction(e->crossword = Loader.loadCrossword());
        printButton.setOnAction(e->PrintCrosswordBox.display(crossword));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(dictionaryButton,generateButton,saveButton,loadButton,printButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,600,500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
