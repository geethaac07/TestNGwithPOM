package com.automation.testscripts;

import java.util.List;
import java.util.Set;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Properties;
import java.awt.AWTException;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.automation.basetest.POMBaseTest;
import com.automation.pages.home.HomePage;
import com.automation.pages.login.CheckYourEmailPage;
import com.automation.pages.login.ForgotPasswordPage;
import com.automation.pages.login.LoginPage;
import com.automation.pages.myprofile.MyProfilePage;
import com.automation.pages.mysettings.CalenderReminderPage;
import com.automation.pages.mysettings.CustomizeTabsPage;
import com.automation.pages.mysettings.EmailSettingsPage;
import com.automation.pages.mysettings.LoginHistoryPage;
import com.automation.pages.mysettings.MySettingsPage;
import com.automation.pages.account.*;
import com.automation.utility.POMPropertiesUtility;
import com.fasterxml.jackson.databind.type.CollectionLikeType;

public class POMAutomationScripts extends POMBaseTest {

	public POMPropertiesUtility propUtility = new POMPropertiesUtility();
	public Properties propertyFile = propUtility.loadFile("salesforceproperties");
	String username = propUtility.getPropertyValue("sf.valid.username");
	String password = propUtility.getPropertyValue("sf.valid.password");
	public String emailForgotPassword = propUtility.getPropertyValue("sf.forgot.password");
	public String invalidUsername = propUtility.getPropertyValue("sf.invalid.password");
	public String invalidPassword = propUtility.getPropertyValue("sf.invalid.password");

	@Test
	public void Login_Error_Message_TestScript_01() {
		LoginPage login = new LoginPage(driver);
		String actualLoginTitle = "Login | Salesforce";
		String expectedLoginTitle = login.getTitleOfThePage(driver);

		Assert.assertEquals(actualLoginTitle, expectedLoginTitle);
		login.enterUserName(username);
		login.clickButton();

		String actualText = "Please enter your password.";
		String expectedText = login.getLoginErrorText();

		Assert.assertEquals(actualText, expectedText);
		System.out.println("Error Message is Matched");
		log.info("Error Message is Matched");

	}

	@Test
	public void Success_login_TestScript_02() {

		LoginPage login = new LoginPage(driver);
		String actualLoginTitle = "Login | Salesforce";
		String expectedLoginTitle = login.getTitleOfThePage(driver);

		Assert.assertEquals(actualLoginTitle, expectedLoginTitle);

		login.enterUserName(username);
		login.enterPassword(password);
		login.clickButton();

		HomePage home = new HomePage(driver);
		String expText = home.getTextGetStarted();
		String actText = "Getting Started";

		Assert.assertEquals(actText, expText);

		log.info("Home Page Text is matched");
		report.logTestInfo("Home Page Text is matched");

	}

	@Test
	public void Success_login_RememberMe_TestScript_03() throws InterruptedException {

		LoginPage login = new LoginPage(driver);
		String actualLoginTitle = "Login | Salesforce";
		String expectedLoginTitle = login.getTitleOfThePage(driver);

		Assert.assertEquals(actualLoginTitle, expectedLoginTitle);

		login.enterUserName(username);
		login.enterPassword(password);
		login.rememberMeLogin(true);
		login.clickButton();

		HomePage home = new HomePage(driver);
		String expText = home.getTextGetStarted();
		String actText = "Getting Started";

		Assert.assertEquals(actText, expText);

		log.info("Home Page Text is matched");
		report.logTestInfo("Home Page Text is matched");

		home.clickUserProfile();
		driver = home.clickLogout();
		Thread.sleep(3000);
		String savedUser = login.getUserNameText();

		Assert.assertEquals(username, savedUser);

		log.info("User Name is remembered and displayed in the Username field");
		log.error("User Name is remembered and displayed in the Username field");

	}

