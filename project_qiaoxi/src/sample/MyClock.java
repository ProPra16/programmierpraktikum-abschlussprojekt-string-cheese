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
    boolean babysteps;
    boolean running;
    int total;
    int limit;
   int timing;
  Color color;
    boolean warning;
   boolean again;
    private Text[] digits = new Text[3];
    private Group[] digitsGroup = new Group[3];
    private final int boxHeight = 25;
    private final int boxWidth = boxHeight * 5 / 8;
    private final int scale = 1;
    private Font FONT = new Font(15 * scale);
    private HBox hbox = new HBox();


    public MyClock(Color color) {
        configureDigits();
        configureHbox();
        getChildren().add(hbox);
        running=false;
total=0;
        timing=0;
        this.color=color;
    }
    private void configureHbox() {
        hbox.getChildren().addAll(digitsGroup);
        hbox.setSpacing(1);
    }
    public void configureDigits() {
        for (int i = 0; i < digits.length; i++) {
            digits[i] = new Text("0");
            digits[i].setFont(FONT);
            digits[i].setTextOrigin(VPos.TOP);
            digits[i].setLayoutY(5);

            digits[i].setFill(Color.GRAY);
            digitsGroup[i] = new Group(digits[i]);
        }
    }

    public void refreshDigits(String number) {
        for (int i = 0; i < digits.length; i++) {
            digits[i].setText(number.substring(i, i + 1));
        }
    }

    public void stopClock() {
        running = false;
        refreshDigits(df.format(timing));
        total=total+timing;
        timing=0;

    }
    public void reset(boolean babysteps) {
        this.babysteps=babysteps;
        running = false;
        total=0;
        timing =0;
        for (int i = 0; i < digits.length; i++)
            digits[i].setText("0");
    }

  public void runClock(){
      running= true;
      new Thread(){
          public void run() {
                  long last = System.nanoTime();
                  double delta = 0;
                  double ns = 1000000000.0;
                  int count = 0;
              Color red = Color.RED;
              Color green = Color.GREEN;
                  while (running) {
                      long now = System.nanoTime();
                      delta += (now - last) / ns;
                      last = now;
                      while (delta >= 1) {
                          if (!babysteps) {
                              if (count < 1000) {
                              count = count + 1;
                              refreshDigits(df.format(count));
                              timing = count;
                              delta--;
                              }
                              if (count >= 1000) {
                              refreshDigits(df.format(999));
                              }
                          }
                          if (babysteps && (color.equals(red)||color.equals(green))) {
                              if (count < limit/2) {
                                  count = count + 1;
                                  refreshDigits(df.format(count));
                                  timing = count;
                                  delta--;
                              }
                              if (count >= limit/2 && count<=limit) {
                                  count = count + 1;
                                  refreshDigits(df.format(count));
                                  timing = count;
                                  delta--;
                                  setAgainAndWarning(false,true);
                                  for (int i = 0; i < digits.length; i++) {
                                      digits[i].setFill(color);
                                      digits[i].setStyle("-fx-font-weight: bold");
                                  }
                              }
                              if(count>limit){
                                  refreshDigits(df.format(limit));
                                  setAgainAndWarning(true,true);
                                  total=total+limit;
                                  timing=0;
                                  reset(true);
                              }
                          }
                      }
                  }

          }
      }.start();
  }
    public int getTiming(){
        return timing;
    }
    public boolean isRun(){
        return running;
    }
    public int getTotal(){
        return total;
    }
    public boolean getWarning(){
        return warning;
    }
    public boolean getAgain(){
        return again;
    }
    public void setAgainAndWarning(boolean aAgain,boolean aWarning){
        this.warning=aWarning;
        this.again=aAgain;
        this.running=!aAgain;
    }
    public void setLimit(int limit){
        this.limit=limit;

    }
}