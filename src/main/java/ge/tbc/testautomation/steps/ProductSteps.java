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

    public ProductSteps validateProductDetails() {
        PlaywrightAssertions.assertThat(productPage.productName).isVisible();
        PlaywrightAssertions.assertThat(productPage.unitPrice).isVisible();
        PlaywrightAssertions.assertThat(productPage.description).isVisible();
        PlaywrightAssertions.assertThat(productPage.co2Rating).isVisible();
        PlaywrightAssertions.assertThat(productPage.quantityInput).isVisible();

        return this;
    }

    public ProductSteps increaseQuantityTo(int quantity) {
        while (Integer.parseInt(productPage.quantityInput.inputValue()) < quantity) {
            productPage.increaseQuantity.click();
        }
        PlaywrightAssertions.assertThat(productPage.quantityInput).hasValue(String.valueOf(quantity));

        return this;
    }

    public ProductSteps addToCart() {
        productPage.addToCart.click();

        return this;
    }

    public ProductSteps validateCartToast(String message) {
        PlaywrightAssertions.assertThat(productPage.toastMessage).containsText(message);

        return this;
    }

    public double getUnitPrice() {
        return Double.parseDouble(productPage.unitPrice.innerText().trim());
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
