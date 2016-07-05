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
            Element recordElement =(Element)exerciseElement.next();
            System.out.println("record:"+recordElement.getName());
            Iterator classesElement = recordElement.elementIterator("classes");
            Iterator testsElement = recordElement.elementIterator("tests");
            while(classesElement.hasNext()) {

                Element classesItem = (Element)classesElement.next();
                String classContent = classesItem.elementText("class");
                System.out.println(classContent);
            } // lesen die Elements unter  dem <classes> ein.
            while(testsElement.hasNext()) {
                System.out.println("tests");
                Element tableItem = (Element)testsElement.next();
                String testContent = tableItem.elementText("test");
                System.out.println(testContent);
            } // lesen die Elements unter dem <tests> ein.
        }

    }
}
