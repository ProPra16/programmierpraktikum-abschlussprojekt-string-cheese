package sample;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.Iterator;

/** Through this class, the programm can parse the "catalog" file( example here: excersices.xml) to .java file.
 *
 *
 */

public class ParseUnit {
    String parseError="";
  //  boolean success;
    String direction = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String xmlFileName;
    String[] testName=new String[2];
    String[] className=new String[2];
    String[] testContent=new String[2];
    String[] classContent=new String[2];
    MyJavaFile javaFileForTest;
    MyJavaFile javaFileForClass;
   private int babaystepsTime;
    public ParseUnit(String xmlFileName, boolean babysteps) {
       this.xmlFileName=xmlFileName;
        createJavaFile(babysteps);
        System.out.print(direction);
    }

    public void parseXML() throws Exception  {
        System.out.println(parseError);
        Document document=null;
        java.io.File file = new java.io.File(direction + "sample/" + xmlFileName + ".xml");
            SAXReader reader = new SAXReader();
            try {
               document = reader.read(file);
            }catch (Exception e){parseError=" This .xml file can not be read, because it isn't with a right format.";}
            Element rootElement = document.getRootElement();
            Iterator exerciseElement = rootElement.elementIterator("exercise");
            int i;
            if(!exerciseElement.hasNext())
            {   if(parseError==null)
                parseError=" There is not a label named exercise.\n";}
            // lesen die child node(naemlich <exercise> ein.
            while (exerciseElement.hasNext()) {
                Element childElement = (Element) exerciseElement.next();
                Iterator configIterator = childElement.elementIterator("config");
                if(!configIterator.hasNext())
                {   parseError=parseError+" There is not a label named config.\n";}
                while (configIterator.hasNext()) {
                    Element configItem = (Element) configIterator.next();
                    Iterator babystepsIterator = configItem.elementIterator("babysteps");
                    if(!babystepsIterator.hasNext())
                    {   parseError=parseError+" There is not a label named babysteps.\n";}
                    Element babystepsElement = (Element) babystepsIterator.next();
                    if (babystepsElement.attributeValue("value").equals("True")) {
                        i = 1;
                        String stringTime = babystepsElement.attributeValue("time");
                        String[] partTimes = stringTime.split(":");
                        babaystepsTime = Integer.parseInt(partTimes[0]) * 60 + Integer.parseInt(partTimes[1]);
                    }
                    // lesen die babysteps begrenzende Zeit ein.
                    else
                        i = 0;
                    Iterator testsIterator = childElement.elementIterator("tests");
                    Iterator classesIterator = childElement.elementIterator("classes");
                    if(!testsIterator.hasNext())
                    { parseError=parseError+" There is not a label named tests.\n";}
                    if(!classesIterator.hasNext())
                    {   parseError=parseError+" There is not a label named classes.\n";}
                    while (testsIterator.hasNext()) {
                        Element testsItem = (Element) testsIterator.next();
                        Iterator classIterator = testsItem.elementIterator("test");
                        if(!classIterator.hasNext())
                        { parseError=parseError+" There is not a label named class.\n";}
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
            System.out.println(parseError);
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
        } catch (Exception e) {
            AlertBox parseFail = new AlertBox(400,250);
            parseFail.display("Parse Fail","\n\n The format of this file(.xml) is illegal.\n Please read the user guide to create correct format of the file.\n\n Details:\n"+parseError);
            xmlFileName=null;
        }
    }

    public String getXmlFileName(){
        return xmlFileName;
    }
    public String getWithBabystepsTestName(){return testName[1];}
    public String getWithBabystepsClassName(){
        return className[1];
    }
    public String getWithoutBabystepsClassName(){return className[0];}
    public String getWithoutBabystepsTestName(){
        return testName[0];
    }
    public int getBabaystepsTime(){return babaystepsTime;}
}