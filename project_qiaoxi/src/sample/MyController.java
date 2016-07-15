package sample;
import java.io.*;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
 import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;

import static java.lang.Thread.sleep;

public class MyController {
    Stage stage;
    @FXML
    HBox hBox;

    @FXML
    HBox tracking;

    @FXML
    TextArea textAreaForTest;

    @FXML
    TextArea textAreaForClass;

    @FXML
    Label testResults;

    @FXML
    Label compileResults;

    @FXML
    Button save = new Button();

    @FXML
    VBox colorPanel = new VBox();

    @FXML
    Label babystepswarning ;

    @FXML
    Button back = new Button();

    @FXML
    Button analysis = new Button();

    @FXML
    Button withbabysteps = new Button();
    @FXML
    Button withoutbabysteps = new Button();
    String direction = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private MyCompiler compilerForTest;
    private MyCompiler compilerForClass;
    boolean babysteps;
    String status;
    private MyClock rClock;
    private MyClock gClock;
    private MyClock wClock;
    private MyTracking myTracking;
    MyJavaFile testFile;
    MyJavaFile classFile;

    public void init(Stage primaryStage) {
       // analysis.setOnAction(e ->{AlertBox track = new AlertBox(); track.display("EEE","error");});
        this.stage = stage;
        textAreaForClass.setDisable(true);
        textAreaForTest.setDisable(true);
        save.setDisable(true);
        back.setDisable(true);
        analysis.setDisable(true);
        colorPanel.setStyle("-fx-background-color: grey;");
        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"test";
        File fp = new File(filePath);
        if (!fp.exists()) {
            fp.mkdirs();
        }
        rClock = new MyClock();
        gClock = new MyClock();
        wClock = new MyClock();
        myTracking = new MyTracking(rClock,gClock,wClock);
        tracking.getChildren().add(myTracking);

    }
    @FXML
    public void setWithbabysteps() {
        withbabysteps.setStyle("-fx-background-color: yellow;");
        chooseCatalog(true);
        colorPanel.setStyle("-fx-background-color: red;");
        gClock.reset(true);
        wClock.reset(false);
        rClock.reset(true);
        rClock.runClock();
        babysteps =true;
       new Thread(){
           public void run(){
            startCounting();}
        }.start();

    }
    @FXML
    public void setWithoutbabysteps() {
        babysteps= false;
        chooseCatalog(false);
        colorPanel.setStyle("-fx-background-color: red;");
        gClock.reset(false);
        wClock.reset(false);
        rClock.reset(false);
        rClock.runClock();
        myTracking.refresh();
    }

    @FXML
    void clickAnalysisButton() {
           String error=null;
        File a=new File(direction+"errorstracking.txt");
        try{FileReader fr=new FileReader(a);
            BufferedReader br= new BufferedReader(fr);
            String line = null;

            while((line=br.readLine())!=null)
             error=error+"\n"+line;
            br.close();
        }catch(Exception e){}
        AlertBox track = new AlertBox(); track.display("Tracking Anlaysis",error);
    }
    @FXML
    void clickSaveButton() {
        System.out.println(rClock.getTiming());
        System.out.println("WWW:"+wClock.getTiming());
      testFile = new MyJavaFile(compilerForTest.getName());
      classFile = new MyJavaFile(compilerForClass.getName());
        if (testFile != null && classFile != null) {
            saveFile(textAreaForTest.getText(), testFile);
            saveFile(textAreaForClass.getText(), classFile);
        }
        compilerForClass.compileAndRunTests();
        compilerForTest.compileAndRunTests();
        testResults.setText(compilerForTest.getTestResult().toString());
        compileResults.setText(compilerForClass.getCompilerResult().toString());
        int fails = compilerForTest.getTestResult().getNumberOfFailedTests();
        int success = compilerForTest.getTestResult().getNumberOfSuccessfulTests();

        if (fails ==0 && success!=0) {
            textAreaForClass.setDisable(false);
        }
        changeColor(compilerForTest);
        if(status.equals("green")){
            back.setDisable(false);
        }
        myTracking.configureHbox();
        }

    @FXML
    void clickBackButton(){

        textAreaForTest.clear();
        textAreaForTest.setText(testFile.getFileContent());
        textAreaForClass.clear();
        textAreaForClass.setText(classFile.getFileContent());

    }

    @FXML
    void changeColor(MyCompiler compilerForTest) {
        int failsNumber =compilerForTest.getTestResult().getNumberOfFailedTests();
       switch(failsNumber) {
           case 1:{
               colorPanel.setStyle("-fx-background-color: red;");
               status="red";
               rClock.runClock();
               gClock.stopClock();
               wClock.stopClock();
               break;}
           case 0:{
               colorPanel.setStyle("-fx-background-color: white;");
               status="white";
               rClock.stopClock();
               gClock.stopClock();
               wClock.runClock();
               break;}
           default:{
               colorPanel.setStyle("-fx-background-color: green;");
               status="green";
               rClock.stopClock();
               gClock.runClock();
               wClock.stopClock();break;}
       }
    }

    public String setCatalog() {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        String fileName = file.getName();
        if (file != null) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            textAreaForTest.setDisable(false);
            save.setDisable(false);
            analysis.setDisable(false);
        }
        else fileName="This File doesn't exist.";
        return fileName;

    }

    public void chooseCatalog(boolean babysteps){
        String fileName=setCatalog();
        ParseUnit parseUnit = new ParseUnit(fileName, babysteps);
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
    public void startCounting() {
                while (rClock.isRun()) {
                    System.out.println(rClock.getTiming());
                    if (rClock.getWarning()) {
                        rClock.setWarning(false);
                        System.out.println("WWWWWWWWWWWWWWWW");
                       // babystepswarning = new Label();
                       // babystepswarning.setText("WWW");
                       break;
                    }
                //    myTracking.refresh();
                    if (rClock.getAgain() || gClock.getAgain()) {
                        textAreaForClass.clear();
                        textAreaForClass.clear();
                        textAreaForTest.setText(testFile.getFileContent());
                        textAreaForClass.setText(classFile.getFileContent());
                        rClock.setAgainAndWarning(false, false);
                        babystepswarning.setDisable(true);
                        break;
                    }
                    try {
                        sleep(1000); //暂停，每一秒输出一次
                    }catch (InterruptedException e) {
                        return;
                    }
                }
            }

    }