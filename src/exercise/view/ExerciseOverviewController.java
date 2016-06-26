package exercise.view;

import exercise.MainApp;
import exercise.model.Exercise;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class ExerciseOverviewController {
    @FXML
    private TableView<Exercise> exerciseTable;
    @FXML
    private TableColumn<Exercise, String> nameColumn;


    @FXML
    private TextArea nameArea;
    @FXML
    private TextArea descriptionArea;

    private MainApp mainApp;

    public ExerciseOverviewController() {
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());

        showExerciseDetails(null);

        exerciseTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showExerciseDetails(newValue));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        exerciseTable.setItems(mainApp.getExerciseData());
    }

    private void showExerciseDetails(Exercise exercise) {
        if (exercise != null) {
            nameArea.setText(exercise.getName());
            descriptionArea.setText(exercise.getDescription());

        } else {
            nameArea.setText("");
            descriptionArea.setText("");

        }
    }

    @FXML
    private void handleDeleteExercise() {
        int selectedIndex = exerciseTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            exerciseTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Exercise Selected");
            alert.setContentText("Please select an exercise in the table.");

            alert.showAndWait();
        }
    }


    @FXML
    private void handleNewExercise() {
        Exercise tempExercise = new Exercise();
        boolean okClicked = mainApp.showExerciseEditDialog(tempExercise);
        if (okClicked) {
            mainApp.getExerciseData().add(tempExercise);
        }
    }
}