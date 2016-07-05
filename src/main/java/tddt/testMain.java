package sample;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler;

/**
 * Created by lqx on 05/07/16.
 */
public class testMain {
    public static void main(String[] args) throws Exception{
        String relativelyPath=System.getProperty("user.dir");
        System.out.println(relativelyPath);
        ClassUnit aa = new ClassUnit("exercises");
        aa.createJavaFile();
        TestUnit ab= new TestUnit("exercises");
        ab.createJavaFile();
       // JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
       // int results= compiler.run(null,null,null,ab.name+".java");
       // System.out.println("Result code: " +results);

    }
}
