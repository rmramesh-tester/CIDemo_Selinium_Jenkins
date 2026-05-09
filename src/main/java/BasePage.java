import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasePage {
    protected static final Logger log = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;
    JavascriptExecutor js;
    Actions actions;
    Select select;

    //constructor
    public BasePage(WebDriver driver) {
            // Initialize the page elements
            this.driver=driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            this.js = (JavascriptExecutor) driver;
            this.actions = new Actions(driver);
            this.select=new Select(driver.findElement(By.tagName("select"))); // Default select initialization, can be overridden in child classes
        }

    // =========================
    // 🔹 BASIC ELEMENT ACTIONS
    // =========================

    public void click(By locator) {
        waitForElementVisible(locator);
        driver.findElement(locator).click();
        String logMessage = "[INFO] Clicked on element: " + locator;
        log.info(logMessage);
        ExtentTestManager.logInfo(logMessage);
    }

    public void sendKeys(By locator, String text) {
        waitForElementVisible(locator);
        driver.findElement(locator).sendKeys(text);
        String logMessage = "[INFO] Entered '" + text + "' into element: " + locator;
        log.info(logMessage);
        ExtentTestManager.logInfo(logMessage);
    }

    public void clear(By locator) {
        driver.findElement(locator).clear();
        String logMessage = "[INFO] Cleared element: " + locator;
        log.info(logMessage);
        ExtentTestManager.logInfo(logMessage);
    }

    public String getText(By locator) {
        String text = driver.findElement(locator).getText();
        String logMessage = "[INFO] Retrieved text '" + text + "' from element: " + locator;
        log.info(logMessage);
        ExtentTestManager.logInfo(logMessage);
        return text;
    }

    public String getAttribute(By locator, String attr) {
        String attributeValue = driver.findElement(locator).getAttribute(attr);
        String logMessage = "[INFO] Retrieved attribute '" + attr + "' with value '" + attributeValue + "' from element: " + locator;
        log.info(logMessage);
        ExtentTestManager.logInfo(logMessage);
        return attributeValue;
    }

    public boolean isDisplayed(By locator) {
        boolean displayed = driver.findElement(locator).isDisplayed();
        String logMessage = "[INFO] Element " + locator + " is displayed: " + displayed;
        log.info(logMessage);
        ExtentTestManager.logInfo(logMessage);
        return displayed;
    }

    public boolean isEnabled(By locator) {
        boolean enabled = driver.findElement(locator).isEnabled();
        String logMessage = "[INFO] Element " + locator + " is enabled: " + enabled;
        log.info(logMessage);
        ExtentTestManager.logInfo(logMessage);
        return enabled;
    }

    public boolean isSelected(By locator) {
        boolean selected = driver.findElement(locator).isSelected();
        String logMessage = "[INFO] Element " + locator + " is selected: " + selected;
        log.info(logMessage);
        ExtentTestManager.logInfo(logMessage);
        return selected;
    }

    public List<WebElement> findElements(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        log.info("[INFO] Found {} elements with locator: {}", elements.size(), locator);
        return elements;
    }

    // =========================
    // 🔹 WAIT UTILITIES
    // =========================


    public void waitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForPresence(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }


    public void waitForTitle(String title) {
        wait.until(ExpectedConditions.titleContains(title));
    }

    public void waitForUrl(String url) {
        wait.until(ExpectedConditions.urlContains(url));
    }

    public void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForText(By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // =========================
    // 🔹 DROPDOWN
    // =========================

    public void selectByVisibleText(By locator, String text) {
        log.info("[selectByVisibleText] Selecting '{}' from dropdown: {}", text, locator);
        new Select(driver.findElement(locator)).selectByVisibleText(text);
    }

    public void selectByValue(By locator, String value) {
        log.info("[selectByValue] Selecting value '{}' from dropdown: {}", value, locator);
        new Select(driver.findElement(locator)).selectByValue(value);
    }

    public void selectByIndex(By locator, int index) {
        log.info("[selectByIndex] Selecting index '{}' from dropdown: {}", index, locator);
        new Select(driver.findElement(locator)).selectByIndex(index);
    }

    public String getSelectedText(By locator) {
        String selectedText = new Select(driver.findElement(locator)).getFirstSelectedOption().getText();
        log.info("[getSelectedText] Retrieved selected text '{}' from dropdown: {}", selectedText, locator);
        return selectedText;
    }

    // =========================
    // 🔹 MOUSE ACTIONS
    // =========================

    public void hover(By locator) {
        actions.moveToElement(driver.findElement(locator)).perform();
    }

    public void doubleClick(By locator) {
        actions.doubleClick(driver.findElement(locator)).perform();
    }

    public void rightClick(By locator) {
        actions.contextClick(driver.findElement(locator)).perform();
    }

    public void dragAndDrop(By source, By target) {
        actions.dragAndDrop(
                driver.findElement(source),
                driver.findElement(target)).perform();
    }

    // =========================
    // 🔹 KEYBOARD ACTIONS
    // =========================

//    public void pressEnter(By locator) {
//        driver.findElement(locator).sendKeys(Keys.ENTER);
//    }
//
//    public void pressTab(By locator) {
//        driver.findElement(locator).sendKeys(Keys.TAB);
//    }
//
//    public void pressEscape() {
//        actions.sendKeys(Keys.ESCAPE).perform();
//    }

    // =========================
    // 🔹 JAVASCRIPT UTILITIES
    // =========================

    public void jsClick(By locator) {
        js.executeScript("arguments[0].click();",locator);
    }

    public void jsSendKeys(By locator, String value) {
        js.executeScript("arguments[0].value='" + value + "';", locator);
    }

    public void scrollToElement(By locator) {
        js.executeScript("arguments[0].scrollIntoView(true);",locator);
    }

    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    //highlight element using JS
    public void highlightElement(By locator) {
        WebElement element = driver.findElement(locator);
        String originalStyle = element.getAttribute("style");
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: 2px solid red; border-style: dashed;");
        // Revert back to original style after a short delay
        try {            Thread.sleep(500);
        } catch (InterruptedException e) {            e.printStackTrace();
        }
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, originalStyle);
    }

    //highlight elmment backround color using JS
    public void highlightElementBackground(By locator) {
        WebElement element = driver.findElement(locator);
        String originalStyle = element.getAttribute("style");
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: yellow;");
        // Revert back to original style after a short delay
        try {            Thread.sleep(500);
        } catch (InterruptedException e) {            e.printStackTrace();
        }
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, originalStyle);
    }

    public void scrollByPixels(int x, int y) {
        js.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }

    public String getPageTitleByJS() {
        return js.executeScript("return document.title;").toString();
    }

    public String getPageURLByJS() {
        return js.executeScript("return document.URL;").toString();
    }

    // =========================
    // 🔹 ALERT HANDLING
    // =========================

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    public void sendAlertText(String text) {
        driver.switchTo().alert().sendKeys(text);
    }

    // =========================
    // 🔹 FRAME HANDLING
    // =========================

    public void switchToFrame(By locator) {
        driver.switchTo().frame(driver.findElement(locator));
    }

    public void switchToFrame(int index) {
        driver.switchTo().frame(index);
    }

    public void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    // =========================
    // 🔹 WINDOW HANDLING
    // =========================

    public void switchToWindow(String handle) {
        driver.switchTo().window(handle);
    }

    public String getCurrentWindow() {
        return driver.getWindowHandle();
    }

    public void switchToNewWindow() {
        for (String win : driver.getWindowHandles()) {
            driver.switchTo().window(win);
        }
    }

    // =========================
    // 🔹 NAVIGATION
    // =========================

    public void openUrl(String url) {
        driver.get(url);
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public void back() {
        driver.navigate().back();
    }

    public void forward() {
        driver.navigate().forward();
    }

    // =========================
    // 🔹 SCREEN & BROWSER
    // =========================

    public void maximize() {
        driver.manage().window().maximize();
    }

    public void minimize() {
        driver.manage().window().minimize();
    }

    public void setImplicitWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    // =========================
    // 🔹 CHECKBOX / RADIO
    // =========================

    public void check(By locator) {
        if (!driver.findElement(locator).isSelected()) {
            click(locator);
        }
    }

    public void uncheck(By locator) {
        if (driver.findElement(locator).isSelected()) {
            click(locator);
        }
    }

    // =========================
    // 🔹 UTILITY METHODS
    // =========================

    private String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    // Helper method to check if the current URL matches the expected URL
//    protected boolean isCurrentUrl(String expectedUrl) {
//        return wait.until(ExpectedConditions.urlToBe(expectedUrl));
//    }
//
//    // Helper method to check if the page title matches the expected title
//    protected boolean isPageTitle(String expectedTitle) {
//        return wait.until(ExpectedConditions.titleIs(expectedTitle));
//    }
//
//    // Helper method to check if an element contains specific text
//    protected boolean doesElementContainText(By locator, String expectedText) {
//        WebElement element = waitForElementToBePresent(locator);
//        String actualText = element.getText();
//        return actualText.contains(expectedText);
//    }


    // Reusable assertion methods
//    protected void assertCurrentUrl(String expectedUrl, String testDescription) {
//        String actualUrl = driver.getCurrentUrl();
//        Assert.assertEquals(actualUrl, expectedUrl, "User was not redirected to the expected URL after " + testDescription);
//        System.out.println("✓ " + testDescription + " - User was redirected to the expected URL after " + testDescription.toLowerCase());
//    }
//
//    protected void assertElementText(By locator, String expectedText, String testDescription) {
//        WebElement element = waitForElementToBePresent(locator);
//        String actualText = element.getText();
//        Assert.assertEquals(actualText, expectedText, "Text did not match expected value for " + testDescription);
//        System.out.println("✓ " + testDescription + " - Text is displayed correctly.");
//    }
//
//    protected void assertElementText(WebElement element, String expectedText, String testDescription) {
//        waitForElementToBeVisible((By) element);
//        String actualText = element.getText();
//        Assert.assertEquals(actualText, expectedText, "Text did not match expected value for " + testDescription);
//        System.out.println("✓ " + testDescription + " - Text is displayed correctly.");
//    }
}
