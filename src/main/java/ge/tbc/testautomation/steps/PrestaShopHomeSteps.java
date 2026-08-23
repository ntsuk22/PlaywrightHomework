package ge.tbc.testautomation.steps;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.pages.PrestaShopHomePage;
import org.testng.Assert;

import java.util.List;
import java.util.regex.Pattern;

public class PrestaShopHomeSteps {
    Page page;
    FrameLocator shop;
    PrestaShopHomePage homePage;

    public PrestaShopHomeSteps(Page page) {
        this.page = page;
        this.shop = page.frameLocator("iframe[name='" + Constants.PRESTASHOP_FRAME_NAME + "']");
        this.homePage = new PrestaShopHomePage(shop);
    }

    public PrestaShopHomeSteps waitForShop() {
        page.locator("iframe[name='" + Constants.PRESTASHOP_FRAME_NAME + "']")
                .waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.ATTACHED)
                        .setTimeout(180000));
        homePage.contactUsLink.first().waitFor(new Locator.WaitForOptions().setTimeout(180000));
        dismissCookies();

        return this;
    }

    public String extractStoreEmail() {
        homePage.footerEmailLink.first().waitFor();
        String footerHref = homePage.footerEmailLink.first().getAttribute("href");
        String footerText = homePage.footerEmailLink.first().innerText();

        String email = (String) page.evaluate("""
                ([href, text]) => {
                  const raw = (href || text || "").trim();
                  return raw.replace(/^mailto:/i, "").split("?")[0].trim();
                }
                """, List.of(
                footerHref == null ? "" : footerHref,
                footerText == null ? "" : footerText));
        Assert.assertFalse(email == null || email.isBlank(), "Store email was not found in the footer");

        return email;
    }

    public PrestaShopHomeSteps goToContactUs() {
        homePage.contactUsLink.first().click();

        return this;
    }

    private void dismissCookies() {
        Locator accept = shop.getByRole(AriaRole.BUTTON,
                new FrameLocator.GetByRoleOptions().setName(
                        Pattern.compile("accept|agree|ok|got it", Pattern.CASE_INSENSITIVE)));
        try {
            accept.first().click(new Locator.ClickOptions().setTimeout(3000));
        } catch (Exception ignored) {
        }
    }
}
