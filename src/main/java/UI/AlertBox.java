package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //blocking input for the rest of the apps windows
        window.setTitle(title);

        Label label = new Label(message);
        Button closeButton = new Button();
        closeButton.setText("OK");
        closeButton.setOnAction(e-> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,300,200);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void display(String message){
        display("",message);
    }
}
