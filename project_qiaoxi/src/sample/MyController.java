package sample;
import java.io.File;
import java.io.FileWriter;
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
    Button back = new Button();

    @FXML
    Button withbabysteps = new Button();
    @FXML
    Button withoutbabysteps = new Button();

    private MyCompiler compilerForTest;
    private MyCompiler compilerForClass;


    public void init(Stage primaryStage) {
        this.stage = stage;
        textAreaForClass.setDisable(true);
        colorPanel.setStyle("-fx-background-color: red;");
        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"test";
        System.out.println(filePath);
        File fp = new File(filePath);
        // 创建目录
        if (!fp.exists()) {
            fp.mkdirs();// 目录不存在的情况下，创建目录。
        }

    }
    @FXML
    public void setWithbabysteps() {
       chooseCatalog(true);
    }
    @FXML
    public void setWithoutbabysteps() {
        chooseCatalog(false);
    }

    @FXML
    void clickSaveButton() {
        MyJavaFile testFile =new MyJavaFile(compilerForTest.name);
        MyJavaFile classFile =new MyJavaFile(compilerForClass.name);
        if(testFile!=null &&classFile!=null ) {
           saveFile(textAreaForTest.getText(), testFile);
           saveFile(textAreaForClass.getText(), classFile);
       }
       compilerForClass.compileAndRunTests();
        compilerForTest.compileAndRunTests();
        textAreaForTestResults.setText(compilerForTest.getTestResult().toString());
        if (compilerForTest.getTestResult().getNumberOfFailedTests() != 0)
        {  textAreaForClass.setDisable(false);
        }
        changeColor(compilerForTest);
    }

    @FXML
    void changeColor(MyCompiler compilerForTest) {
        int failsNumber =compilerForTest.getTestResult().getNumberOfFailedTests();
       switch(failsNumber) {
           case 1:
               colorPanel.setStyle("-fx-background-color: red;");break;
           case 0:
               colorPanel.setStyle("-fx-background-color: white;");break;
           default:
               colorPanel.setStyle("-fx-background-color: green;");break;
       }
    }

    public String setCatalog() {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        String fileName = file.getName();
        if (file != null) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        else fileName="This File doesn't exist.";
        return fileName;}

    public void chooseCatalog(boolean babysteps){
        String fileName=setCatalog();
        ParseUnit parseUnit = new ParseUnit(fileName, babysteps);
        MyJavaFile testFile;
        MyJavaFile classFile;
        if(!babysteps) {
            testFile = new MyJavaFile(parseUnit.getWithoutBabystepsTestName());
            classFile = new MyJavaFile(parseUnit.getWithoutBabystepsClassName());
        }
        else
        {
            testFile = new MyJavaFile(parseUnit.getWithBabystepsTestName());
            classFile = new MyJavaFile(parseUnit.getWithBabystepsClassName());
        }
        textAreaForTest.setText(testFile.getFileContent());
        textAreaForClass.setText(classFile.getFileContent());
        compilerForTest = new MyCompiler(testFile.getFileName());
        compilerForClass = new MyCompiler(classFile.getFileName());
    }
    private void saveFile(String content, MyJavaFile file) {
        try {
            FileWriter fileWriter = new FileWriter(file.javaFile);
            fileWriter.write(content);
            fileWriter.close();

        } catch (IOException e) {
        }
    }
}