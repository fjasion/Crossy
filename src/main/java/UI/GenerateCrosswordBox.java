package UI;

import CustomExceptions.GenerationTimeExceeded;
import Entities.Crossword;
import Entities.CrosswordDictionary;
import Entities.DictionaryEntity;
import Enums.ReprezentationType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GenerateCrosswordBox {

    static Crossword crossword = null;
    public static Crossword generate(CrosswordDictionary dictionary) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //blocking input for the rest of the apps windows
        window.setTitle("Generate Crossword");

        Label label = new Label("Choose board size and number of clues:");

        TextField boardSizeTextField = new TextField();
        boardSizeTextField.setPromptText("board size");

        TextField expectedCluesTextBox = new TextField();
        expectedCluesTextBox.setPromptText("expected clues");

        Button button = new Button();
        button.setText("GENERATE");

        button.setOnAction(e -> {
            try {
                int boardSize = Integer.parseInt(boardSizeTextField.getText());
                int clueSize = Integer.parseInt(expectedCluesTextBox.getText());
                if (boardSize <= 0 || clueSize <= 0)
                    throw new Exception();
            } catch (Exception exception) {
                AlertBox.display("ERROR", "Enter valid board and dictionary size");
                crossword = null;
                return;
            }

            try {
                crossword = new Crossword(Integer.parseInt(boardSizeTextField.getText()));
                crossword.generateFromDictionary(dictionary, Integer.parseInt(expectedCluesTextBox.getText()));
                AlertBox.display("SUCCESS", "Successfully generated crossword");
            } catch (GenerationTimeExceeded exception) {
                crossword = null;
                AlertBox.display("ERROR", "Crossword max generation time exceeded \n try decreasing number of words/increasing board or dictionary size");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, boardSizeTextField, expectedCluesTextBox, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500, 300);
        window.setScene(scene);
        window.showAndWait();
        return crossword;
    }
}
