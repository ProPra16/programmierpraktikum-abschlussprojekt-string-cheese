package sample;

import java.io.*;

/**
 * Created by lqx on 08/07/16.
 */
public class MyJavaFile {
    File javaFile;
    String direction = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private String fileName;
    private String fileContent;
    private boolean isATest;
    private boolean babysteps;

    public MyJavaFile(String fileName) {
        this.javaFile = new File(direction +"sample/"+ fileName + ".java");
        this.fileName = fileName;
        if (fileName.contains("Test"))
            isATest = true;
        else
            isATest = false;
        try {
            if (javaFile.isFile() && javaFile.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(javaFile));
                BufferedReader bufferedReader = new BufferedReader(read);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    fileContent = fileContent + line;
                }
                bufferedReader.close();
                read.close();
            }
        } catch (Exception e) {}
    }
    public String getFileContent() {
        return fileContent;
    }
    public String getFileName() {
        return fileName;
    }
    public boolean isATest() {
        return isATest;
    }
}