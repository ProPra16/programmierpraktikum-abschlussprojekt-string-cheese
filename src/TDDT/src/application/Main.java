package application;
	
import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	private Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		Menu();
	}
	
	public void Menu() {
		try {
			
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Menu.fxml"));
			AnchorPane pane =loader.load();
			primaryStage.setMinHeight(760);
			primaryStage.setMinWidth(600);
			MenuController menuController = loader.getController();
			menuController.setMain(this);
			
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
		
	}
}

