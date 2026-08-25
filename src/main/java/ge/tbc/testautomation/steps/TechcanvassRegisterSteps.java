package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import ge.tbc.testautomation.pages.TechcanvassRegisterPage;
import io.qameta.allure.Step;

public class TechcanvassRegisterSteps {
    Page page;
    TechcanvassRegisterPage registerPage;

    public TechcanvassRegisterSteps(Page page) {
        this.page = page;
        this.registerPage = new TechcanvassRegisterPage(page);
        page.onDialog(dialog -> dialog.accept());
    }

    @Step("Open the Techcanvass registration page")
    public TechcanvassRegisterSteps open() {
        page.navigate("https://techcanvass.com/examples/register.html");
        registerPage.firstNameInput.waitFor();

        return this;
    }

    @Step("Fill registration form from database row")
    public TechcanvassRegisterSteps fillForm(String firstName, String lastName, String gender, String model,
                                             String address1, String address2, String city,
                                             String contact1, String contact2) {
        fillField(registerPage.firstNameInput, firstName);
        fillField(registerPage.lastNameInput, lastName);
        registerPage.genderRadio(gender).check();
        selectModel(model);
        fillField(registerPage.address1Input, address1);
        fillField(registerPage.address2Input, address2);
        fillField(registerPage.cityInput, city);
        fillField(registerPage.contact1Input, contact1);
        fillField(registerPage.contact2Input, contact2);

        return this;
    }

    @Step("Submit the registration form")
    public TechcanvassRegisterSteps submit() {
        registerPage.registerBtn.click();

        return this;
    }

    private void selectModel(String model) {
        Locator matchingOption = registerPage.modelSelect.locator("option")
                .filter(new Locator.FilterOptions().setHasText(model));
        if (matchingOption.count() > 0) {
            registerPage.modelSelect.selectOption(new SelectOption().setLabel(matchingOption.first().innerText().trim()));
            return;
        }
        registerPage.modelSelect.selectOption(new SelectOption().setIndex(0));
    }

    private void fillField(Locator locator, String value) {
        locator.click();
        locator.fill(value);
    }
}
