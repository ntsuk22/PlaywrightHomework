package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.CategorySteps;
import ge.tbc.testautomation.steps.FavouritesSteps;
import ge.tbc.testautomation.steps.HomeSteps;
import ge.tbc.testautomation.steps.LoginSteps;
import ge.tbc.testautomation.steps.ProductSteps;
import ge.tbc.testautomation.steps.RegisterSteps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class IsolatedToolshopTests extends IsolatedBaseTest {
    RegisterSteps registerSteps;
    LoginSteps loginSteps;
    HomeSteps homeSteps;
    ProductSteps productSteps;
    FavouritesSteps favouritesSteps;
    CategorySteps categorySteps;
    String email;
    String password;

    @BeforeMethod
    public void innerSetup() {
        registerSteps = new RegisterSteps(page);
        loginSteps = new LoginSteps(page);
        homeSteps = new HomeSteps(page);
        productSteps = new ProductSteps(page);
        favouritesSteps = new FavouritesSteps(page);
        categorySteps = new CategorySteps(page);
        email = "nino" + System.currentTimeMillis() + "@gmail.com";
        password = "Nino#" + System.currentTimeMillis() + "aA1!";
        registerAndLogin();
    }

    @Test(description = "Add a random product to favourites and verify it persists after re-login")
    public void favouritesTest() {
        homeSteps.goToHome();
        String favouriteProductName = homeSteps.chooseRandomProduct();
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

    @Test(description = "Verify that combined category filters return the sum of individual counts")
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

    @Test(description = "Remove a favourite and verify it stays deleted after re-login")
    public void removeFavouriteTest() {
        homeSteps.goToHome();
        String favouriteProductName = homeSteps.chooseRandomProduct();
        productSteps.addItemToFavourites();
        homeSteps.goToFavourites();
        favouritesSteps
                .validateFavouriteVisible(favouriteProductName)
                .removeFavourite()
                .validateFavouriteNotVisible(favouriteProductName);
        homeSteps.logOut();
        homeSteps.goToSignIn();
        loginSteps
                .fillLoginCredentials(email, password)
                .logIn();
        homeSteps.goToFavourites();
        favouritesSteps.validateFavouriteNotVisible(favouriteProductName);
    }

    @Test(description = "Open Thor Hammer from Hand Tools / Hammer and validate tags")
    public void tagsTest() {
        homeSteps.goToHandTools();
        categorySteps
                .checkHammerCategory()
                .openThorHammer();
        productSteps.validateTags(Constants.EXPECTED_CATEGORY_TAG, Constants.EXPECTED_BRAND_TAG);
    }

    private void registerAndLogin() {
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
}
