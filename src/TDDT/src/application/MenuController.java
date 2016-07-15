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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Paths;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MenuController {
    private static Stage primaryStage;
    @FXML
    private MenuItem open;
    @FXML
    private MenuItem save;
    @FXML
    private MenuItem close;
    @FXML 
    private MenuItem tracking;
    @FXML
    private TextArea editor;
    @FXML
    private TextArea test;
    @FXML
    private Button btnextstep;
    @FXML
    private Label statuslabel;
    @FXML
    private Label timelabel;
    @FXML
    private ListView<Exercise> exercises;
    @FXML
    private Label descriptionlabel;
    private Catalog catalog;
    private File directory;
    private Exercise currentexe;
    public Main main;
    
    @FXML
    public void setMain(Main main){
		this.main = main;}
    
    @FXML
    protected void open(ActionEvent event) {
        FileChooser dialog = new FileChooser();
        dialog.setTitle("Select a file");
        dialog.setInitialDirectory(Paths.get("src/Tests").toFile()); 
        dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File file = dialog.showOpenDialog(primaryStage);

        if(file == null) {
            return;
        }


        try {
        	catalog = new Parsercat().parse(file);
        } catch (Exception e) {
            System.err.println("Fehler beim Parsen des Katalogs aufgetreten");
            return;
        }

        catalog.loadExercises(editor, test, descriptionlabel, exercises);
                
    }


    private void speichernAbfrage(Saveoption option) {
        if(currentexe != null) {
            ButtonType ja = new ButtonType("Y");
            ButtonType nein = new ButtonType("N");
            String abfrage = "bevor zu einer anderen Aufgabe gewechselt wird";
            if (option == Saveoption.Close) {
                abfrage = "bevor das Programm geschlossen wird?";
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You want save, " + abfrage, ja, nein);

            alert.setHeaderText("");
            alert.setTitle("");
            alert.showAndWait();
            if (alert.getResult() == ja) {
                save(null);
            }
        }
    }



    private boolean continueAbfrage() {
        ButtonType ja = new ButtonType("Ja");
        ButtonType nein = new ButtonType("Nein");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Diese Aufgabe hast du bereits bearbeitet. Möchtest du daran weiterarbeiten?", ja, nein);
        alert.setHeaderText("");
        alert.setTitle("");
        alert.showAndWait();
        if(alert.getResult() == ja) {
            return true;
        }
        return false;
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
    protected void tracking(ActionEvent event) {
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
    	 if(exercises.getSelectionModel().getSelectedItem() == null) {
             return;
         }
         Exercise exercise = exercises.getSelectionModel().getSelectedItem();
         catalog.loadExercises(editor, test, descriptionlabel, exercises);
       
        // Babysteps baby = new Babysteps(exercise, statuslabel);
     }



    public static void setStage (Stage stage) {
        primaryStage = stage;
    }

	public Object getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setText(String string) {
		// TODO Auto-generated method stub
		
	}
}
