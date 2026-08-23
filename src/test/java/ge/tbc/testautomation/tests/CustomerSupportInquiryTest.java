package ge.tbc.testautomation.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.PrestaShopContactSteps;
import ge.tbc.testautomation.steps.PrestaShopHomeSteps;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomerSupportInquiryTest {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    PrestaShopHomeSteps homeSteps;
    PrestaShopContactSteps contactSteps;

    @BeforeMethod
    public void setUp(ITestContext context) {
        String browserName = context.getCurrentXmlTest().getParameter("browser");
        if (browserName == null || browserName.isBlank()) {
            browserName = "chromium";
        }
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(false);
        if ("webkit".equalsIgnoreCase(browserName)) {
            browser = playwright.webkit().launch(launchOptions);
        } else {
            browser = playwright.chromium().launch(launchOptions);
        }
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
        page = browserContext.newPage();
        page.setDefaultTimeout(120000);
        page.navigate(Constants.PRESTASHOP_URL, new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                .setTimeout(120000));
        homeSteps = new PrestaShopHomeSteps(page);
        contactSteps = new PrestaShopContactSteps(page);
    }

    @AfterMethod
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test(description = "SCRUM-T26: მომხმარებლის მხარდაჭერის მოთხოვნა ფაილის მიმაგრებით")
    public void submitCustomerSupportInquiry() {
        homeSteps.waitForShop();
        String storeEmail = homeSteps.extractStoreEmail();
        homeSteps.goToContactUs();
        contactSteps
                .validateFormDisplayed()
                .fillForm(Constants.PRESTASHOP_SUBJECT, storeEmail, Constants.PRESTASHOP_MESSAGE)
                .attachFile(attachmentFile())
                .send()
                .validateSuccess(Constants.PRESTASHOP_SUCCESS_MESSAGE);
    }

    private Path attachmentFile() {
        return Paths.get("src", "test", "resources", "prestashop", Constants.PRESTASHOP_ATTACHMENT_FILE);
    }
}
