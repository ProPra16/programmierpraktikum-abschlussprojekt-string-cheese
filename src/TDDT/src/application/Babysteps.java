package application;
import java.util.Timer;
import java.util.TimerTask;

import application.Exercise;
import javafx.application.Platform;
import application.MenuController;
public class Babysteps {
    private int timer;
    private int oldTimer;
    private String oldEditorText;
    private String oldTestText;
    private Timer t;


    public Babysteps() {
    
    }

    public void babystep(MenuController statuslabel, MenuController timelabel, MenuController editor, MenuController test) {
        if (timer > 0) {
            t.schedule(new TimerTask() {
                public void run() {
                    Platform.runLater(() -> {
                        if (!statuslabel.getText().equals("REFACTOR CODE") && !statuslabel.getText().equals("REFACTOR TEST")) {
                            int minutes = timer / 60;
                            int seconds = timer % 60;
                            String prettySeconds = Integer.toString(seconds);
                            if(seconds < 10) {
                                prettySeconds = "0" + prettySeconds;
                            }
                            timelabel.setText(Integer.toString(minutes) + ":" + prettySeconds);
                            if (timer == 0) {
                                timer = oldTimer;
                                if (statuslabel.getText().equals("RED")) {
                                    test.setText(oldTestText);
                                } else {
                                    editor.setText(oldEditorText);
                                }
                            }
                            timer--;
                        }
                        else {
                            timelabel.setText("-:-");
                        }
                    });
                }
            }, 0, 1000);
        }
    }

    public void refreshTimer() {
        timer = oldTimer;
    }

    public void stopTimer() {
        t.cancel();
    }


}