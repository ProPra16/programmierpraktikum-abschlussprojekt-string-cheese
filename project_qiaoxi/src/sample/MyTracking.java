package sample;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.Rectangle;

public class MyTracking extends Parent {
    int[] last=new int[3];
    Paint[] paints=new Paint[3];
   int[] percent= new int[3];
    MyClock[] clocks=new MyClock[3];
    private final int scale = 10;
    private HBox hbox = new HBox();
    public MyTracking(MyClock red,MyClock green,MyClock white) {
        this.clocks[0] = red;
        this.clocks[1] = green;
        this.clocks[2] = white;
        this.paints[0]=Color.RED;
       this. paints[1]= Color.GREEN;
      this.  paints[2]=Color.WHITE;
        for(int i=0;i<3;i++)
        {last[i]=0;}
        configureHbox();
        getChildren().add(hbox);
    }

    public void configureHbox() {
        Rectangle chart=new Rectangle(1,1);
        for(int i=0;i<3;i++){
                if(clocks[i].isRun()) {
                    chart = new Rectangle((clocks[i].getTiming() - last[i]) * scale, scale, paints[i]);
                    last[i] = clocks[i].getTiming();
                }
           }
        hbox.getChildren().add(chart);

    }

    public void refresh(){
        hbox.getChildren().clear();
        configureHbox();
    }
    public void result(){
        int total= 0;
       percent =new int[3];
        for(int i=0;i<3;i++) {
            percent[i] = clocks[i].getTiming();
           total=total+percent[i];
        }
for(int i=0;i<3;i++)
        percent[i]=200*percent[i]/total;
    }
}