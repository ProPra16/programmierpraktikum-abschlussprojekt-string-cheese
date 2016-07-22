package sample;
import java.io.*;
import java.util.Optional;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
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
    VBox babystepsclock= new VBox();

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
    boolean start;
    String status;
    String status0;
    private MyClock rClock;
    private MyClock gClock;
    private MyClock wClock;
    private MyTracking myTracking;
    MyJavaFile testFile;
    MyJavaFile classFile;

    public void init(Stage primaryStage) {
        this.stage = stage;
        textAreaForClass.setDisable(true);
        textAreaForTest.setDisable(true);
        save.setDisable(true);
        back.setDisable(true);
        analysis.setDisable(true);
        colorPanel.setStyle("-fx-background-color: grey;");
        File fp = new File(direction+"test");
        System.out.println(direction);
        if (!fp.exists()) {
            fp.mkdirs();
        }
        File error = new File(direction+"errorTracking.txt");
        if(error.exists()) {error.delete();}
        rClock = new MyClock(Color.RED);
        gClock = new MyClock(Color.GREEN);
        wClock = new MyClock(Color.WHITE);
        myTracking = new MyTracking(rClock,gClock,wClock);
        tracking.getChildren().add(myTracking);
        new Thread(){
            public void run(){
            play();}
        }.start();

    }

    public void resume(){
        File error = new File(direction+"errorTracking.txt");
        if(error.exists())
        {
            error.delete();}
        withbabysteps.setStyle("");
        withoutbabysteps.setStyle("");
        textAreaForTest.clear();
        textAreaForClass.clear();
        compileResults.setText("");
        testResults.setText("");
        textAreaForClass.setDisable(true);
        textAreaForTest.setDisable(true);
        save.setDisable(true);
        back.setDisable(true);
        analysis.setDisable(true);
        colorPanel.setStyle("-fx-background-color: grey;");
        tracking.getChildren().clear();
        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"test";
        File fp = new File(filePath);
        if (!fp.exists()) {
            fp.mkdirs();
        }
        rClock.reset(false);
        gClock.reset(false);
        wClock.reset(false);
        myTracking = new MyTracking(rClock,gClock,wClock);
        tracking.getChildren().add(myTracking);
    }

    public void startAlert() {
        if (start) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setContentText("Are you sure to start with a new Catalog without saving?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                start = false;
                babysteps = false;
                resume();
            }
            if (action.get() == ButtonType.CANCEL) {}
        }
    }

    @FXML
    public void setWithbabysteps() {
        startAlert();
        if (!start) {
            chooseCatalog(true);
            if (start) {
                withbabysteps.setStyle("-fx-font-weight: bold;-fx-background-color: lightgrey;");
                colorPanel.setStyle("-fx-background-color: red;");
                gClock.reset(true);
                wClock.reset(false);
                rClock.reset(true);
                babystepsclock.getChildren().addAll(rClock);
                rClock.runClock();
                babysteps = true;
            }
        }
    }

    @FXML
    public void setWithoutbabysteps() {
       startAlert();
        if (!start) {
            chooseCatalog(false);

            }
        if(start){
            babystepsclock.getChildren().clear();
            withoutbabysteps.setStyle("-fx-font-weight: bold;-fx-background-color: lightgrey;");
            colorPanel.setStyle("-fx-background-color: red;");
            gClock.reset(false);
            wClock.reset(false);
            rClock.reset(false);

            rClock.runClock();
            myTracking.refresh();
        }
    }

    @FXML
    void clickAnalysisButton() {
        myTracking.result();
        String error="your CLASS CODES are saved at:\n"+direction.replaceAll("out/","test/")+compilerForClass.getName()+".java";
error=error+"\nand your TEST CODES are saved at:\n" +direction.replaceAll("out/","test/")+compilerForTest.getName()+".java";
        error=error+myTracking.result();


        File a=new File(direction+"errorTracking.txt");
        if(a.exists()) {
            error=error+"\n********************************************************************\n";
            error=error+"                  Here is the ERROR-INFORMATION. \n";
            try {
                FileReader fr = new FileReader(a);
                BufferedReader br = new BufferedReader(fr);
                String line = null;
                while ((line = br.readLine()) != null)
                    error = error + "\n" + line;
                br.close();
            } catch (Exception e) {
                AlertBox noErrorFile = new AlertBox(100,100);
                noErrorFile.display("Error","Don't find the File(errorTracking.txt) in the path "+direction);
            }
        }
        AlertBox track = new AlertBox();
        track.display("Tracking Anlaysis",error);
    }

    @FXML
    void clickSaveButton() {
        myTracking.configureHbox();
        status0=status;
        testFile = new MyJavaFile(compilerForTest.getName());
        classFile = new MyJavaFile(compilerForClass.getName());
        if (testFile != null && classFile != null) {
            saveFile(textAreaForTest.getText(), testFile);
            saveFile(textAreaForClass.getText(), classFile);
        }
        compilerForClass.compileAndRunTests();
        if (compilerForClass.getCompilerResult().getCompileErrors() == 0) {
            compilerForTest.compileAndRunTests();
            testResults.setText("");
            testResults.setText(compilerForTest.getTestResult().toString());
            compileResults.setText("");
            int fails = compilerForTest.getTestResult().getNumberOfFailedTests();
            int success = compilerForTest.getTestResult().getNumberOfSuccessfulTests();
            if (fails != 0 || success != 0) {
                textAreaForClass.setDisable(false);
            }
            changeColor(compilerForTest);
            if (status.equals("green")) {
                back.setDisable(false);
            }
        }
        else{
            testResults.setText("");
            compileResults.setText(compilerForClass.getCompilerResult().toString());
        }
        analysis.setDisable(false);
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
               if(babysteps) {
                   babystepsclock.getChildren().clear();
                   babystepsclock.getChildren().add(rClock);
               }
               if(!rClock.isRun())
               rClock.runClock();
               gClock.stopClock();
               wClock.stopClock();
               break;}
           case 0:{
               colorPanel.setStyle("-fx-background-color: white;");
               status="white";
               rClock.stopClock();
               gClock.stopClock();
               babystepsclock.getChildren().clear();
               if(!wClock.isRun())
              wClock.runClock();
               break;}
           default:{
               colorPanel.setStyle("-fx-background-color: green;");
               status="green";
               rClock.stopClock();
               wClock.stopClock();
               if(!gClock.isRun());
               gClock.runClock();
               if(babysteps) {
               babystepsclock.getChildren().clear();
               babystepsclock.getChildren().add(gClock);
               }
               break;}
       }
    }

    public String setCatalog() {
        final FileChooser fileChooser = new FileChooser();
        String fileName=null;
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        try {
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileName = file.getName();
            if (file != null) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
              //  analysis.setDisable(false);
            }
        }catch (Exception e){}
        return fileName;
    }

    public void chooseCatalog(boolean babysteps) {
        String fileName = setCatalog();
        boolean parseSuccess=true;
        if (fileName != null) {
            start = true;
            ParseUnit parseUnit = new ParseUnit(fileName, babysteps);
            if (parseUnit.getXmlFileName() != null) {
                if (!babysteps) {
                    testFile = new MyJavaFile(parseUnit.getWithoutBabystepsTestName());
                    classFile = new MyJavaFile(parseUnit.getWithoutBabystepsClassName());
                } else {
                    int limitTime = parseUnit.getBabaystepsTime();
                    rClock.setLimit(limitTime);
                    gClock.setLimit(limitTime);
                    testFile = new MyJavaFile(parseUnit.getWithBabystepsTestName());
                    classFile = new MyJavaFile(parseUnit.getWithBabystepsClassName());
                }
                textAreaForTest.setDisable(false);
                save.setDisable(false);
                textAreaForTest.setText(testFile.getFileContent());
                textAreaForClass.setText(classFile.getFileContent());
                compilerForTest = new MyCompiler(testFile.getFileName());
                compilerForClass = new MyCompiler(classFile.getFileName());
            }
            else {
                start = false;
                parseSuccess = false;
            }
        }
        if (fileName == null&& parseSuccess)  {
                start = false;
                AlertBox noXMLFile = new AlertBox(200, 100);
                noXMLFile.display("Warning", "\n\n   Please choose a File(.xml). ");
            }
        }


    private void saveFile(String content, MyJavaFile file) {
        try {
            FileWriter fileWriter = new FileWriter(file.javaFile);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {}
    }

    public void play() {
        while (true) {
            while (start) {
                if(rClock.getAgain()||gClock.getAgain()) {
                  colorPanel.setStyle("-fx-background-color:"+status0+";");
                    textAreaForTest.clear();
                    textAreaForClass.clear();
                    textAreaForTest.setText(testFile.getFileContent());
                    textAreaForClass.setText(classFile.getFileContent());
                }
            }
        }
    }

    }