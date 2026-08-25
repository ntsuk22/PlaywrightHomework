package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.ProductPage;
import io.qameta.allure.Step;

public class ProductSteps {
    Page page;
    ProductPage productPage;

    public ProductSteps(Page page) {
        this.page = page;
        productPage = new ProductPage(page);
    }

    @Step("Validate product details")
    public ProductSteps validateProductDetails() {
        PlaywrightAssertions.assertThat(productPage.productName).isVisible();
        PlaywrightAssertions.assertThat(productPage.unitPrice).isVisible();
        PlaywrightAssertions.assertThat(productPage.description).isVisible();
        PlaywrightAssertions.assertThat(productPage.co2Rating).isVisible();
        PlaywrightAssertions.assertThat(productPage.quantityInput).isVisible();

        return this;
    }

    @Step("Increase quantity to {quantity}")
    public ProductSteps increaseQuantityTo(int quantity) {
        while (Integer.parseInt(productPage.quantityInput.inputValue()) < quantity) {
            productPage.increaseQuantity.click();
        }
        PlaywrightAssertions.assertThat(productPage.quantityInput).hasValue(String.valueOf(quantity));

        return this;
    }

    @Step("Add product to cart")
    public ProductSteps addToCart() {
        productPage.addToCart.click();

        return this;
    }

    @Step("Validate cart toast contains {message}")
    public ProductSteps validateCartToast(String message) {
        PlaywrightAssertions.assertThat(productPage.toastMessage).containsText(message);

        return this;
    }

    @Step("Get unit price")
    public double getUnitPrice() {
        return Double.parseDouble(productPage.unitPrice.innerText().trim());
    }

    @Step("Add product to favourites")
    public ProductSteps addItemToFavourites() {
        productPage.addToFavourites.click();

        return this;
    }

    @Step("Validate product tags")
    public ProductSteps validateTags(String expectedCategory, String expectedBrand) {
        PlaywrightAssertions.assertThat(productPage.categoryTag).hasText(expectedCategory);
        PlaywrightAssertions.assertThat(productPage.brandTag).hasText(expectedBrand);

        return this;
    }
}
