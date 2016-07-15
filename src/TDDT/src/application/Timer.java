package application;

public class Timer {
    String name;
    int timeSpend;
    long start = 0;
    long end = 0;
    public Timer(){
    }

    public void start() {
        start = System.currentTimeMillis();
    }

    public void end(){
        end = System.currentTimeMillis();
    }

    public int returnTimeSpentInSeconds() {
        timeSpend = (int) (end-start)/1000;
        return timeSpend;
    }

    public void setLabel(String s){
        name = s;
    }

    public String getLabel(){
        return name;
    }

}