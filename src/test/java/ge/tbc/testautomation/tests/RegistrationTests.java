package ge.tbc.testautomation.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import ge.tbc.testautomation.data.DatabaseSteps;
import ge.tbc.testautomation.steps.TechcanvassRegisterSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Epic("Playwright Homework")
@Feature("JDBC registration form")
public class RegistrationTests {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    TechcanvassRegisterSteps registerSteps;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
        page = browserContext.newPage();
        registerSteps = new TechcanvassRegisterSteps(page);
    }

    @AfterClass
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

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() throws SQLException {
        DatabaseSteps databaseSteps = new DatabaseSteps();
        try {
            ResultSet resultSet = databaseSteps.selectAllRegistrationData();
            List<Object[]> rows = new ArrayList<>();
            while (resultSet.next()) {
                rows.add(new Object[]{
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getString("model"),
                        resultSet.getString("address1"),
                        resultSet.getString("address2"),
                        resultSet.getString("city"),
                        resultSet.getString("contact1"),
                        resultSet.getString("contact2")
                });
            }
            return rows.toArray(new Object[0][]);
        } finally {
            databaseSteps.close();
        }
    }

    @Description("Fills the Techcanvass registration form once per row returned from RegistrationData.")
    @Test(dataProvider = "registrationData", description = "Fill Techcanvass register form for each RegistrationData row")
    public void fillRegistrationFormFromDatabase(String firstName, String lastName, String gender, String model,
                                                 String address1, String address2, String city,
                                                 String contact1, String contact2) {
        registerSteps
                .open()
                .fillForm(firstName, lastName, gender, model, address1, address2, city, contact1, contact2)
                .submit();
    }
}
