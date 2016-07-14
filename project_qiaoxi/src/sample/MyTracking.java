package sample;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MyTracking extends Parent {

    MyClock red;
    MyClock green;
    MyClock white;
    Rectangle chart1;
    Rectangle chart2;
    Rectangle chart0;
    private final int scale = 10;
    private VBox vbox = new VBox();
    public MyTracking(MyClock red,MyClock green,MyClock white) {
        this.red = red;
        this.green = green;
        this.white = white;
        configureVbox();
        getChildren().addAll(vbox);
    }

    private void configureVbox() {
        createBackground();
        vbox.getChildren().addAll(chart0,chart1,chart2);
        vbox.setSpacing(1);
    }

    private  void createBackground() {
        chart0 = new Rectangle(red.getTiming()*scale,scale,Color.RED);
        chart1 = new Rectangle(green.getTiming()*scale,scale,Color.GREEN);
        chart2 = new Rectangle(white.getTiming()*scale,scale,Color.WHITE);

    }
    public void refresh(){
        vbox.getChildren().clear();
        configureVbox();
    }
}