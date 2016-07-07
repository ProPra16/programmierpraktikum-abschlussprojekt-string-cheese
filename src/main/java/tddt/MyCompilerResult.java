package sample;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerResult;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lqx on 07/07/16.
 */
public class MyCompilerResult implements CompilerResult {
Duration duration;
    /**
     * @return true, if and only if the compilation process produced no error
     *         messages
     */
   public boolean hasCompileErrors(){
       return true;
   }

    /**
     * @return Duration of the compilation process
     */
  public  Duration getCompileDuration(){

        return duration;
    }


    /**
     * @param cu - a compilation unit
     * @return all errors for that compilation unit
     */
 public   Collection<CompileError> getCompilerErrorsForCompilationUnit(CompilationUnit cu){
        Collection<CompileError> errors = new ArrayList<CompileError>();
        return errors;
    }

}