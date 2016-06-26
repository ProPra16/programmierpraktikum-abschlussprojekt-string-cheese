package exercise;/**
 * Created by Palusch on 25.06.2016.
 */

import exercise.model.Exercise;
import exercise.model.ExerciseListWrapper;
import exercise.view.ExerciseEditDialogController;
import exercise.view.ExerciseOverviewController;
import exercise.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Exercise> exerciseData = FXCollections.observableArrayList();

    public MainApp() {
        exerciseData.add(new Exercise("Groesster gemeinsamer Teiler", "Ein Programm, das den groessten" + "\n" +
                "gemeinsamen Teiler zweier nichtnegativer" + "\n" +
                "ganzer Zahlen mit Hilfe des Euklidischen" + "\n" + "Algorithmus berechnet und ausgibt."));
        exerciseData.add(new Exercise("Matrixmultiplikation", "Ein Programm zur Multiplikation" + "\n" +
                "rechteckiger (aber nicht notwendigerweise" + "\n" + "quadratischer) Matrizen"));
        exerciseData.add(new Exercise("LeapYear", "Ein Programm, das berechnet," + "\n" +
                "ob ein gegebenes Jahr ein Schaltjahr ist."));
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TDDApp");

        initRootLayout();

        showExerciseOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getExerciseFilePath();
        if (file != null) {
            loadExerciseDataFromFile(file);
        }
    }

    public void showExerciseOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ExerciseOverview.fxml"));
            AnchorPane exerciseOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(exerciseOverview);

            ExerciseOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showExerciseEditDialog(Exercise exercise) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EditExerciseDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Exercise");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ExerciseEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setExercise(exercise);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Exercise> getExerciseData() {
        return exerciseData;
    }

    public File getExerciseFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setExerciseFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            primaryStage.setTitle("TDDApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            primaryStage.setTitle("TDDApp");
        }
    }

    public void loadExerciseDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ExerciseListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            ExerciseListWrapper wrapper = (ExerciseListWrapper) um.unmarshal(file);

            exerciseData.clear();
            exerciseData.addAll(wrapper.getExercises());

            setExerciseFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveExerciseDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ExerciseListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ExerciseListWrapper wrapper = new ExerciseListWrapper();
            wrapper.setExercises(exerciseData);

            m.marshal(wrapper, file);

            setExerciseFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}