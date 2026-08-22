package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.HomePage;
import org.testng.Assert;

import java.util.Random;

public class HomeSteps {
    Page page;
    HomePage homePage;

    public HomeSteps(Page page) {
        this.page = page;
        homePage = new HomePage(page);
    }

    public HomeSteps goToSignIn() {
        homePage.signInLink.click();

        return this;
    }

    public HomeSteps goToHome() {
        homePage.homeLink.click();
        homePage.productCards.first().waitFor();

        return this;
    }

    public HomeSteps goToFavourites() {
        homePage.userMenu.click();
        homePage.myFavouritesLink.click();

        return this;
    }

    public HomeSteps goToHandTools() {
        homePage.categoriesNav.click();
        homePage.handToolsLink.click();

        return this;
    }

    public HomeSteps logOut() {
        homePage.userMenu.click();
        homePage.signOutLink.click();
        homePage.signInLink.waitFor();

        return this;
    }

    public HomeSteps validateLoggedIn() {
        PlaywrightAssertions.assertThat(homePage.userMenu).isVisible();

        return this;
    }

    public String chooseRandomProduct() {
        homePage.productCards.first().waitFor();
        int count = homePage.productCards.count();
        int index = new Random().nextInt(count);
        String productName = homePage.productNames.nth(index).innerText().trim();
        homePage.productCards.nth(index).click();

        return productName;
    }

    public HomeSteps selectCategory(String categoryName) {
        homePage.categoryCheckbox(categoryName).check();
        homePage.filterStarted.waitFor();
        homePage.searchCompleted.waitFor();

        return this;
    }

    public HomeSteps unselectCategory(String categoryName) {
        homePage.categoryCheckbox(categoryName).uncheck();
        homePage.filterStarted.waitFor();
        homePage.searchCompleted.waitFor();

        return this;
    }

    public int getProductCount() {
        homePage.productCards.first().waitFor();
        goToFirstPageIfNeeded();

        int total = homePage.productCards.count();
        while (isNextPageEnabled()) {
            String firstProductName = homePage.productNames.first().innerText().trim();
            homePage.paginationNext.click();
            PlaywrightAssertions.assertThat(homePage.productNames.first()).not().hasText(firstProductName);
            total += homePage.productCards.count();
        }

        return total;
    }

    public HomeSteps validateProductCount(int expectedCount) {
        Assert.assertEquals(getProductCount(), expectedCount);

        return this;
    }

    private void goToFirstPageIfNeeded() {
        Locator firstPage = homePage.pageNumber(1);
        if (firstPage.count() == 0) {
            return;
        }

        Locator activePage = firstPage.locator("xpath=ancestor::li[contains(@class,'active')]");
        if (activePage.count() > 0) {
            return;
        }

        String firstProductName = homePage.productNames.first().innerText().trim();
        firstPage.click();
        PlaywrightAssertions.assertThat(homePage.productNames.first()).not().hasText(firstProductName);
    }

    private boolean isNextPageEnabled() {
        if (homePage.paginationNext.count() == 0) {
            return false;
        }

        Locator disabledParent = homePage.paginationNext.locator("xpath=ancestor::li[contains(@class,'disabled')]");
        return disabledParent.count() == 0;
    }
}
