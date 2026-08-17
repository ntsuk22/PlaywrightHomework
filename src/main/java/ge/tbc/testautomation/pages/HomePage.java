package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage extends CommonPage {
    Page page;
    public Locator productCards;
    public Locator productNames;
    public Locator searchCompleted;
    public Locator filterStarted;
    public Locator paginationNext;

    public HomePage(Page page) {
        super(page);
        this.page = page;
        productCards = page.locator("a.card[data-test^='product-']");
        productNames = page.locator("a.card[data-test^='product-'] [data-test='product-name']");
        searchCompleted = page.locator("[data-test='filter_completed']");
        filterStarted = page.locator("[data-test='filter_started']");
        paginationNext = page.locator("[data-test='pagination-next']");
    }

    public Locator categoryCheckbox(String categoryName) {
        return page.locator("label")
                .filter(new Locator.FilterOptions().setHasText(categoryName))
                .locator("input[type='checkbox']")
                .first();
    }

    public Locator pageNumber(int pageNumber) {
        return page.locator("[aria-label='Page-" + pageNumber + "']");
    }
}
