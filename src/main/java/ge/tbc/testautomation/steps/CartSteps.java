package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.CartPage;
import org.testng.Assert;

public class CartSteps {
    Page page;
    CartPage cartPage;

    public CartSteps(Page page) {
        this.page = page;
        cartPage = new CartPage(page);
    }

    public CartSteps validateCartItem(String productName, int quantity, double unitPrice) {
        PlaywrightAssertions.assertThat(cartPage.productTitle).containsText(productName);
        PlaywrightAssertions.assertThat(cartPage.quantityInput).hasValue(String.valueOf(quantity));
        Assert.assertEquals(parsePrice(cartPage.productPrice.innerText()), unitPrice, 0.01);
        Assert.assertEquals(parsePrice(cartPage.linePrice.innerText()), unitPrice * quantity, 0.01);
        Assert.assertEquals(parsePrice(cartPage.cartTotal.innerText()), unitPrice * quantity, 0.01);

        return this;
    }

    public CartSteps updateQuantity(int quantity) {
        cartPage.quantityInput.fill(String.valueOf(quantity));
        cartPage.quantityInput.press("Enter");
        PlaywrightAssertions.assertThat(cartPage.quantityInput).hasValue(String.valueOf(quantity));

        return this;
    }

    public CartSteps validateTotal(double unitPrice, int quantity) {
        String expected = String.format("%.2f", unitPrice * quantity);
        PlaywrightAssertions.assertThat(cartPage.linePrice).containsText(expected);
        PlaywrightAssertions.assertThat(cartPage.cartTotal).containsText(expected);

        return this;
    }

    public CartSteps proceedToCheckout() {
        cartPage.proceedToCheckout.click();

        return this;
    }

    private double parsePrice(String text) {
        return Double.parseDouble(text.replace("$", "").replace(",", "").trim());
    }
}
