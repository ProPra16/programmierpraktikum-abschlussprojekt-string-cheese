package sample;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.chart.NumberAxis;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.DecimalFormat;

public class MyClock extends Parent {
    DecimalFormat df = new DecimalFormat("000");
    boolean running;
   private int timing=1;
    public MyClock() {}

    public void reset() {
        running = false;
       timing =1;}

    public void stopClock() {
        running = false;
    }

    public void runClock(){
        running= true;
        new Thread(){
            public void run(){
                long last = System.nanoTime();
                double delta= 0;
                double ns = 10000000000.0;
                int count =0;
                while(running){
                    long now = System.nanoTime();
                    delta += (now-last)/ns;
                    last = now;
                    while( delta>=1){
                            count = count+1;

                            timing=count;
                            delta--;}
                }
            }
        }.start();
    }
    public int getTiming(){
        return timing;
    }
}