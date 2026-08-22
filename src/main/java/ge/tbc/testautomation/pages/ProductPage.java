package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductPage extends CommonPage {
    public Locator addToFavourites;
    public Locator productName;
    public Locator categoryTag;
    public Locator brandTag;
    public Locator unitPrice;
    public Locator description;
    public Locator co2Rating;
    public Locator quantityInput;
    public Locator increaseQuantity;
    public Locator addToCart;
    public Locator toastMessage;

    public ProductPage(Page page) {
        super(page);
        addToFavourites = page.locator("[data-test='add-to-favorites']");
        productName = page.locator("[data-test='product-name']");
        categoryTag = page.locator("[aria-label='category']");
        brandTag = page.locator("[aria-label='brand']");
        unitPrice = page.locator("[data-test='unit-price']");
        description = page.locator("[data-test='product-description']");
        co2Rating = page.locator("[data-test='co2-rating-badge']");
        quantityInput = page.locator("[data-test='quantity']");
        increaseQuantity = page.locator("[data-test='increase-quantity']");
        addToCart = page.locator("[data-test='add-to-cart']");
        toastMessage = page.locator(".toast-message");
    }
}
