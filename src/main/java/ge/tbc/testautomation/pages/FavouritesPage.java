package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FavouritesPage extends CommonPage {
    public Locator pageTitle;
    public Locator favouriteItems;
    public Locator productNames;
    public Locator deleteBtn;

    public FavouritesPage(Page page) {
        super(page);
        pageTitle = page.locator("[data-test='page-title']");
        favouriteItems = page.locator("[data-test^='favorite-']");
        productNames = page.locator("[data-test^='favorite-'] [data-test='product-name']");
        deleteBtn = page.locator("[data-test='delete']");
    }
}
