package application;
import java.util.ArrayList;

public class Tracking {
    ArrayList<Timer> timerList;
 
    public Tracking(){
        timerList.add(new Timer());
    }

    public void addTimer(){
        timerList.add(new Timer());
    }

    
    public void startTimer(){
        timerList.get(0).start();
    }

    public void stopTimer(){
        timerList.get(0).end();
    }

    public int getTimer() {
        return timerList.get(0).returnTimeSpentInSeconds();
    }

    public void setLabel(String s){
        timerList.get(0).setLabel(s);
    }

    public String getLabel(){
        return timerList.get(0).getLabel();
    }

   
    public void startTimer(int x){
        if(x>=timerList.size()) return;
        timerList.get(x).start();
    }

    public void stopTimer(int x){
        if(x>=timerList.size()) return;
        timerList.get(x).end();
    }

    public int getTimer(int x) {
        if (x >=timerList.size()) return 0;
        return timerList.get(x).returnTimeSpentInSeconds();
    }

    public void setLabel(String s, int x){
        timerList.get(x).setLabel(s);
    }

    public String getLabel(int x){
        return timerList.get(x).getLabel();
    }
}
