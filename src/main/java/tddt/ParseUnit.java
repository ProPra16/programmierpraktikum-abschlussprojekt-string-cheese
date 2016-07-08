package sample;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;

/** Through this class, the programm can parse the "catalog" file( example here: excersices.xml) to .java file.
 *
 *
 */

public class ParseUnit {
    String direction = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String xmlFileName;
    String name;
    String content;
    MyJavaFile javaFile;
    boolean isATest;

    /*public ParseUnit(String name) {
        this.javaFile = new File(direction + name + ".java");
        this.name = name;
        if (name.contains("Test"))
            isATest = true;
        else
            isATest = false;
        try {
            if (javaFile.isFile() && javaFile.exists()) {

                InputStreamReader read = new InputStreamReader(new FileInputStream(javaFile));
                BufferedReader bufferedReader = new BufferedReader(read);
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    content = content + line;
                }
                bufferedReader.close();
                read.close();
            }
        } catch (Exception e){}
    }
*/
    public ParseUnit(String xmlFileName, String testOrCode) {
       this.xmlFileName=xmlFileName;
        if (testOrCode == "test")
            this.isATest = true;
        if (testOrCode == "class")
            this.isATest = false;
        createJavaFile();
    }

    public void parseXML() throws Exception {
        java.io.File file = new java.io.File(direction + "sample/" + xmlFileName + ".xml");

        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element rootElement = document.getRootElement();
        System.out.println("ROOT:" + rootElement.getName());
        Iterator exerciseElement = rootElement.elementIterator("exercise");
        // lesen die child node(naemlich <exercise> ein.
        while (exerciseElement.hasNext()) {
            Element childElement = (Element) exerciseElement.next();
            if (isATest) {
                Iterator testsIterator = childElement.elementIterator("tests");
                while (testsIterator.hasNext()) {

                    Element testsItem = (Element) testsIterator.next();
                    Iterator classIterator = testsItem.elementIterator("test");
                    Element testNameElement = (Element) classIterator.next();
                    name = testNameElement.attributeValue("name");
                    content = testsItem.elementText("test");
                    System.out.println("TestName :" + name);
                    System.out.println(content);
                } // lesen die Elements unter dem <tests> ein.
            }
            if (!isATest) {
                Iterator classesIterator = childElement.elementIterator("classes");
                while (classesIterator.hasNext()) {
                    Element classesItem = (Element) classesIterator.next();
                    content = classesItem.elementText("class");
                    Iterator classIterator = classesItem.elementIterator("class");
                    Element classNameElement = (Element) classIterator.next();
                    name = classNameElement.attributeValue("name");
                    System.out.println("ClassName :" + name);
                    System.out.println(content);
                } // lesen die Elements unter  dem <classes> ein.
            }
        }
    }

    public void createJavaFile()  {
        try {
            this.parseXML();
           File file = new File(direction + name + ".java");
            if (!file.exists())
                file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(this.content);
            bw.close();
           this.javaFile=new MyJavaFile(file.getName()) ;
        } catch (Exception e) {}

    }
}