import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

protected WebDriver driver;


// You can add common setup and teardown methods here if needed

   //setup method to initialize WebDriver
    @BeforeMethod
    public void setUp(

    ) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://vinothqaacademy.com/demo-site/");
    }

    //teardown method to quit WebDriver
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }





}
