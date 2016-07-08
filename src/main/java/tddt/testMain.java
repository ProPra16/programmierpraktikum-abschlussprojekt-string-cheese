package sample;
import javax.tools.StandardJavaFileManager;
import javax.tools.DiagnosticCollector;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import java.io.FileOutputStream;
import java.util.Arrays;

import vk.core.api.CompilationUnit;

/**
 * Created by lqx on 05/07/16.
 */
public class testMain {
    public static void main(String[] args) throws Exception {
        String relativelyPath = System.getProperty("user.dir");
        System.out.println(relativelyPath);
      //  chooseACatalog();
       MyJavaFile onlyToRomanTest = new MyJavaFile("RomanNumbersTest");
        MyCompiler myCompiler = new MyCompiler(onlyToRomanTest.getFileName());
        myCompiler.compileAndRunTests();
    }

        public static void chooseACatalog(){
        ParseUnit aa = new ParseUnit("exercises","class");
        ParseUnit ab= new ParseUnit("exercises","test");
    }



    }
