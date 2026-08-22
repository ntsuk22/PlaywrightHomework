package ge.tbc.testautomation.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import ge.tbc.testautomation.data.Constants;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class IsolatedBaseTest {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(true);
        browser = playwright.chromium().launch(launchOptions);
    }

    @BeforeMethod
    public void setUpContext() {
        browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080));
        page = browserContext.newPage();
        page.navigate(Constants.BASE_URL);
    }

    @AfterMethod
    public void tearDownContext() {
        page.close();
        browserContext.close();
    }

    @AfterClass
    public void tearDown() {
        browser.close();
        playwright.close();
    }
}
