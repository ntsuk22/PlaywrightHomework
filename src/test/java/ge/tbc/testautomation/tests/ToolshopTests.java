package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.CategorySteps;
import ge.tbc.testautomation.steps.FavouritesSteps;
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
@Feature("Toolshop sequential tests")
public class ToolshopTests extends BaseTest {
    RegisterSteps registerSteps;
    LoginSteps loginSteps;
    HomeSteps homeSteps;
    ProductSteps productSteps;
    FavouritesSteps favouritesSteps;
    CategorySteps categorySteps;
    String email;
    String password;
    String favouriteProductName;

    @BeforeClass
    public void innerSetup() {
        registerSteps = new RegisterSteps(page);
        loginSteps = new LoginSteps(page);
        homeSteps = new HomeSteps(page);
        productSteps = new ProductSteps(page);
        favouritesSteps = new FavouritesSteps(page);
        categorySteps = new CategorySteps(page);
        email = "nino" + System.currentTimeMillis() + "@gmail.com";
        password = "Nino#" + System.currentTimeMillis() + "aA1!";
    }

    @Description("Register a new account and log in")
    @Test(priority = 1, description = "Register a new account and log in")
    public void registerAndLogin() {
        homeSteps.goToSignIn();
        loginSteps.goToRegister();
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
        loginSteps
                .fillLoginCredentials(email, password)
                .logIn();
        homeSteps.validateLoggedIn();
    }

    @Description("Add a random product to favourites and verify it persists after re-login")
    @Test(priority = 2, description = "Add a random product to favourites and verify it persists after re-login")
    public void favouritesTest() {
        homeSteps.goToHome();
        favouriteProductName = homeSteps.chooseRandomProduct();
        productSteps.addItemToFavourites();
        homeSteps.goToFavourites();
        favouritesSteps.validateFavouriteVisible(favouriteProductName);
        homeSteps.logOut();
        homeSteps.goToSignIn();
        loginSteps
                .fillLoginCredentials(email, password)
                .logIn();
        homeSteps.goToFavourites();
        favouritesSteps.validateFavouriteVisible(favouriteProductName);
    }

    @Description("Verify that combined category filters return the sum of individual counts")
    @Test(priority = 3, description = "Verify that combined category filters return the sum of individual counts")
    public void filterTest() {
        homeSteps.goToHome();
        homeSteps.selectCategory(Constants.FIRST_CATEGORY);
        int firstCategoryCount = homeSteps.getProductCount();
        homeSteps.unselectCategory(Constants.FIRST_CATEGORY);
        homeSteps.selectCategory(Constants.SECOND_CATEGORY);
        int secondCategoryCount = homeSteps.getProductCount();
        homeSteps.selectCategory(Constants.FIRST_CATEGORY);
        homeSteps.validateProductCount(firstCategoryCount + secondCategoryCount);
    }

    @Description("Remove a favourite and verify it stays deleted after re-login")
    @Test(priority = 4, description = "Remove a favourite and verify it stays deleted after re-login")
    public void removeFavouriteTest() {
        homeSteps.goToFavourites();
        favouritesSteps.removeFavourite();
        favouritesSteps.validateFavouriteNotVisible(favouriteProductName);
        homeSteps.logOut();
        homeSteps.goToSignIn();
        loginSteps
                .fillLoginCredentials(email, password)
                .logIn();
        homeSteps.goToFavourites();
        favouritesSteps.validateFavouriteNotVisible(favouriteProductName);
    }

    @Description("Open Thor Hammer from Hand Tools / Hammer and validate tags")
    @Test(priority = 5, description = "Open Thor Hammer from Hand Tools / Hammer and validate tags")
    public void tagsTest() {
        homeSteps.goToHandTools();
        categorySteps
                .checkHammerCategory()
                .openThorHammer();
        productSteps.validateTags(Constants.EXPECTED_CATEGORY_TAG, Constants.EXPECTED_BRAND_TAG);
    }
}
