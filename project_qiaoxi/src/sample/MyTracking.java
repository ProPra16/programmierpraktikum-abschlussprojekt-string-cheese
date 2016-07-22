package sample;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MyTracking extends Parent {
    int part;
    int[] last=new int[3];
    Paint[] paints=new Paint[3];
    int[] percent= new int[3];
    MyClock[] clocks=new MyClock[3];
    private final int scale = 10;
    private HBox hbox = new HBox();


    public MyTracking(MyClock red,MyClock green,MyClock white) {
        part=0;
        this.clocks[0] = red;
        this.clocks[1] = green;
        this.clocks[2] = white;
        this.paints[0]=Color.RED;
        this.paints[1]= Color.GREEN;
        this.paints[2]=Color.WHITE;
        for(int i=0;i<3;i++)
        {last[i]=0;}
        configureHbox();
        getChildren().addAll(hbox);
    }

    public void configureHbox() {
        Rectangle chart=new Rectangle(1,1);
int x=-1;
        for(int i=0;i<3;i++){
                if(clocks[i].isRun()) {
                    System.out.println("RUNNING COLOR IS::"+i);
                    System.out.println("last0:  "+last[i]);
                   part= part+clocks[i].getTiming()-last[i];
                    chart = new Rectangle((clocks[i].getTiming() - last[i])*2, scale, paints[i]);
                    System.out.println("now:   "+(clocks[i].getTiming() - last[i]));
                    last[i] = clocks[i].getTiming();
                    System.out.println("newlast: "+last[i]);
                    x=i;
                    break;
                }
           }

        if(part<=200) {
        hbox.getChildren().add(chart);}
        else
        {hbox.getChildren().clear();
            hbox.getChildren().add(chart);
        part=0;}

    }

    public void refresh(){
        hbox.getChildren().clear();
        configureHbox();
    }

    public String result(){
        String result;
        int totals= 0;
        percent =new int[3];
        for(int i=0;i<3;i++) {
            percent[i] = clocks[i].getTotal();
            if(clocks[i].isRun()){
                percent[i]=percent[i]+clocks[i].getTiming();
            }
            totals=totals+percent[i];
        }
        for(int i=0;i<3;i++)
        percent[i]=100*percent[i]/totals;
       result= "\n\n"+" Till now you have spent:********** "+percent[0]+"% on RED Status;\n"
                     +"                                      ********** "+percent[1]+"% on GREEN Status;\n"
                     +"                                      ********** "+percent[2]+"% on WHITE Status.\n";
        return result;
    }


}