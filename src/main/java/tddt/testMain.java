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
    public static void main(String[] args) throws Exception{
        String relativelyPath=System.getProperty("user.dir");
        System.out.println(relativelyPath);

        ParseUnit aa = new ParseUnit("exercises","class");
        aa.createJavaFile();
        ParseUnit ab= new ParseUnit("exercises","test");
        ab.createJavaFile();

       CompilationUnit compilationUnit = new CompilationUnit(ab.name,ab.content, ab.isATest);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics,null,null);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(ab.name+".java"));
        FileOutputStream err = new FileOutputStream(ab.name+"err.txt"); // speichern (Error)Info als ein Datei.
        String fullQuanlifiedFileName =ab.direction+ab.name+".java";
        int compilationResult = compiler.run(null, null, err, fullQuanlifiedFileName);
System.out.println("complilationResult:"+compilationResult);
    //    JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
    //    boolean success = task.call();
        fileManager.close();
     //   System.out.println("Success: " + success);




        // Iterable it=fileManager.getJavaFileObjects(new File(relativelyPath+"/out/production/project07gruppe/sample",ab.name+".java"));
       // CompilationTask task = compiler.getTask(null,fileManager,diagnostics, Arrays.asList(),null,it);
       // task.call();
      //  fileManager.close();
     //   int results= compiler.run(null, null, null, "-d","./out","./out/production/project07gruppe/sample/"+ab.name+".java");
      //  System.out.println("Result code: " +results);

    }
}
