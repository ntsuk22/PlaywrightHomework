package ge.tbc.testautomation.steps;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.FavouritesPage;
import io.qameta.allure.Step;

public class FavouritesSteps {
    Page page;
    FavouritesPage favouritesPage;

    public FavouritesSteps(Page page) {
        this.page = page;
        favouritesPage = new FavouritesPage(page);
    }

    @Step("Validate favourite {productName} is visible")
    public FavouritesSteps validateFavouriteVisible(String productName) {
        Locator favourite = favouritesPage.productNames.filter(new Locator.FilterOptions().setHasText(productName));
        PlaywrightAssertions.assertThat(favourite).hasCount(1);

        return this;
    }

    @Step("Remove favourite")
    public FavouritesSteps removeFavourite() {
        favouritesPage.deleteBtn.first().click();

        return this;
    }

    @Step("Validate favourite {productName} is not visible")
    public FavouritesSteps validateFavouriteNotVisible(String productName) {
        Locator favourite = favouritesPage.productNames.filter(new Locator.FilterOptions().setHasText(productName));
        PlaywrightAssertions.assertThat(favourite).hasCount(0);

        return this;
    }
}
