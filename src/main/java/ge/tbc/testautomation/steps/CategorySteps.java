package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.CategoryPage;

public class CategorySteps {
    Page page;
    CategoryPage categoryPage;

    public CategorySteps(Page page) {
        this.page = page;
        categoryPage = new CategoryPage(page);
    }

    public CategorySteps checkHammerCategory() {
        categoryPage.hammerCheckbox.waitFor();
        categoryPage.hammerCheckbox.check();
        categoryPage.filterStarted.waitFor();
        categoryPage.searchCompleted.waitFor();

        return this;
    }

    public CategorySteps openThorHammer() {
        categoryPage.thorHammer.click();

        return this;
    }
}
