package ui_tests;

import data_providers.UserDataProvider;
import dto.UserLombok;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

import static utils.PropertiesReader.*;

public class LoginTests extends AppManager {
    LoginPage loginPage;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void goToRegistrationLoginPage() {
        new HomePage(getDriver()).clickLinkLogin();
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void loginPositiveTest() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        ContactsPage contactsPage = new ContactsPage(getDriver());
        softAssert.assertTrue(contactsPage.isLinkContactDisplayed(),
                "validate isLinkContactDisplayed");
        softAssert.assertTrue(contactsPage.isUrlContainsText("contacts"), "validate url");
        softAssert.assertAll();
    }

    @Test
    public void loginNegativeAllFieldsEmptyWithNotTypeTest() {
        loginPage.clickBtnLogin();
//        Assert.assertTrue(loginPage.closeAlert()
//                .contains("Wrong email or password"));
        Assert.assertEquals(loginPage.closeAlert(),
                "Wrong email or password");
    }

    // HW 7.01
    @Test
    public void loginNegativeAllFieldsEmptyWithClickOnIt() {
        UserLombok user = UserLombok.builder()
                .username("")
                .password("")
                .build();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlert(),
                "Wrong email or password");
    }

    // HW 7.02
    @Test
    public void loginNegativeEmptyEmailFieldWithClickOnItTest() {
        UserLombok user = UserLombok.builder()
                .username("")
                .password(getProperty("base.properties", "password"))
                .build();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlert(),
                "Wrong email or password");
    }

    // HW 7.03
    @Test
    public void loginNegativeEmptyPasswordFieldWithClickOnItTest() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password("")
                .build();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlert(),
                "Wrong email or password");
    }

    // HW 7.04
    @Test
    public void loginNegativeInvalidEmailTest() {
        UserLombok user = UserLombok.builder()
                .username("renate.certoka11@gmail.com")
                .password(getProperty("base.properties", "password"))
                .build();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlert(),
                "Wrong email or password");
    }

    // HW 7.05
    @Test
    public void loginNegativeInvalidPasswordTest() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password("123RenC!$")
                .build();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlert(),
                "Wrong email or password");
    }

    // HW 7.06
    @Test(dataProvider = "dataProviderInvalidPasswordOrEmail",
            dataProviderClass = UserDataProvider.class)
    public void loginNegativeInvalidsEmailsTest(UserLombok user) {
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlert(),
                "Wrong email or password");
    }


}
