package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MyController {


    @FXML
    TextArea textArea1;

    @FXML
    TextArea textArea2;

    @FXML
     void clickButton(){
        String text = textArea1.getText();
        textArea2.setText(text);
        textArea1.clear();
    }
}