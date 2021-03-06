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

    public MyJavaFile(String fileName) {
        String    testDirection=direction.replaceAll("out/","test/");
        this.javaFile = new File(testDirection+ fileName + ".java");
        this.fileName = fileName;
        if (fileName.contains("Test"))
            isATest = true;
        else
            isATest = false;
        try {
            if (javaFile.isFile() && javaFile.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(javaFile));
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                   stringBuilder.append("\n");
                }
                bufferedReader.close();
                fileContent = stringBuilder.toString();
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