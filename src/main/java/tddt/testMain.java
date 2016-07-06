package sample;
import javax.tools.StandardJavaFileManager;
import javax.tools.DiagnosticCollector;
import javax.tools.Diagnostic;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import javax.tools.JavaCompiler.CompilationTask;

public class testMain {
    public static void main(String[] args) throws Exception{
        String relativelyPath=System.getProperty("user.dir");
        System.out.println(relativelyPath);
        //ClassUnit aa = new ClassUnit("exercises");
       // aa.createJavaFile();
        TestUnit ab= new TestUnit("exercises");
        ab.createJavaFile();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics,null,null);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(ab.name+".java"));
        FileOutputStream err = new FileOutputStream("err.txt"); // speichern (Error)Info als ein Datei.
        String fullQuanlifiedFileName =ab.direction+ab.name+".java";
        int compilationResult = compiler.run(null, null, err, fullQuanlifiedFileName);

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
        boolean success = task.call();
        fileManager.close();
        System.out.println("Success: " + success);




        // Iterable it=fileManager.getJavaFileObjects(new File(relativelyPath+"/out/production/project07gruppe/sample",ab.name+".java"));
       // CompilationTask task = compiler.getTask(null,fileManager,diagnostics, Arrays.asList(),null,it);
       // task.call();
      //  fileManager.close();
     //   int results= compiler.run(null, null, null, "-d","./out","./out/production/project07gruppe/sample/"+ab.name+".java");
      //  System.out.println("Result code: " +results);

    }
}
