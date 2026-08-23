package ge.tbc.testautomation.pages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

public class PrestaShopHomePage {
    public Locator contactUsLink;
    public Locator footerEmailLink;

    public PrestaShopHomePage(FrameLocator shop) {
        contactUsLink = shop.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName("Contact us"));
        footerEmailLink = shop.locator("footer a[href^='mailto:']");
    }
}
