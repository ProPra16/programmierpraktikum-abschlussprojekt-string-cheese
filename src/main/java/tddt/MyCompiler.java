package sample;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;

import javax.tools.*;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by lqx on 07/07/16.
 */
public class MyCompiler implements JavaStringCompiler {
    CompilationUnit compilationUnit;
    String name;
    public MyCompiler(String fileName) {
        this.name = fileName;
        this.compilationUnit = getCompilationUnitByName(fileName);
    }
    /**
     * Compiles the provided compilation units and runs all tests. This method
     * must be called before getting the results.
     */
    public void compileAndRunTests() {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        //Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(ab.name + ".java"));
        //FileOutputStream err = new FileOutputStream(ab.name + "err.txt"); // speichern (Error)Info als ein Datei.
    //    String fullQuanlifiedFileName = ab.direction + ab.name + ".java";
      //  int compilationResult = compiler.run(null, null, err, fullQuanlifiedFileName);
       // System.out.println("complilationResult:" + compilationResult);
    }

    /**
     * Get the information related to compilation (i.e., errors, warnings,
     * compiler info).
     *
     * @return result of the compilation process
     * @see CompilerResult
     */
    public MyCompilerResult getCompilerResult() {
        MyCompilerResult compilerResult = new MyCompilerResult();
        return compilerResult;
    }

    /**
     * Get the information related to testing (i.e., failures and statistics).
     *
     * @return result of all tests
     * @see TestResult
     */
    public TestResult getTestResult() {
MyTestResult testResult = new MyTestResult();
        return testResult;
    }

    /**
     * @return a set of all names of the compilation units
     */
    public Set<String> getAllCompilationUnitNames() {
        Set<String> set = new HashSet<String>();

        return set ;
    }

    /**
     * @param name the name of a compilation unit
     * @return the compilation unit object for the name
     */
    public CompilationUnit getCompilationUnitByName(String name){

        ParseUnit parseUnit = new ParseUnit(name);
        CompilationUnit compilationUnit = new CompilationUnit(parseUnit.name, parseUnit.content, parseUnit.isATest);

        return compilationUnit;
    }
}
