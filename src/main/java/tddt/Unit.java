package sample;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

/**
 * Created by lqx on 05/07/16.
 */
public class Unit {
    String direction = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String xmlFileName;
    String name;
    String content;
    File javaFile;

    FileReader filereader;

    public void parseXML() throws Exception {
    }

    public void createJavaFile() throws Exception {
        try {
            this.parseXML();
            this.javaFile = new File(direction +"sample/" +name + ".java");
            if (!javaFile.exists())
                javaFile.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(javaFile));
            bw.write(this.content);
            bw.close();
        } catch (Exception e) {
        }
    }
}