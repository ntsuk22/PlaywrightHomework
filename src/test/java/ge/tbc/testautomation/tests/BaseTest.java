package ge.tbc.testautomation.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import ge.tbc.testautomation.data.Constants;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);
        browser = playwright.chromium().launch(launchOptions);
        browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080));
        page = browserContext.newPage();
        page.navigate(Constants.BASE_URL);
    }

    @AfterClass
    public void tearDown() {
        page.close();
        browser.close();
        playwright.close();
    }
}
