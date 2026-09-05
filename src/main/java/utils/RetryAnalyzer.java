package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private int maxTryCount = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxTryCount) {
            retryCount++;
            return true;
        }
        return false;
    }
}
