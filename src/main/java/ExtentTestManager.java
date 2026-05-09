import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static void setTest(ExtentTest test) {
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void logInfo(String message) {
        if (extentTest.get() != null) {
            extentTest.get().info(message);
        }
    }

    public static void logPass(String message) {
        if (extentTest.get() != null) {
            extentTest.get().pass(message);
        }
    }

    public static void logFail(String message) {
        if (extentTest.get() != null) {
            extentTest.get().fail(message);
        }
    }

    public static void removeTest() {
        extentTest.remove();
    }
}

