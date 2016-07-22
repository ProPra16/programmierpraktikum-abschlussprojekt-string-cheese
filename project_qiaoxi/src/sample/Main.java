package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage primarystage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Pane root = loader.load();
        Scene scene = new Scene(root);
        MyController controller = (MyController)loader.getController();
        controller.init(primarystage);
        primarystage.setTitle("SMART TESTING");

        primarystage.setScene(scene);
        primarystage.show();
        primarystage.setOnCloseRequest(e-> {
            System.exit(0);
        });
    }
}

