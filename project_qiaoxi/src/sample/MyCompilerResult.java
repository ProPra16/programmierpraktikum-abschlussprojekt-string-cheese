package sample;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerResult;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lqx on 07/07/16.
 */
public class MyCompilerResult {
    String name;
   Duration duration;
    int compileErrors;
    File errors;

    public MyCompilerResult(String name, int compileErrors, File errors, Duration duration){

        this.name=name;
        this.duration=duration;
        this.compileErrors=compileErrors;
        this.errors=errors;
    }

  public String toString(){
      String stringErrors = "There is no compiling error.\n";
      try {
          if (errors.isFile() && errors.exists()) {
              InputStreamReader read = new InputStreamReader(new FileInputStream(errors));
              BufferedReader bufferedReader = new BufferedReader(read);
              StringBuilder stringBuilder = new StringBuilder();
              String line = null;
              while ((line = bufferedReader.readLine()) != null) {
                  stringBuilder.append(line);
                  stringBuilder.append("\n");
              }
              bufferedReader.close();
              stringErrors = stringBuilder.toString();
              read.close();
          }
      } catch (Exception e) {}
        return stringErrors;
    }

}