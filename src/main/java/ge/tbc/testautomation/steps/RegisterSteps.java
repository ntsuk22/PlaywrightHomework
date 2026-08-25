package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.pages.RegisterPage;

public class RegisterSteps {
    Page page;
    RegisterPage registerPage;

    public RegisterSteps(Page page) {
        this.page = page;
        registerPage = new RegisterPage(page);
    }

    public RegisterSteps validateRegistrationFormDisplayed() {
        PlaywrightAssertions.assertThat(registerPage.firstNameInput).isVisible();
        PlaywrightAssertions.assertThat(registerPage.lastNameInput).isVisible();
        PlaywrightAssertions.assertThat(registerPage.dobInput).isVisible();
        PlaywrightAssertions.assertThat(registerPage.streetInput).isVisible();
        PlaywrightAssertions.assertThat(registerPage.postalCodeInput).isVisible();
        PlaywrightAssertions.assertThat(registerPage.cityInput).isVisible();
        PlaywrightAssertions.assertThat(registerPage.stateInput).isVisible();
        PlaywrightAssertions.assertThat(registerPage.countrySelect).isVisible();
        PlaywrightAssertions.assertThat(registerPage.phoneInput).isVisible();
        PlaywrightAssertions.assertThat(registerPage.emailInput).isVisible();
        PlaywrightAssertions.assertThat(registerPage.passwordInput).isVisible();

        return this;
    }

    public RegisterSteps fillInvalidEmailAndPassword(String email, String password) {
        fillAndBlur(registerPage.emailInput, email);
        fillAndBlur(registerPage.passwordInput, password);

        return this;
    }

    public RegisterSteps validateValidationErrors() {
        PlaywrightAssertions.assertThat(registerPage.emailError).isVisible();
        PlaywrightAssertions.assertThat(registerPage.passwordError).isVisible();
        PlaywrightAssertions.assertThat(page.getByText(Constants.PASSWORD_RULES_HEADING)).isVisible();
        PlaywrightAssertions.assertThat(page.getByText(Constants.PASSWORD_RULE_LENGTH)).isVisible();
        PlaywrightAssertions.assertThat(page.getByText(Constants.PASSWORD_RULE_CASE)).isVisible();
        PlaywrightAssertions.assertThat(page.getByText(Constants.PASSWORD_RULE_NUMBER)).isVisible();
        PlaywrightAssertions.assertThat(page.getByText(Constants.PASSWORD_RULE_SYMBOL)).isVisible();

        return this;
    }

    public RegisterSteps fillRegistrationForm(String firstName, String lastName, String dateOfBirth,
                                              String country, String postalCode, String houseNumber,
                                              String street, String city, String state, String phone,
                                              String email, String password) {
        fillAndBlur(registerPage.firstNameInput, firstName);
        fillAndBlur(registerPage.lastNameInput, lastName);
        fillAndBlur(registerPage.dobInput, dateOfBirth);
        registerPage.countrySelect.selectOption(country);
        fillAndBlur(registerPage.postalCodeInput, postalCode);
        fillAndBlur(registerPage.houseNumberInput, houseNumber);
        waitForPostcodeLookup();
        fillAndBlur(registerPage.streetInput, street);
        fillAndBlur(registerPage.cityInput, city);
        fillAndBlur(registerPage.stateInput, state);
        fillAndBlur(registerPage.phoneInput, phone);
        fillAndBlur(registerPage.emailInput, email);
        fillAndBlur(registerPage.passwordInput, password);

        return this;
    }

    public RegisterSteps submit() {
        registerPage.registerBtn.click();

        return this;
    }

    private void fillAndBlur(Locator locator, String value) {
        locator.click();
        locator.fill(value);
        locator.press("Tab");
    }

    private void waitForPostcodeLookup() {
        Locator loading = page.locator("[data-test='postcode-lookup-loading']");
        loading.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }
}
