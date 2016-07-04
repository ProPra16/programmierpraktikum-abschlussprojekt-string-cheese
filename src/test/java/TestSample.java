import static org.junit.Assert.*;
import org.junit.*;
// Input Methode Test
public class TestSample {

    @Test
    public void testFileChooser() throws Exception{
FileChooser fileChooser= new FileChooser();
fileChooser.setTitle("Katalog");
assertEquals("Katalog",fileChooser.evaluate());
    }
    
    @Test
    public void testDocumentOfClassName() throws Exception{
Document document = db.parse(new File(excercises.xml));
String className = document.getClassName("RomanNumberConverter");
assertEquals("RomanNumberConverter",document.className);
    }
    
    @Test
    public void testDocumentOfTestName() throws Exception{
Document document = db.parse(new File(excercises.xml));
String testName = document.getTestName("RomanNumberConverterTest");
assertEquals("RomanNumberConverterTest",document.testName);
    }

}
