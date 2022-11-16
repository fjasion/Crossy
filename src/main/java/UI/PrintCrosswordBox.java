package UI;

import Entities.Crossword;
import Enums.ReprezentationType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrintCrosswordBox {
    public static void display(Crossword crossword){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //blocking input for the rest of the apps windows
        window.setTitle("Print Crossword");

        Label label = new Label("Choose printing format:");
        Button but1 = new Button();
        but1.setText("SOLVED");
        but1.setOnAction(e-> {
            crossword.getBoardRepresentation(ReprezentationType.SOLVED).print();
            window.close();
        });

        Button but2 = new Button();
        but2.setText("UNSOLVED");
        but2.setOnAction(e-> {
            crossword.getBoardRepresentation(ReprezentationType.UNSOLVED).print();
            window.close();
        });

        Button but3 = new Button();
        but3.setText("UNSOLVED JOLKA");
        but3.setOnAction(e-> {
            crossword.getBoardRepresentation(ReprezentationType.UNSOLVED_JOLKA).print();
            window.close();
        });

        Button but4 = new Button();
        but4.setText("CROSSWORD");
        but4.setOnAction(e-> {
            crossword.printCrossword();
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,but1,but2,but3,but4);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,400,200);
        window.setScene(scene);
        window.showAndWait();
    }
}