	@Test(enabled = false)
	public void Forgot_Password_TestScript__4A() {

		LoginPage login = new LoginPage(driver);
		String actualLoginTitle = "Login | Salesforce";
		String expectedLoginTitle = login.getTitleOfThePage(driver);

		Assert.assertEquals(actualLoginTitle, expectedLoginTitle);

		driver = login.clickForgotPassword();

		ForgotPasswordPage forgotPW = new ForgotPasswordPage(driver);
		forgotPW.enterUserNameText(emailForgotPassword);
		driver = forgotPW.clickContinueButton();

		CheckYourEmailPage checkEmail = new CheckYourEmailPage(driver);
		checkEmail.returnToLogin();
	}

	@Test
	public void Forgot_Password_TestScript_4B() {
		LoginPage login = new LoginPage(driver);
		String actualLoginTitle = "Login | Salesforce";
		String expectedLoginTitle = login.getTitleOfThePage(driver);

		Assert.assertEquals(actualLoginTitle, expectedLoginTitle);

		login.enterUserName(invalidUsername);
		login.enterPassword(invalidPassword);
		login.rememberMeLogin(false);
		login.clickButton();

		String expError = login.getLoginErrorText();
		String actError = "Please check your username and password. If you still can't log in, contact your Salesforce administrator.";

		Assert.assertEquals(actError, expError);

		log.info("Error Message is displayed");
		report.logTestInfo("Error Message is displayed");
	}

	@Test
	public void User_Menu_Dropdown_TestScript_05() throws InterruptedException {

		LoginPage login = new LoginPage(driver);
		String actualLoginTitle = "Login | Salesforce";
		String expectedLoginTitle = login.getTitleOfThePage(driver);

		Assert.assertEquals(actualLoginTitle, expectedLoginTitle);

		login.enterUserName(username);
		login.enterPassword(password);
		login.rememberMeLogin(false);
		login.clickButton();

		HomePage home = new HomePage(driver);
		Thread.sleep(4000);

		home.checkUserMenuOptions();
	}

	@Test
	public void MyProfile_UserMenu_Option_TestScript_06() throws AWTException, InterruptedException {

		LoginPage login = new LoginPage(driver);
		String actualLoginTitle = "Login | Salesforce";
		String expectedLoginTitle = login.getTitleOfThePage(driver);

		Assert.assertEquals(actualLoginTitle, expectedLoginTitle);

		login.enterUserName(username);
		login.enterPassword(password);
		login.rememberMeLogin(false);
		login.clickButton();

		HomePage home = new HomePage(driver);
		Thread.sleep(4000);
		home.clickUserProfile();

		driver = home.clickMyProfile();

		MyProfilePage myProfile = new MyProfilePage(driver);
		myProfile.clickEditDropDown();
		myProfile.clickEditProfile();
		Thread.sleep(3000);
		myProfile.switchToFrame();
		myProfile.enterLastName();
		myProfile.clickOnSaveButton();

		myProfile.clickPostLink();
		myProfile.clickPostFrame();
		myProfile.enterMessageToPost();

	}

