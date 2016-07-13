package sample;

import vk.core.api.TestFailure;

/**
 * Created by lqx on 13/07/16.
 */
public class MyTestFailure  {
  private   String testClassName;
    private  String methodName;
    private  String message;
  //  String exceptionStackTrace;
    public MyTestFailure(String testClassName,String methodName,String message){
        this.testClassName=testClassName;
        this.methodName=methodName;
        this.message=message;
    }

    public String getTestClassName() {
        return testClassName;
    }


    public String getMethodName() {
        return methodName;
    }


    public String getMessage() {
        return message;
    }
    @Override
    public String toString(){
        String all ="FAILURE-INFORMATION of class "+getTestClassName()+":\n In the method '"+getMethodName()+"': \n"+getMessage()+"\n";
        return all;
    }
}
