import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportListener implements ITestListener {
    protected static final Logger log = LoggerFactory.getLogger(ExtentReportListener.class);

    public ExtentReports extent;
    public ExtentSparkReporter sparkReporter;
    public ExtentTest test;
    private static WebDriver driver;

    @Override
    public void onStart(ITestContext context) {
        log.info("============ Test Suite Started ============");
        
        // Create test-output directory if it doesn't exist
        String testOutputDir = System.getProperty("user.dir") + "/test-output";
        String screenshotsDir = testOutputDir + "/screenshots";
        new File(testOutputDir).mkdirs();
        new File(screenshotsDir).mkdirs();
        
        // Initialize Extent Report
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = testOutputDir + "/ExtentReport_" + timeStamp + ".html";
        
        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("TestNG Extent Report");
        sparkReporter.config().setReportName("Automation Test Results - " + timeStamp);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        
        log.info("Report generated at: " + reportPath);
        log.info("Screenshots directory: " + screenshotsDir);
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("============ Test: " + result.getMethod().getMethodName() + " Started ============");
        test = extent.createTest(result.getMethod().getMethodName());
        test.info("Test Started: " + result.getMethod().getMethodName());
        ExtentTestManager.setTest(test);
        
        // Store driver instance for screenshot capture
        try {
            Object testInstance = result.getInstance();
            java.lang.reflect.Field driverField = testInstance.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            driver = (WebDriver) driverField.get(testInstance);
            log.info("[Driver] Stored WebDriver instance for screenshot capture");
        } catch (Exception e) {
            log.warn("[Driver] Could not store driver instance: " + e.getMessage());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✓ Test PASSED: " + result.getMethod().getMethodName());
        test.log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("✗ Test FAILED: " + result.getMethod().getMethodName());
        test.log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
        test.log(Status.FAIL, result.getThrowable());
        
        // Capture screenshot on failure
        if (driver != null) {
            String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
            if (screenshotPath != null && !screenshotPath.isEmpty()) {
                // Convert to relative path for report
                String relativePath = "./screenshots/" + new File(screenshotPath).getName();
                test.addScreenCaptureFromPath(relativePath);
                test.log(Status.INFO, "Screenshot attached on failure");
                log.info("Screenshot attached to report: " + relativePath);
            }
        } else {
            log.warn("[Screenshot] WebDriver is null, cannot capture screenshot");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⊘ Test SKIPPED: " + result.getMethod().getMethodName());
        test.log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.info("Test Failed but within success percentage: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("============ Test Suite Finished ============");
        ExtentTestManager.removeTest();
        extent.flush();
        log.info("Extent Report generated successfully!");
    }

    // Screenshot capture method
    private String captureScreenshot(String testName) {
        try {
            if (driver != null) {
                // Create directory structure
                String screenshotsDir = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "screenshots";
                File screenshotsDirFile = new File(screenshotsDir);
                
                if (!screenshotsDirFile.exists()) {
                    boolean created = screenshotsDirFile.mkdirs();
                    if (created) {
                        log.info("[Directory] Created: " + screenshotsDir);
                    }
                }
                
                // Capture screenshot
                TakesScreenshot ts = (TakesScreenshot) driver;
                File source = ts.getScreenshotAs(OutputType.FILE);
                
                String fileName = testName + "_" + System.currentTimeMillis() + ".png";
                String destinationPath = screenshotsDir + File.separator + fileName;
                File destinationFile = new File(destinationPath);
                
                // Copy screenshot file
                org.apache.commons.io.FileUtils.copyFile(source, destinationFile);

                log.info("[Screenshot] Captured and saved: {}", destinationPath);
                return destinationPath;
            } else {
                log.warn("[Screenshot] WebDriver is null, cannot capture screenshot");
                return null;
            }
        } catch (Exception e) {
            log.error("[Screenshot] Failed to capture: {}", e.getMessage(), e);
            return null;
        }
    }
}

