package sample;

import vk.core.api.TestFailure;
import vk.core.api.TestResult;
import java.util.ArrayList;
import java.time.Duration;
import java.util.Collection;
import java.util.List;

/**
 * Created by lqx on 07/07/16.
 */
public class MyTestResult  {
    /**
     * @return number of tests that succeeded
     */
    Duration duration;
    int numberOfSuccessfulTests;
    int numberOfFailedTests;
    int numberOfIgnoredTests;
    ArrayList<MyTestFailure> testFailures;
public MyTestResult(Duration duration,int numberOfSuccessfulTests,int numberOfFailedTests,int numberOfIgnoredTests,ArrayList<MyTestFailure> testFailures){
   this.duration =duration;
    this.numberOfFailedTests=numberOfFailedTests;
    this.numberOfSuccessfulTests=numberOfSuccessfulTests;
    this.numberOfIgnoredTests=numberOfIgnoredTests;
    this.testFailures=testFailures;
}
    public   int getNumberOfSuccessfulTests(){
        return numberOfSuccessfulTests;}
    /**
     * @return number of tests that failed
     */
    public  int getNumberOfFailedTests(){
        return numberOfFailedTests;}
    /**
     * @return number of tests that were skipped
     */
    public   int getNumberOfIgnoredTests(){
        return numberOfIgnoredTests;}
    /**
     * @return Duration of the testing process
     */
    public Duration getTestDuration(){
        return duration;}
    /**
     * @return collection of all testing failures
     */

    public String toString(){
       String timing = "Duration:"+String.valueOf(duration.toMillis());
        String number ="\n"+"FailedTests:"+numberOfFailedTests+"\n"+"IgnoredTests:"+numberOfIgnoredTests+"\n"+"SuccessfulTests:"+numberOfSuccessfulTests+"\n";
int a =testFailures.size();
        String failures ="";
        for(int i=0;i<a;i++){
            failures=failures+"\n"+testFailures.get(i).toString();
        }
         return timing+number+failures;
    }
}