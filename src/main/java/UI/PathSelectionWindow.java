package UI;

import Enums.ReprezentationType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PathSelectionWindow {

    private static String path;

    public static String getPath(String title, String labelMessage, String msgFieldDefault){
        path = null;
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //blocking input for the rest of the apps windows
        window.setTitle("title");

        Label label = new Label(labelMessage);

        TextField textField = new TextField();
        textField.setPromptText(msgFieldDefault);

        Button button = new Button("OK");
        button.setOnAction(e-> {
            path = processTextField(textField.getText());
            if(path != null)
                window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,textField,button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,400,200);
        window.setScene(scene);
        window.showAndWait();
        return path;
    }
    private static String processTextField(String fieldText){
        return fieldText;
    }
}
