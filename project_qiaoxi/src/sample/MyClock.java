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
    boolean babysteps;
    boolean running;
   private int count ;
   private int timing;
  private boolean warning;
    private boolean again;
    public MyClock() { running=false;
    count=0;
    timing=0;}

    public void reset(boolean babysteps) {
        this.babysteps=babysteps;
        running = false;
        timing =1;
        count=1;}

    public void stopClock() {
        running = false;
    }

    public void runClock(){
        running= true;
        if(!babysteps) {
            new Thread() {
                public void run() {
                    long last = System.nanoTime();
                    double delta = 0;
                    double ns = 10000000000.0;
                    while (running) {
                        long now = System.nanoTime();
                        delta += (now - last) / ns;
                        last = now;
                        while (delta >= 1) {
                            count = count + 1;
                            timing = count;
                            delta--;
                        }
                    }
                }
            }.start();
        }
        if(babysteps){
            new Thread() {
                public void run() {
                    long last = System.nanoTime();
                    double delta = 0;
                    double ns = 1000000000.0;
                    while (running) {
                        long now = System.nanoTime();
                        delta += (now - last) / ns;
                        last = now;
                        while (delta >= 1) {
                            if(count<60){
                                count = count+1;
                                timing=count;
                                delta--;}
                            if(count>=60 &&count<=120){
                                count = count+1;
                                timing=count;
                                delta--;
                            warning=true;}
                            if(count>120){
                                timing=count;
                                stopClock();
                                again=true;
                            }
                        }
                    }
                }
            }.start();
        }
    }
    public int getTiming(){
        return timing;
    }
    public boolean isRun(){
        return running;
    }
    public boolean getWarning(){
        return warning;
    }
    public void setWarning(boolean aWarning){
        this.warning=aWarning;
    }
    public boolean getAgain(){
        return again;
    }
    public void setAgainAndWarning(boolean aWarning,boolean aAgain){
        this.running=aAgain;
        this.warning=aWarning;
        this.again=aAgain;
    }
}