package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox {
    Stage window = new Stage();
    final ScrollPane sp = new ScrollPane();
    final VBox vb = new VBox();
    Label fileName = new Label();

    public AlertBox(int width,int height){
        VBox box = new VBox();
        Scene scene = new Scene(box,width,height);
        this.window.setMinWidth(width);
        this.window.setMinHeight(height);
        window.setScene(scene);
        box.getChildren().addAll(sp,fileName);
        VBox.setVgrow(sp, Priority.ALWAYS);
        fileName.setLayoutY(30);
        fileName.setLayoutX(100);

    }
    public AlertBox(){
        VBox box = new VBox();
        Scene scene = new Scene(box,480,480);
        this.window.setMinWidth(500);
        this.window.setMinHeight(500);
        window.setScene(scene);
        box.getChildren().addAll(sp,fileName);
        VBox.setVgrow(sp, Priority.ALWAYS);
        fileName.setLayoutY(30);
        fileName.setLayoutX(160);

    }
    public void display(String title,String message){

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        Label label = new Label();
        label.setText(message);
        vb.getChildren().add(label);
        sp.setVmax(440);
        sp.setPrefSize(150,150);
        sp.setContent(vb);

        window.showAndWait();
    }


}
