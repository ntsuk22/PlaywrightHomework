package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage extends CommonPage {
    public Locator productTitle;
    public Locator quantityInput;
    public Locator productPrice;
    public Locator linePrice;
    public Locator cartTotal;
    public Locator proceedToCheckout;

    public CartPage(Page page) {
        super(page);
        productTitle = page.locator("[data-test='product-title']");
        quantityInput = page.locator("[data-test='product-quantity']");
        productPrice = page.locator("[data-test='product-price']");
        linePrice = page.locator("[data-test='line-price']");
        cartTotal = page.locator("[data-test='cart-total']");
        proceedToCheckout = page.locator("[data-test='proceed-1']");
    }
}
