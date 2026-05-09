import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest {

     @Test
    public void testRegistration() throws InterruptedException {
         //fake data for registration
         Faker faker = new Faker();

         RegistrationPage registrationPage = new RegistrationPage(driver);
         registrationPage.registerUser(faker.name().firstName(), faker.name().lastName(), faker.address().streetAddress(), faker.address().buildingNumber(), faker.address().state(), faker.address().zipCode(), faker.address().stateAbbr(),faker.internet().emailAddress(),"9874561231", "40");
         // Add assertions to verify successful registration if needed
         Thread.sleep(2000);
         //assert sucessful registration by checking for a success message or redirection
         //registrationPage.assertCurrentUrl("https://vinothqaacademy.com/transaction-id/", "User registration");
         //registrationPage.assertElementText(By.xpath("//div[@id='messageContainer']"), "Registration Form is Successfully Submitted", "User registration");
     }
}