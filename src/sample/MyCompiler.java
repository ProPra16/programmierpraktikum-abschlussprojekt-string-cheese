package sample;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import vk.core.api.CompilationUnit;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;
import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.HashSet;
import java.time.Duration;
import java.time.Instant;
/**
 * Created by lqx on 07/07/16.
 */
public class MyCompiler implements JavaStringCompiler {
    String direction0 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    CompilationUnit compilationUnit;
    String name;
    MyCompilerResult compilerResult;
    MyTestResult testResult;
    private StringBuilder outMsg = new StringBuilder();
    private StringBuilder ce = new StringBuilder();
    String error;//Exception of running
    boolean isRunningError;
    private String getClassOutput(){
        //设置class文件的存放位置
        if(System.getProperty("java.class.path").contains("bin")) return "bin/";
        else return "./";
    }
    private class StringSourceJavaObject extends SimpleJavaFileObject {
        private String content = null;
        public StringSourceJavaObject(String name, String content) {
            super(URI.create(name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.content = content;
        }
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return content;
        }
    }

    public MyCompiler(String fileName) {
        this.name = fileName;
        this.compilationUnit = getCompilationUnitByName(fileName);
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

        PrintStream ps = null;
        FileInputStream fis = null;
        BufferedReader br = null;
        //保存标准输出流
        InputStream stdIn = System.in;
        //保存标准输入流
        PrintStream stdOut = System.out;
        try {
            System.out.println("RUNNNN");
            Instant startTime = Instant.now();
            stdOut.println(compilationUnit.getClassName());
for(int i=0;i<5;i++)
    stdOut.println(direction0);
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            MyDiagnosticListener diagnosticListener = new MyDiagnosticListener();

            // DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticListener, null, null);
            String fullQuanlifiedFileName = direction0 + "sample/" + compilationUnit.getClassName() ;
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(fullQuanlifiedFileName));
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File[] { new File(getClassOutput()) }));
            StringSourceJavaObject sourceObject = new MyCompiler.StringSourceJavaObject(name, compilationUnit.getClassContent());
            Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(sourceObject);

            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticListener, null, null, fileObjects);

           // FileOutputStream err = new FileOutputStream(compilationUnit.getClassName() + "_Error.txt"); // speichern (Error)Info als ein Datei.
            int compilationResult = compiler.run(null, null, null, fullQuanlifiedFileName);
            boolean result = task.call();
            Instant endTime = Instant.now();
            int results = compiler.run(null, null, null, fullQuanlifiedFileName);

            stdOut.println("?:" + result + results + "compilationResult:" + compilationResult);
            if (results == 0) {
                System.out.println("continue:");
                Runtime run = Runtime.getRuntime();
                System.out.println(run.freeMemory());
            }
            Duration compileDuration = Duration.between(startTime, endTime);
            compilerResult = new MyCompilerResult(name);
            compilerResult.compileDuration=compileDuration;
            stdOut.println("TIMING:"+compileDuration.toMillis());

        } catch (Exception e) {
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
