package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CommonPage {
    public Locator signInLink;
    public Locator userMenu;
    public Locator myFavouritesLink;
    public Locator signOutLink;
    public Locator homeLink;
    public Locator categoriesNav;
    public Locator handToolsLink;

    public CommonPage(Page page) {
        signInLink = page.locator("[data-test='nav-sign-in']");
        userMenu = page.locator("[data-test='nav-menu']");
        myFavouritesLink = page.locator("[data-test='nav-my-favorites']");
        signOutLink = page.locator("[data-test='nav-sign-out']");
        homeLink = page.locator("[data-test='nav-home']");
        categoriesNav = page.locator("[data-test='nav-categories']");
        handToolsLink = page.locator("[data-test='nav-hand-tools']");
    }
}
