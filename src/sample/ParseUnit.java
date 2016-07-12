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
    String[] testName=new String[2];
    String[] className=new String[2];
    String[] testContent=new String[2];
    String[] classContent=new String[2];
    MyJavaFile javaFileForTest;
    MyJavaFile javaFileForClass;
    public ParseUnit(String xmlFileName, boolean babysteps) {
       this.xmlFileName=xmlFileName;
        createJavaFile(babysteps);
        System.out.print(direction);
    }

    public void parseXML() throws Exception {
        java.io.File file = new java.io.File(direction + "sample/" + xmlFileName + ".xml");

        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element rootElement = document.getRootElement();

        Iterator exerciseElement = rootElement.elementIterator("exercise");
        int i;
        // lesen die child node(naemlich <exercise> ein.
        while (exerciseElement.hasNext()) {
            Element childElement = (Element) exerciseElement.next();
            Iterator configIterator = childElement.elementIterator("config");
            while (configIterator.hasNext()) {
                Element configItem = (Element) configIterator.next();
                Iterator babystepsIterator = configItem.elementIterator("babysteps");
                Element babystepsElement = (Element) babystepsIterator.next();
                if(babystepsElement.attributeValue("value").equals("True"))
                    i=1;
                else
                    i=0;
                    Iterator testsIterator = childElement.elementIterator("tests");
                Iterator classesIterator = childElement.elementIterator("classes");
                    while (testsIterator.hasNext()) {
                        Element testsItem = (Element) testsIterator.next();
                        Iterator classIterator = testsItem.elementIterator("test");
                        Element testNameElement = (Element) classIterator.next();
                        testName[i] = testNameElement.attributeValue("name");
                        testContent[i] = testsItem.elementText("test");
                     } // lesen die Elements unter dem <tests> ein.
                    while (classesIterator.hasNext()) {
                    Element classesItem = (Element) classesIterator.next();
                    classContent[i] = classesItem.elementText("class");
                    Iterator classIterator = classesItem.elementIterator("class");
                    Element classNameElement = (Element) classIterator.next();
                    className[i] = classNameElement.attributeValue("name");
                } // lesen die Elements unter  dem <classes> ein.
            }
        }
        }


    public void createJavaFile(boolean babysteps)  {
        try {
            this.parseXML();
            int x;
            File fileForTest ;
            File fileForClass;
            if(babysteps)
                x=1;
            else x=0;

            String    testDirection=direction.replaceAll("out/","test/");
            fileForTest = new File(testDirection + testName[x] + ".java");
            fileForClass = new File(testDirection + className[x] + ".java");

            if (!fileForTest.exists())
                fileForTest.createNewFile();
            if (!fileForClass.exists())
                fileForClass.createNewFile();
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileForTest));
            bw1.write(this.testContent[x]);
            bw1.close();
           BufferedWriter bw2 = new BufferedWriter(new FileWriter(fileForClass));
            bw2.write(this.classContent[x]);
            bw2.close();
           javaFileForTest=new MyJavaFile(fileForTest.getName()) ;
            javaFileForClass=new MyJavaFile(fileForClass.getName()) ;

        } catch (Exception e) {}

    }
    public MyJavaFile getJavaClassFile(){
        return javaFileForClass;
    }
    public MyJavaFile getJavaTestFile(){
        return javaFileForTest;
    }
    public String getWithBabystepsTestName(){
        return testName[1];
    }
    public String getWithBabystepsClassName(){
        return className[1];
    }
    public String getWithoutBabystepsClassName(){
        return className[0];
    }
    public String getWithoutBabystepsTestName(){
        return testName[0];
    }
}