package sample;

import vk.core.api.TestFailure;
import vk.core.api.TestResult;
import java.util.ArrayList;
import java.time.Duration;
import java.util.Collection;

/**
 * Created by lqx on 07/07/16.
 */
public class MyTestResult implements TestResult {
    /**
     * @return number of tests that succeeded
     */
    Duration duration;
 public   int getNumberOfSuccessfulTests(){
return 0;}
    /**
     * @return number of tests that failed
     */
  public  int getNumberOfFailedTests(){
    return 0;}
    /**
     * @return number of tests that were skipped
     */
    public   int getNumberOfIgnoredTests(){
    return 0;}
    /**
     * @return Duration of the testing process
     */
   public Duration getTestDuration(){
    return duration;}
    /**
     * @return collection of all testing failures
     */
    public Collection<TestFailure> getTestFailures(){
Collection<TestFailure> failures = new ArrayList<TestFailure>();
        return failures;
    }
}