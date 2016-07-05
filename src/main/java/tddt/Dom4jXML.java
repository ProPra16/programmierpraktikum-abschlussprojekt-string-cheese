package sample;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * Created by lqx on 05/07/16.
 */
public class Dom4jXML {
    public static void main(String[] args) throws Exception{

        String direction = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        java.io.File file= new java.io.File(direction + "sample/excersices.xml");
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element rootElement= document.getRootElement();
        System.out.println("ROOT:"+rootElement.getName());
        Iterator exerciseElement = rootElement.elementIterator("exercise"); // lesen die child node(naemlich <exercise> ein.
        while(exerciseElement.hasNext()){
            Element childElement =(Element)exerciseElement.next();
            System.out.println("CHILD:"+childElement.getName());
            Iterator classesIterator = childElement.elementIterator("classes");
            Iterator testsIterator = childElement.elementIterator("tests");
            while(classesIterator.hasNext()) {
                Element classesItem = (Element)classesIterator.next();
                String classContent = classesItem.elementText("class");
                Iterator classIterator =classesItem.elementIterator("class");
                Element classNameElement =(Element)classIterator.next();
                String className = classNameElement.attributeValue("name");
                System.out.println("ClassName :"+className);
                System.out.println(classContent);
            } // lesen die Elements unter  dem <classes> ein.
            while(testsIterator.hasNext()) {

                Element testsItem = (Element)testsIterator.next();
                Iterator classIterator =testsItem.elementIterator("test");
                Element testNameElement =(Element)classIterator.next();
                String testName = testNameElement.attributeValue("name");

                String testContent = testsItem.elementText("test");
                System.out.println("TestName :"+testName);
                System.out.println(testContent);
            } // lesen die Elements unter dem <tests> ein.
        }

    }
}
