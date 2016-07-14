package sample;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

import vk.core.api.CompilationUnit;

import javax.tools.*;
import java.time.Duration;
import java.time.Instant;
import org.junit.runner.Result;

/**
 * Created by lqx on 07/07/16.
 */
public class MyCompiler  {

    String direction = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    CompilationUnit compilationUnit;
    String name;
    MyCompilerResult compilerResult;
    MyTestResult testResult;


    public MyCompiler(String fileName) {
        this.name = fileName;
        this.compilationUnit = getCompilationUnitByName(fileName);
    }

    static class MyDiagnosticListener implements DiagnosticListener {
        @Override
        public void report(Diagnostic diagnostic) {
            StringBuilder errors = new StringBuilder();
            errors.append("\nCode          ->" + diagnostic.getCode());
            errors.append("\nColumn Number ->" + diagnostic.getColumnNumber());
            errors.append("\nEnd Position  ->" + diagnostic.getEndPosition());
            errors.append("\nKind          ->" + diagnostic.getKind());
            errors.append("\nLine Number   ->" + diagnostic.getLineNumber());
            errors.append("\nMessage       ->" + diagnostic.getMessage(Locale.ENGLISH));
            errors.append("\nPosition      ->" + diagnostic.getPosition());
            errors.append("\nSource        ->" + diagnostic.getSource());
            errors.append("\nStart Position->" + diagnostic.getStartPosition());
            File outputErrors = new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"errorTracking.txt");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputErrors));
                bw.write(errors.toString());
                bw.newLine();
                bw.close();
            }catch(Exception e){}
            }
        }


    public void compileAndRunTests() {
           try{ if (!compilationUnit.isATest()) {
               String    testDirection=direction.replaceAll("out/","test/");
               String[] path = new String[2];
               path[1]=testDirection + compilationUnit.getClassName() + ".java";
               path[0]=testDirection + compilationUnit.getClassName() + "Test.java";
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

               MyDiagnosticListener diagnosticListener = new MyDiagnosticListener();
                StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticListener, null, null);
                Iterable<? extends JavaFileObject> javaFileObjects = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(path));
               Iterable<String> options = Arrays.asList("-d", direction+"test/");
               Instant start = Instant.now();
               JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticListener, options, null, javaFileObjects);
               try {
                   File outputErrors = new File(direction+"errorstracking.txt");
                   PrintStream ps=new PrintStream(direction+"errorstracking.txt");
                   System.setOut(ps); } catch (FileNotFoundException e) {}
               boolean success = task.call();
               int result = compiler.run(null,null,null,path);
               System.out.println("RESSSSULT:"+result);
               Instant end = Instant.now();

               compilerResult = new MyCompilerResult(compilationUnit.getClassName(),Duration.between(start, end),!success,null);

               fileManager.close();

           }
        } catch (Exception e) {
        }

        if (compilationUnit.isATest()) {

            File root = new File(direction+"test");
            URLClassLoader classLoader = null;
            System.out.println("TESTCOMPIL RUN:"+direction+"test");
            try {
                classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Class<?> cls = null;
            try {
                cls = Class.forName(compilationUnit.getClassName(), true, classLoader);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Instant first = Instant.now();
            Result result = JUnitCore.runClasses(cls);
            System.out.println(cls);
            Instant second = Instant.now();
            int failureNumber = 0;
            ArrayList<MyTestFailure> testFailures = new ArrayList<MyTestFailure>();
            for (Failure failure : result.getFailures()) {
                failureNumber = failureNumber + 1;
                String methodName = failure.getDescription().toString().replaceAll("\\([^)]+\\)", "");
                String testClassName = compilationUnit.getClassName();
                String message = failure.getMessage().toString();
                MyTestFailure testFailure = new MyTestFailure(testClassName,methodName , message);
                testFailures.add(testFailure);
            }
            Duration duration = Duration.between(first, second);
            int numberOfFailedTests = result.getFailureCount();
            int numberOfIgnoredTests = result.getIgnoreCount();
            int numberOfSuccessfulTests = result.getRunCount() - numberOfIgnoredTests - numberOfFailedTests;
            testResult = new MyTestResult(duration, numberOfSuccessfulTests, numberOfFailedTests, numberOfIgnoredTests, testFailures);
        }
    }

    public MyCompilerResult getCompilerResult() {
        return compilerResult;
    }

    public MyTestResult getTestResult() {
        return testResult;
    }

    public CompilationUnit getCompilationUnitByName(String name) {

        MyJavaFile javaFile = new MyJavaFile(name);
        CompilationUnit compilationUnit = new CompilationUnit(javaFile.getFileName(), javaFile.getFileContent(), javaFile.isATest());

        return compilationUnit;
    }
}
