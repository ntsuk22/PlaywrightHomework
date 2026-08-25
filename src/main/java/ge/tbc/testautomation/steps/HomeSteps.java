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

    public HomeSteps goToContact() {
        page.waitForResponse(
                response -> response.url().contains("/users/me"),
                homePage.contactLink::click);

        return this;
    }

    public HomeSteps goToHome() {
        homePage.homeLink.click();
        homePage.productCards.first().waitFor();

        return this;
    }

    public HomeSteps validateCatalogDisplayed() {
        homePage.productCards.first().waitFor();
        PlaywrightAssertions.assertThat(homePage.productCards.first()).isVisible();
        PlaywrightAssertions.assertThat(homePage.sortSelect).isVisible();
        PlaywrightAssertions.assertThat(homePage.filters).isVisible();
        PlaywrightAssertions.assertThat(homePage.categoryHeading).isVisible();
        PlaywrightAssertions.assertThat(homePage.brandHeading).isVisible();
        PlaywrightAssertions.assertThat(homePage.paginationNext).isVisible();

        return this;
    }

    public HomeSteps searchFor(String query) {
        homePage.searchInput.fill(query);
        homePage.searchSubmit.click();
        homePage.searchCompletedState.waitFor();

        return this;
    }

    public HomeSteps validateSearchResultsContain(String query) {
        homePage.productNames.first().waitFor();
        int count = homePage.productNames.count();
        Assert.assertTrue(count > 0);
        for (int i = 0; i < count; i++) {
            Assert.assertTrue(homePage.productNames.nth(i).innerText().toLowerCase().contains(query.toLowerCase()));
        }

        return this;
    }

    public HomeSteps selectBrand(String brandName) {
        homePage.brandCheckbox(brandName).check();
        homePage.filterStarted.waitFor();
        homePage.searchCompleted.waitFor();

        return this;
    }

    public HomeSteps openProduct(String productName) {
        homePage.productNames.filter(new Locator.FilterOptions().setHasText(productName)).first().click();

        return this;
    }

    public HomeSteps goToCart() {
        homePage.cartLink.click();

        return this;
    }

    public HomeSteps validateCartBadge(int expectedCount) {
        PlaywrightAssertions.assertThat(homePage.cartBadge).hasText(String.valueOf(expectedCount));

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

    public HomeSteps validateLoggedOut() {
        PlaywrightAssertions.assertThat(homePage.signInLink).isVisible();

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
