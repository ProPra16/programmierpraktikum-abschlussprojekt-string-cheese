package sample;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerResult;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lqx on 07/07/16.
 */
public class MyCompilerResult implements CompilerResult {
    String name;
    Duration compileDuration;
    boolean hasCompileErrors;
    Collection<CompileError> errors;
    /**
     * @return true, if and only if the compilation process produced no error
     *         messages
     */
    public MyCompilerResult(String name){
        this.name=name;
    }
   public boolean hasCompileErrors(){
       return hasCompileErrors;
   }

    /**
     * @return Duration of the compilation process
     */
  public  Duration getCompileDuration(){


        return compileDuration;
    }


    /**
     * @param cu - a compilation unit
     * @return all errors for that compilation unit
     */
 public   Collection<CompileError> getCompilerErrorsForCompilationUnit(CompilationUnit cu){

        return errors;
    }

}