package sample;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.*;
import junit.framework.TestSuite;
import junit.framework.TestResult;
import junit.framework.TestFailure;
import junit.runner.BaseTestRunner;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;

import javax.tools.*;
import java.time.Duration;
import java.time.Instant;
import org.junit.runner.Result;

import static junit.runner.BaseTestRunner.getFilteredTrace;

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
       Path a= compilationUnit.getSourceFile();
       System.out.println("COMPILATIONUNIT:"+a.toString());
    }
    /**
     * Compiles the provided compilation units and runs all tests. This method
     * must be called before getting the results.
     */
    static class MyDiagnosticListener implements DiagnosticListener {
        @Override
        public void report(Diagnostic diagnostic) {
            System.out.println("Code->" + diagnostic.getCode());
            System.out.println("Column Number->" + diagnostic.getColumnNumber());
            System.out.println("End Position->" + diagnostic.getEndPosition());
            System.out.println("Kind->" + diagnostic.getKind());
            System.out.println("Line Number->" + diagnostic.getLineNumber());
            System.out.println("Message->" + diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("Position->" + diagnostic.getPosition());
            System.out.println("Source" + diagnostic.getSource());
            System.out.println("Start Position->" + diagnostic.getStartPosition());
            System.out.println("\n");
        }
    }

    public void compileAndRunTests() {

        /*   try{ if (!compilationUnit.isATest()) {
                String path = this.getClassOutput() + compilationUnit.getSourceFile().toString();
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                MyDiagnosticListener diagnosticListener = new MyDiagnosticListener();
                StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticListener, null, null);
                System.out.println("RUNNNN:" + this.getClassOutput());
                Iterable<? extends JavaFileObject> javaFileObjects = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(path));
                JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticListener, null, null, javaFileObjects);
                int compilationResult = compiler.run(null, null, null, path);
                boolean result = task.call();
                if (compilationResult == 0) {
                    System.out.println("continue:");
                    Runtime run = Runtime.getRuntime();
                    System.out.println(run.freeMemory());
                }
            }
        } catch (Exception e) {
        }*/

        if (compilationUnit.isATest()) {

            File root = new File(direction+"/test");
            URLClassLoader classLoader = null;

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

            System.out.println("TestResult:" + result.wasSuccessful());
            Duration duration = Duration.between(first, second);

            int numberOfFailedTests = result.getFailureCount();
            int numberOfIgnoredTests = result.getIgnoreCount();
            int numberOfSuccessfulTests = result.getRunCount() - numberOfIgnoredTests - numberOfFailedTests;
            testResult = new MyTestResult(duration, numberOfSuccessfulTests, numberOfFailedTests, numberOfIgnoredTests, testFailures);

        }
    }



    /**
     * Get the information related to compilation (i.e., errors, warnings,
     * compiler info).
     *
     * @return result of the compilation process
     * @see CompilerResult
     */
    public MyCompilerResult getCompilerResult() {

        return compilerResult;
    }

    /**
     * Get the information related to testing (i.e., failures and statistics).
     *
     * @return result of all tests
     * @see TestResult
     */
    public MyTestResult getTestResult() {

        return testResult;
    }

    /**
     * @return a set of all names of the compilation units
     */
    public Set<String> getAllCompilationUnitNames() {
        Set<String> set = new HashSet<String>();

        return set;
    }

    /**
     * @param name the name of a compilation unit
     * @return the compilation unit object for the name
     */
    public CompilationUnit getCompilationUnitByName(String name) {

        MyJavaFile javaFile = new MyJavaFile(name);
        CompilationUnit compilationUnit = new CompilationUnit(javaFile.getFileName(), javaFile.getFileContent(), javaFile.isATest());

        return compilationUnit;
    }
}
