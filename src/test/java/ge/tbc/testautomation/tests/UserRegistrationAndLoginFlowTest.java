package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.FavouritesSteps;
import ge.tbc.testautomation.steps.ForgotPasswordSteps;
import ge.tbc.testautomation.steps.HomeSteps;
import ge.tbc.testautomation.steps.LoginSteps;
import ge.tbc.testautomation.steps.ProductSteps;
import ge.tbc.testautomation.steps.RegisterSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Playwright Homework")
@Feature("Registration and login")
public class UserRegistrationAndLoginFlowTest extends BaseTest {
    RegisterSteps registerSteps;
    LoginSteps loginSteps;
    HomeSteps homeSteps;
    ProductSteps productSteps;
    FavouritesSteps favouritesSteps;
    ForgotPasswordSteps forgotPasswordSteps;

    @BeforeClass
    public void innerSetup() {
        registerSteps = new RegisterSteps(page);
        loginSteps = new LoginSteps(page);
        homeSteps = new HomeSteps(page);
        productSteps = new ProductSteps(page);
        favouritesSteps = new FavouritesSteps(page);
        forgotPasswordSteps = new ForgotPasswordSteps(page);
    }

    @Description("SCRUM-T24: register, log in, manage favourites, log out, and request a password reset.")
    @Test(description = "SCRUM-T24: მომხმარებლის რეგისტრაცია, ავტორიზაცია და სესია")
    public void registerLoginAndManageSession() {
        String email = "nino" + System.currentTimeMillis() + "@gmail.com";
        String password = "Nino#" + System.currentTimeMillis() + "aA1!";

        homeSteps.goToSignIn();
        loginSteps.validateLoginPage();
        loginSteps.goToRegister();
        registerSteps.validateRegistrationFormDisplayed();
        registerSteps
                .fillInvalidEmailAndPassword(Constants.INVALID_EMAIL, Constants.WEAK_PASSWORD)
                .submit()
                .validateValidationErrors();
        registerSteps
                .fillRegistrationForm(
                        Constants.FIRST_NAME,
                        Constants.LAST_NAME,
                        Constants.DATE_OF_BIRTH,
                        Constants.COUNTRY,
                        Constants.POSTAL_CODE,
                        Constants.HOUSE_NUMBER,
                        Constants.STREET,
                        Constants.CITY,
                        Constants.STATE,
                        Constants.PHONE,
                        email,
                        password)
                .submit();

        loginSteps.validateLoginPage();
        loginSteps
                .fillLoginCredentials(email, password)
                .logIn();
        homeSteps.validateLoggedIn();

        homeSteps.goToHome();
        String favouriteProductName = homeSteps.chooseRandomProduct();
        productSteps.addItemToFavourites();
        homeSteps.goToFavourites();
        favouritesSteps.validateFavouriteVisible(favouriteProductName);

        homeSteps.logOut();
        homeSteps.validateLoggedOut();

        homeSteps.goToSignIn();
        loginSteps.goToForgotPassword();
        forgotPasswordSteps.validateForgotPasswordPage();
        forgotPasswordSteps
                .requestReset(email)
                .validateResetConfirmation();
    }
}
