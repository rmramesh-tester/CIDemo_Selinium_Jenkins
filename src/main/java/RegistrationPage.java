import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {

    WebDriver driver;


    public RegistrationPage(WebDriver driver) {
           super(driver);
           this.driver = driver;
    }

    //locators

    By firstName= By.xpath("//input[@id=\"vfb-5\"]");
    By lastName=By.xpath("//input[@id=\"vfb-7\"]");
    By gender =By.xpath("//label[normalize-space()=\"Female\"]");
    By course=By.xpath("//input[@id=\"vfb-20-1\"]");
    By address=By.xpath("//input[@id=\"vfb-13-address\"]");
    By streetAddress =By.xpath("//input[@id=\"vfb-13-address-2\"]");
    By city=By.cssSelector("#vfb-13-city");
    By zip=By.cssSelector("#vfb-13-zip");
    By state=By.cssSelector("#vfb-13-state");
    By email =By.xpath("//input[@id=\"vfxx-14\"]");
    By mobile=By.xpath("//input[@id=\"vfb-19\"]");
    By capcha=By.xpath("//input[@id=\"vfb-3\"]");
    By submit=By.xpath("//input[@id=\"vfb-4\"]");

    //Actions

    public void enteruserName(String first){
        highlightElement(firstName);
        sendKeys(firstName, first);
    }

    public void enterlastName(String last){
        highlightElement(lastName);
       sendKeys(lastName, last);
    }

    public void selectGender() {
       click(gender);
    }

    public void selectCourse() {
        click(course);
    }

    public void enterAddress(String add){
        highlightElementBackground(address);
       sendKeys(address, add);
    }

    public void enterStreetAddress(String street){
        sendKeys(streetAddress, street);
    }

    public void enterCity(String cityName){
        sendKeys(city, cityName);
    }

    public void enterZip(String zipCode){
       sendKeys(zip, zipCode);
    }

    public void enterState(String stateName){
        sendKeys(state, stateName);
    }

    public void enterEmail(String emailId){
       sendKeys(email, emailId);
    }

    public void enterMobile(String mobileNumber){
        sendKeys(mobile, mobileNumber);
    }

    public void enterCapcha(String capchaValue){
        scrollToBottom();
        sendKeys(capcha, capchaValue);
    }

    public void clickSubmit(){
       click(submit);
    }

    public void registerUser(String first, String last, String add, String street, String cityName, String zipCode, String stateName, String emailId, String mobileNumber, String cap
    ) throws InterruptedException {
            enteruserName(first);
            enterlastName(last);
            selectGender();
            selectCourse();
            enterAddress(add);
            enterStreetAddress(street);
            enterCity(cityName);
            enterZip(zipCode);
//            enterState(stateName);
//            enterEmail(emailId);
//            enterMobile(mobileNumber);
//            enterCapcha(cap);
//            clickSubmit();
        }


}
