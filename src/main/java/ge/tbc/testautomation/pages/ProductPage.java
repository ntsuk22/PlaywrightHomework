package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductPage extends CommonPage {
    public Locator addToFavourites;
    public Locator productName;
    public Locator categoryTag;
    public Locator brandTag;

    public ProductPage(Page page) {
        super(page);
        addToFavourites = page.locator("[data-test='add-to-favorites']");
        productName = page.locator("[data-test='product-name']");
        categoryTag = page.locator("[aria-label='category']");
        brandTag = page.locator("[aria-label='brand']");
    }
}
