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
    int NumberOfSuccessfulTests;
    int NumberOfFailedTests;
    int NumberOfIgnoredTests;
    ArrayList<TestFailure> testFailures;

    public   int getNumberOfSuccessfulTests(){
        return NumberOfSuccessfulTests;}
    /**
     * @return number of tests that failed
     */
    public  int getNumberOfFailedTests(){
        return NumberOfFailedTests;}
    /**
     * @return number of tests that were skipped
     */
    public   int getNumberOfIgnoredTests(){
        return NumberOfIgnoredTests;}
    /**
     * @return Duration of the testing process
     */
    public Duration getTestDuration(){
        return duration;}
    /**
     * @return collection of all testing failures
     */
    public Collection<TestFailure> getTestFailures(){
Collection<TestFailure> testFailures = new ArrayList<TestFailure>();
        return testFailures;
    }
}