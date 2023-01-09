package UI;

import Containers.BoardRepresentation;
import Entities.Crossword;
import Enums.Orientation;
import Enums.ReprezentationType;
import javafx.beans.InvalidationListener;
import javafx.collections.ArrayChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrosswordWindow {
    public static void display(Crossword crossword,ReprezentationType repType){
        Stage window = new Stage();
        window.setTitle("crossword");

        int labelSize = 600/ crossword.getBoardSize();

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        //grid.setVgap(labelSize);
        //grid.setHgap(labelSize);
        //for(int i=0;i<crossword.getBoardSize();i++){
            //grid.getColumnConstraints().add(new ColumnConstraints(crossword.getBoardSize()));
        //}
        BoardRepresentation board = crossword.getBoardRepresentation(repType);
        var map = board.getCharacterMap();
        for(var x:map.keySet()){
            if(repType == ReprezentationType.SOLVED) {
                Label label = new Label(map.get(x).toString());
                label.setMaxSize(labelSize, labelSize);
                label.setMinSize(labelSize, labelSize);
                label.setAlignment(Pos.CENTER);
                grid.add(label, x - ((x / crossword.getBoardSize()) * crossword.getBoardSize()), x / crossword.getBoardSize());
            }
        }
        grid.setAlignment(Pos.CENTER);

        Scene scene = new Scene(grid,600,600);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void displaySolvable(Crossword crossword,ReprezentationType repType){
        Stage window = new Stage();
        window.setTitle("crossword");

        int labelSize = 800/ crossword.getBoardSize();

        GridPane grid = new GridPane();
        grid.setMaxSize(800,800);
        grid.setMinSize(800,800);

        grid.setGridLinesVisible(true);
        BoardRepresentation board = crossword.getBoardRepresentation(repType);
        var map = board.getCharacterMap();
        var mapTextFields = new HashMap<Integer,TextField>();
        for(var x:map.keySet()){
            TextField textField = new TextField();
            textField.setPromptText(map.get(x).toString());
            textField.setMaxSize(labelSize,labelSize);
            textField.setMinSize(labelSize,labelSize);
            textField.setAlignment(Pos.CENTER);
            mapTextFields.put(x,textField);
            grid.add(textField,x-((x/crossword.getBoardSize())* crossword.getBoardSize()),x/crossword.getBoardSize());
        }
        grid.setAlignment(Pos.CENTER);


        ArrayList<String> clues = new ArrayList<>();
        clues.add("HORIZONTAL:");
        clues.addAll(crossword.getClues(Orientation.HORIZONTAL));
        clues.add("VERTICAL:");
        clues.addAll(crossword.getClues(Orientation.VERTICAL));

        ListView<String> listView = new ListView<>();
        listView.setMaxSize(400,600);
        listView.setMinSize(400,600);
        ObservableList<String> observableList = FXCollections.observableArrayList(clues);
        listView.setItems(observableList);

        Button checkButton = new Button("CHECK");
        checkButton.setMaxSize(200,100);
        checkButton.setMinSize(200,100);
        checkButton.setAlignment(Pos.CENTER);
        checkButton.setOnAction(e->checkSolution(crossword,mapTextFields));

        VBox vBox = new VBox();
        vBox.setMaxWidth(400);
        vBox.setMaxWidth(400);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        vBox.getChildren().addAll(listView,checkButton);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(grid,vBox);
        Scene scene = new Scene(hBox,1300,800);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void checkSolution(Crossword crossword, Map<Integer,TextField> textFieldMap){
        Map<Integer,Character> characterMap = crossword.getBoardRepresentation(ReprezentationType.SOLVED).getCharacterMap();
        for(Integer i:textFieldMap.keySet()){
            if(textFieldMap.get(i).getText() == null || textFieldMap.get(i).getText().length() < 1 || textFieldMap.get(i).getText().charAt(0) != characterMap.get(i)) {
                AlertBox.display("Solution", "Invalid solution :(");
                return;
            }
        }
        AlertBox.display("Solution", "Valid solution!");
    }

}