	@Test
	public void MySettings_UserMenu_Option_TestScript_07() throws InterruptedException, AWTException {
		LoginPage login = new LoginPage(driver);
		String actualLoginTitle = "Login | Salesforce";
		String expectedLoginTitle = login.getTitleOfThePage(driver);

		Assert.assertEquals(actualLoginTitle, expectedLoginTitle);

		login.enterUserName(username);
		login.enterPassword(password);
		login.rememberMeLogin(false);
		login.clickButton();

		HomePage home = new HomePage(driver);
		Thread.sleep(4000);
		home.clickUserProfile();
		driver = home.clickMySettings();

		MySettingsPage mySettings = new MySettingsPage(driver);
		mySettings.clickPersonalTab();
		LoginHistoryPage loginHistory = new LoginHistoryPage(driver);
		loginHistory.clickLoginHistory();
		loginHistory.clickDownloadHistory();

		MySettingsPage mySettings1 = new MySettingsPage(driver);
		mySettings1.clickDisplayLayout();

		CustomizeTabsPage custTab = new CustomizeTabsPage(driver);

		custTab.clickCustTabs();
		custTab.selectSalesForceChatCustApp();
		custTab.selectAvailTabs();

		MySettingsPage mySettings2 = new MySettingsPage(driver);
//		mySettings.addToTab();
//		mySettings.clickSaveTab();
		mySettings2.clickTopRightDropdown();
		mySettings2.clickSalesForceChatterMenu();

		HomePage home1 = new HomePage(driver);
		home1.verifyReportsTab();
		home1.clickUserProfile();
		home1.clickMySettings();

		MySettingsPage mySettings3 = new MySettingsPage(driver);

		mySettings3.clickEmail();
		mySettings3.clickEmailSettings();

		EmailSettingsPage email = new EmailSettingsPage(driver);
		email.enterSenderName();
		email.enterSenderEmail();
		email.autoBccYes();
		email.saveEmailSettings();

		mySettings3.clickCalAndReminder();
		mySettings3.clickActReminder();

		CalenderReminderPage calReminder = new CalenderReminderPage(driver);

		calReminder.clickTestReminder();

		calReminder.verifySampleEvent();
	}

	@Test
	public void DevConsole_UserMenu_Option_TestScript_08() throws InterruptedException, AWTException {

		LoginPage login = new LoginPage(driver);
		String actualLoginTitle = "Login | Salesforce";
		String expectedLoginTitle = login.getTitleOfThePage(driver);

		Assert.assertEquals(actualLoginTitle, expectedLoginTitle);

		login.enterUserName(username);
		login.enterPassword(password);
		login.rememberMeLogin(false);
		login.clickButton();

		HomePage home = new HomePage(driver);
		Thread.sleep(4000);

		home.clickUserProfile();
		home.clickDevConsole();
		home.switchToParentWindow();
		log.info("Developer Console window is closed - TC passed");
		report.logTestInfo("Developer Console window is closed - TC passed");
	}

	@Test
	public void Logout_TestScript_09() throws InterruptedException {
		LoginPage login = new LoginPage(driver);
		String actualLoginTitle = "Login | Salesforce";
		String expectedLoginTitle = login.getTitleOfThePage(driver);

		Assert.assertEquals(actualLoginTitle, expectedLoginTitle);

		login.enterUserName(username);
		login.enterPassword(password);
		login.rememberMeLogin(false);
		login.clickButton();

		HomePage home = new HomePage(driver);
		Thread.sleep(4000);

		home.clickUserProfile();
		home.clickLogout();
		String actualTitle2 = "Login | Salesforce";
		Thread.sleep(3000);
		String expectedTitle2 = home.GetPageTitle(driver);

		Assert.assertEquals(actualTitle2, expectedTitle2);
	}

	@Test
	public void Create_Account_TestScript_10() throws AWTException, InterruptedException {
		LoginPage login = new LoginPage(driver);
		String actualLoginTitle = "Login | Salesforce";
		String expectedLoginTitle = login.getTitleOfThePage(driver);

		Assert.assertEquals(actualLoginTitle, expectedLoginTitle);

		login.enterUserName(username);
		login.enterPassword(password);
		login.rememberMeLogin(false);
		login.clickButton();

		HomePage home = new HomePage(driver);
		driver = home.clickAccounts();
		Thread.sleep(4000);
		home.Close_SwitchTo_Lightning_PopUp();

		AccountsPage accounts = new AccountsPage(driver);
		driver = accounts.createNewAAccount();

		EditAccountPage editAccount = new EditAccountPage(driver);
		editAccount.setAccountName();
		editAccount.setAccountType();
		editAccount.setCustPriority();
		driver = editAccount.clickSaveAcc();

		AccountDetailsPage accDetails = new AccountDetailsPage(driver);
		String expHeading = accDetails.getAccDetailsText();
		String actHeading = "Account Detail";

		Assert.assertEquals(actHeading, expHeading);
		System.out.println("New account is created and the Account Details page is displayed.");
	}
}
