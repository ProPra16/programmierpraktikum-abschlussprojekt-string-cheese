package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;

public class MenuController {
    private static Stage primaryStage;
    @FXML
    private MenuItem open;
    @FXML
    private MenuItem save;
    @FXML
    private MenuItem close;
    @FXML
    private TextArea editor;
    @FXML
    private TextArea test;
    @FXML
    private Button btnextstep;
    @FXML
    private Label statuslabel;
    @FXML
    private ListView<Exercise> exercises;
    @FXML
    private Label descriptionlabel;
    private Catalog catalog;
    public Main main;
    
    @FXML
    public void setMain(Main main){
		this.main = main;}
    
    @FXML
    protected void open(ActionEvent event) {

    }

    @FXML
    protected void save(ActionEvent event) {
        DirectoryChooser dialog = new DirectoryChooser();
        dialog.setTitle("Choose the directory");
        File directory = dialog.showDialog(primaryStage);
      
    }

    @FXML
    protected void close(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    protected void next(ActionEvent event) {
        switch (statuslabel.getText()) {
            case "RED":
                statuslabel.setText("GREEN");
                statuslabel.setTextFill(Color.GREEN);
                statuslabel.setId("green");
                break;
            case "GREEN":
                statuslabel.setText("REFACTOR");
                statuslabel.setTextFill(Color.BLACK);
                statuslabel.setId("black");
                break;
            case "REFACTOR":
                statuslabel.setText("RED");
                statuslabel.setTextFill(Color.RED);
                statuslabel.setId("red");
                break;
        }
    }

  

    @FXML
    protected void chooseExercise(MouseEvent event) {
        
    }

    public static void setStage (Stage stage) {
        primaryStage = stage;
    }
}
