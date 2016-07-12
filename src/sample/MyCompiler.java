package sample;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.runner.JUnitCore;
import vk.core.api.CompilationUnit;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;
import javax.tools.*;
import java.util.Locale;
import java.util.Set;
import java.util.HashSet;
import java.time.Duration;
import java.time.Instant;
import org.junit.runner.Result;
/**
 * Created by lqx on 07/07/16.
 */
public class MyCompiler implements JavaStringCompiler {

    String direction = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    CompilationUnit compilationUnit;
    String name;
    MyCompilerResult compilerResult;
    MyTestResult testResult;
    private String getClassOutput(){
        String    testDirection=direction.replaceAll("out/","test/");
        return testDirection;
    }
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
        try {
            if (!compilationUnit.isATest()) {
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
            }catch(Exception e){
            }

        if (compilationUnit.isATest())
        {
            Class c= this.compilationUnit.getClassName().getClass();
            Instant first = Instant.now();
            Result result = JUnitCore.runClasses(c);
            Instant second = Instant.now();
            System.out.println("TestResult:"+result.wasSuccessful());
            Duration duration = Duration.between(first, second);
            System.out.println("Duration:"+duration.toMillis()+"ms");
            int NumberOfFailedTests=result.getFailureCount();
            int NumberOfIgnoredTests= result.getIgnoreCount();
            int NumberOfSuccessfulTests=result.getRunCount()-NumberOfIgnoredTests-NumberOfFailedTests;
           testResult = new MyTestResult(duration,NumberOfFailedTests,NumberOfSuccessfulTests,NumberOfIgnoredTests);
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
    public TestResult getTestResult() {

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
