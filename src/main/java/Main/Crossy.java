package Main;

import Entities.CrosswordDictionary;
import Entities.Crossword;
import Enums.ReprezentationType;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Crossy extends Application implements EventHandler<ActionEvent> {

    Button generateButton;
    Button printButton;
    Crossword crossword=null;
    CrosswordDictionary dictionary = null;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Crossy");

        generateButton = new Button();
        generateButton.setText("Generate");
        generateButton.setOnAction(this);
        printButton = new Button();
        printButton.setText("Print");
        printButton.setOnAction(this);

        StackPane layout = new StackPane();
        layout.getChildren().add(generateButton);
        layout.getChildren().add(printButton);

        dictionary = new CrosswordDictionary();
        dictionary.load("src/main/resources/example_data.txt");

        Scene scene = new Scene(layout,600,500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == generateButton){
            crossword = new Crossword(10);
            crossword.generateFromDictionary(dictionary,8);
        }
        if(event.getSource() == printButton){
            //crossword.printCrossword();
            crossword.getBoardRepresentation(ReprezentationType.SOLVED).print();
            System.out.println();
            crossword.getBoardRepresentation(ReprezentationType.UNSOLVED_JOLKA).print();
            System.out.println();
            crossword.getBoardRepresentation(ReprezentationType.UNSOLVED).print();
            System.out.println();
            crossword.printCrossword();
        }
    }
}
