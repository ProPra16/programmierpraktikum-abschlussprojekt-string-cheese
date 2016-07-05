package sample;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.Iterator;

/**
 * Created by lqx on 05/07/16.
 */
public class TestUnit extends Unit {
    public TestUnit(String xmlFileName){
        this.xmlFileName=xmlFileName;
    }
    public void parseXML() throws Exception {
        java.io.File file = new java.io.File(direction + "sample/"+xmlFileName+".xml");
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element rootElement = document.getRootElement();
        System.out.println("ROOT:" + rootElement.getName());
        Iterator exerciseElement = rootElement.elementIterator("exercise");
        // lesen die child node(naemlich <exercise> ein.
        while (exerciseElement.hasNext()) {
            Element childElement = (Element) exerciseElement.next();
            System.out.println("CHILD:" + childElement.getName());
            Iterator testsIterator = childElement.elementIterator("tests");
            while(testsIterator.hasNext()) {

                Element testsItem = (Element)testsIterator.next();
                Iterator classIterator =testsItem.elementIterator("test");
                Element testNameElement =(Element)classIterator.next();
                name = testNameElement.attributeValue("name");
                content = testsItem.elementText("test");
                System.out.println("TestName :"+name);
                System.out.println(content);
            } // lesen die Elements unter dem <tests> ein.

        }
    }
}
