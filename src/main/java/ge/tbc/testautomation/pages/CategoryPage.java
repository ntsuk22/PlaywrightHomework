package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CategoryPage extends CommonPage {
    public Locator searchCompleted;
    public Locator filterStarted;
    public Locator hammerCheckbox;
    public Locator thorHammer;

    public CategoryPage(Page page) {
        super(page);
        searchCompleted = page.locator("[data-test='filter_completed']");
        filterStarted = page.locator("[data-test='filter_started']");
        hammerCheckbox = page.locator("label")
                .filter(new Locator.FilterOptions().setHasText("Hammer"))
                .locator("input[type='checkbox']")
                .first();
        thorHammer = page.locator("a[data-test^='product-']")
                .filter(new Locator.FilterOptions().setHasText("Thor Hammer"));
    }
}
