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
public class MyCompilerResult {
    String name;
    Duration compileDuration;
    boolean hasCompileErrors;
    String errors;

    public MyCompilerResult(String name,Duration duration,boolean hasCompileErrors,String errors){

        this.name=name;
        this.compileDuration=duration;
        this.hasCompileErrors=hasCompileErrors;
        this.errors=errors;
    }
    public boolean hasCompileErrors(){
       return hasCompileErrors;
   }

  public  Duration getCompileDuration(){


        return compileDuration;
    }


 public  String getCompilerErrorsForCompilationUnit(CompilationUnit cu){

        return errors;
    }

}