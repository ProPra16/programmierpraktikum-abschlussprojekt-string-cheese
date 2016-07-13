package sample;
import java.io.File;
import java.io.IOException;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
 import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
public class MyController {
Stage stage;
    @FXML
    HBox hBox;

    @FXML
    TextArea textAreaForTest;

    @FXML
    TextArea textAreaForClass;

    @FXML
    Label textAreaForTestResults;

    @FXML
    Button save = new Button();

@FXML
VBox colorPanel = new VBox();


    @FXML
    Button withbabysteps = new Button() ;
    @FXML
    Button withoutbabysteps = new Button() ;


    public void init(Stage primaryStage){
    this.stage = stage;
}
    public void setWithbabysteps(){
        final FileChooser fileChooser =new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
       fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        if(file!=null){
            String fileName=file.getName();
            fileName=fileName.substring(0,fileName.lastIndexOf("."));
            System.out.println("openbabysteps:"+fileName);
            ParseUnit parseUnit = new ParseUnit(fileName,true);
            MyJavaFile testFile = new MyJavaFile(parseUnit.getWithBabystepsTestName());
            MyJavaFile classFile = new MyJavaFile(parseUnit.getWithBabystepsClassName());
            textAreaForTest.setText(testFile.getFileContent());
            textAreaForClass.setText(classFile.getFileContent());
           MyCompiler myCompiler1 = new MyCompiler(testFile.getFileName());
           myCompiler1.compileAndRunTests();
            System.out.println(myCompiler1.getTestResult().toString());
            textAreaForTestResults.setText(myCompiler1.getTestResult().toString());
            //  MyCompiler myCompiler2 = new MyCompiler(parseUnit.javaFileForClass.getFileName());
            //   myCompiler2.compileAndRunTests();
        }
   }
    public void setWithoutbabysteps() throws IOException {
        final FileChooser fileChooser =new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        if(file!=null) {
            String fileName = file.getName();
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            System.out.println("openwithoutbabysteps:" + fileName);
            ParseUnit parseUnit = new ParseUnit(fileName, false);
            MyJavaFile testFile = new MyJavaFile(parseUnit.getWithoutBabystepsTestName());
            MyJavaFile classFile = new MyJavaFile(parseUnit.getWithoutBabystepsClassName());
            textAreaForTest.setText(testFile.getFileContent());
            textAreaForClass.setText(classFile.getFileContent());
            MyCompiler myCompiler1= new MyCompiler(testFile.getFileName());
            myCompiler1.compileAndRunTests();
            textAreaForTestResults.setText(myCompiler1.getTestResult().toString());
        }
    }

    @FXML
     void clickSaveButton(){


       // String text = textAreaForTest.getText();
       // textAreaForClass.setText(text);
      //  textAreaForTest.clear();
      //  changeColor();
    }
@FXML
    void changeColor() {
    colorPanel.setStyle("-fx-background-color: red;");
}

}