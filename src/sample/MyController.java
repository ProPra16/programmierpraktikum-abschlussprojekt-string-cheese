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
    TextArea textArea1;

    @FXML
    TextArea textArea2;

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
            textArea1.setText(testFile.getFileContent());
            textArea2.setText(classFile.getFileContent());
          //  MyCompiler myCompiler1 = new MyCompiler(parseUnit.javaFileForTest.getFileName());
          //  MyCompiler myCompiler2 = new MyCompiler(parseUnit.javaFileForClass.getFileName());
          //  myCompiler1.compileAndRunTests();
           // myCompiler2.compileAndRunTests();
        }
   }
    public void setWithoutbabysteps() throws IOException {
        final FileChooser fileChooser =new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        if(file!=null){
            String fileName=file.getName();
            fileName=fileName.substring(0,fileName.lastIndexOf("."));
            System.out.println("openwithoutbabysteps:"+fileName);

            ParseUnit parseUnit = new ParseUnit(fileName,false);
            MyJavaFile testFile = new MyJavaFile(parseUnit.getWithoutBabystepsTestName());
            MyJavaFile classFile = new MyJavaFile(parseUnit.getWithoutBabystepsClassName());
            textArea1.setText(testFile.getFileContent());
            textArea2.setText(classFile.getFileContent());
           // MyCompiler myCompiler1 = new MyCompiler(parseUnit.javaFileForTest.getFileName());
           // MyCompiler myCompiler2 = new MyCompiler(parseUnit.javaFileForClass.getFileName());
           // myCompiler2.compileAndRunTests();
          //  myCompiler1.compileAndRunTests();
           // String cmd=System.getProperty("user.dir");
          //  cmd=cmd+"/run_tests.sh";
          //  Runtime.getRuntime().exec(cmd);
        }
    }

    @FXML
     void clickSaveButton(){
        String text = textArea1.getText();
        textArea2.setText(text);
        textArea1.clear();
        changeColor();
    }
@FXML
    void changeColor() {
    colorPanel.setStyle("-fx-background-color: red;");
}

}