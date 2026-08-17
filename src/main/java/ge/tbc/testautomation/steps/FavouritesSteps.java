package ge.tbc.testautomation.steps;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.FavouritesPage;

public class FavouritesSteps {
    Page page;
    FavouritesPage favouritesPage;

    public FavouritesSteps(Page page) {
        this.page = page;
        favouritesPage = new FavouritesPage(page);
    }

    public FavouritesSteps validateFavouriteVisible(String productName) {
        Locator favourite = favouritesPage.productNames.filter(new Locator.FilterOptions().setHasText(productName));
        PlaywrightAssertions.assertThat(favourite).hasCount(1);

        return this;
    }

    public FavouritesSteps removeFavourite() {
        favouritesPage.deleteBtn.first().click();

        return this;
    }

    public FavouritesSteps validateFavouriteNotVisible(String productName) {
        Locator favourite = favouritesPage.productNames.filter(new Locator.FilterOptions().setHasText(productName));
        PlaywrightAssertions.assertThat(favourite).hasCount(0);

        return this;
    }
}
