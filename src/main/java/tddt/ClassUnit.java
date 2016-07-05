package sample;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.Iterator;

/**
 * Created by lqx on 05/07/16.
 */
public class ClassUnit extends Unit{

public ClassUnit(String xmlFileName){
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