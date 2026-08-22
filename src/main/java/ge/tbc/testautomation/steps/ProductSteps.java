package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.ProductPage;

public class ProductSteps {
    Page page;
    ProductPage productPage;

    public ProductSteps(Page page) {
        this.page = page;
        productPage = new ProductPage(page);
    }

    public ProductSteps addItemToFavourites() {
        productPage.addToFavourites.click();

        return this;
    }

    public ProductSteps validateTags(String expectedCategory, String expectedBrand) {
        PlaywrightAssertions.assertThat(productPage.categoryTag).hasText(expectedCategory);
        PlaywrightAssertions.assertThat(productPage.brandTag).hasText(expectedBrand);

        return this;
    }
}
