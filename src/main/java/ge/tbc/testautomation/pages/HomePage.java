package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage extends CommonPage {
    Page page;
    public Locator productCards;
    public Locator productNames;
    public Locator searchCompleted;
    public Locator filterStarted;
    public Locator searchCompletedState;
    public Locator paginationNext;
    public Locator sortSelect;
    public Locator filters;
    public Locator searchInput;
    public Locator searchSubmit;
    public Locator categoryHeading;
    public Locator brandHeading;

    public HomePage(Page page) {
        super(page);
        this.page = page;
        productCards = page.locator("a.card[data-test^='product-']");
        productNames = page.locator("a.card[data-test^='product-'] [data-test='product-name']");
        searchCompleted = page.locator("[data-test='filter_completed']");
        filterStarted = page.locator("[data-test='filter_started']");
        searchCompletedState = page.locator("[data-test='search_completed']");
        paginationNext = page.locator("[data-test='pagination-next']");
        sortSelect = page.locator("[data-test='sort']");
        filters = page.locator("#filters");
        searchInput = page.locator("[data-test='search-query']");
        searchSubmit = page.locator("[data-test='search-submit']");
        categoryHeading = page.getByText("By category:");
        brandHeading = page.getByText("By brand:");
    }

    public Locator categoryCheckbox(String categoryName) {
        return page.locator("label")
                .filter(new Locator.FilterOptions().setHasText(categoryName))
                .locator("input[type='checkbox']")
                .first();
    }

    public Locator brandCheckbox(String brandName) {
        return page.locator("label")
                .filter(new Locator.FilterOptions().setHasText(brandName))
                .locator("input[type='checkbox']")
                .first();
    }

    public Locator pageNumber(int pageNumber) {
        return page.locator("[aria-label='Page-" + pageNumber + "']");
    }
}
