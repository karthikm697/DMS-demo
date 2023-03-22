package dmsdemo.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage {
	
	WebDriver driver;
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
		
	}
	
	@FindBy(xpath="//input[@type='email']")
	WebElement emailfield;
	@FindBy(xpath="//input[@type='password']")
	WebElement passwordfield;
	@FindBy(xpath = "//button[contains(text(),'Login')]")
	WebElement login;
	
	@FindBy(xpath="//small[contains(text(),'Forgot')]")
	WebElement forgotpass;
	@FindBy(xpath="//h2[contains(text(),'Dashboard')]")
	WebElement homepage;
	@FindBy(xpath="//img[@title='User profile']")
	WebElement profile;
	@FindBy(xpath="//div[contains(text(),'Logout')]")
	WebElement logoutoption;
	
	public void login(String usern,String pass) throws InterruptedException
	{
		
		emailfield.sendKeys(usern);
		passwordfield.sendKeys(pass);
		login.click();
		Thread.sleep(3000);
	}
	public String verifyHomepage()
	{
		String text=homepage.getText();
		return text;
	}
	public void profileIcon()
	{
		
		profile.click();
	}
	public void logout()
	{
		logoutoption.click();
	}
	
}
