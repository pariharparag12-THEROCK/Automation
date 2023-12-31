package testPackage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import basePackage.Base;
import pomPackage.HomePageOrange;
import pomPackage.LoginPageOrange;



public class VerifyOrangeTestClass1 extends Base {

	private WebDriver driver;
	
	private LoginPageOrange loginPageOrange;
	private HomePageOrange homePageOrange;
	
	private SoftAssert softassert;
	
	
	
	@BeforeTest
	@Parameters ("browser1")
	public void launchBrowser(String Browser) {
		
		if(Browser.equals("Chrome")) {
			driver = openChromeBrowser();
		}
		
		/*if(Browser.equals("Firefox")) {
			driver = openFirefoxBrowser();
			
			
			helloo parag how arwe u git check
		}*/
	}
	
	
	@BeforeClass
	public void launchOrangeHRMApplication() {
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://opensource-demo.orangehrmlive.com");
		
		System.out.println("Application Launched Successfully");

		loginPageOrange = new LoginPageOrange(driver);
		homePageOrange = new HomePageOrange(driver);
		
		softassert = new SoftAssert();
	}
	
	
	@BeforeMethod
	public void LoginOrangeHRM() throws InterruptedException {
		
		loginPageOrange.sendUsername();
		loginPageOrange.sendPassword();
		loginPageOrange.clickOnLoginButton();
		
		System.out.println("User Clicked on login button");
	}
	
	
	@Test (priority = -1)
	public void verifyAdminOption() throws InterruptedException {
	
		String actUrl1 = driver.getCurrentUrl();
		Assert.assertEquals(actUrl1, "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
		
		System.out.println("Application Logged-In Successfully" +"\n");
		
		homePageOrange.clickOnAdminOption();
		
		String actUrl2 = driver.getCurrentUrl();
		
		boolean result = actUrl2.equalsIgnoreCase("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers");
		
		softassert.assertTrue(result);
	
		String actUrl3 = driver.getTitle();
		
		softassert.assertEquals(actUrl3, "OrangeHRM");
		
		System.out.println("User clicked on AdminOption Successfully" + "\n" +"Tittle : " + actUrl3 + "\n" +"Url : " + actUrl2 +"\n" );
		
		softassert.assertAll();
	}
	
	
	@Test (priority = 2)
	public void verifyAboutOption() throws InterruptedException {
		
		homePageOrange.clickOnAccountOptionsDropDown();
		String companyNameValue2 = homePageOrange.getOptionsONAccountOptionsDropDown();
		softassert.assertEquals(companyNameValue2, "OrangeHRM");
		//softassert.assertTrue(actRes);
		softassert.assertAll();
		System.out.println("Assertion pass verifyAboutOption");
		
		homePageOrange.clickOnAboutCrossIcon();
	
	}
	
	
	
	
	
	@AfterMethod
	public void logoutOrangeHRM() throws InterruptedException {

		homePageOrange.clickOnAccountOptionsDropDown();
		homePageOrange.clickOnLogoutOption();
		System.out.println("Application Logged-Out Successfully");
	}
	
	@AfterClass
	public void cleanObjects() {
		loginPageOrange = null;
		homePageOrange = null; 
		softassert = null;
		System.out.println("Objects are cleaned Successfully");
	}
	
	@AfterTest
	public void closedBrowser() {
		driver.quit();
		driver = null;
		System.gc();
		System.out.println("Browser closed successfully");
	}
	
}
