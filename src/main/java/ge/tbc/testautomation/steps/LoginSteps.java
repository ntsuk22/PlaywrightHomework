package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.LoginPage;
import io.qameta.allure.Step;

public class LoginSteps {
    Page page;
    LoginPage loginPage;

    public LoginSteps(Page page) {
        this.page = page;
        loginPage = new LoginPage(page);
    }

    @Step("Validate login page")
    public LoginSteps validateLoginPage() {
        PlaywrightAssertions.assertThat(loginPage.emailInput).isVisible();
        PlaywrightAssertions.assertThat(loginPage.passwordInput).isVisible();
        PlaywrightAssertions.assertThat(loginPage.loginBtn).isVisible();
        PlaywrightAssertions.assertThat(loginPage.registerLink).isVisible();
        PlaywrightAssertions.assertThat(loginPage.forgotPasswordLink).isVisible();

        return this;
    }

    @Step("Go to register")
    public LoginSteps goToRegister() {
        loginPage.registerLink.click();

        return this;
    }

    @Step("Go to forgot password")
    public LoginSteps goToForgotPassword() {
        loginPage.forgotPasswordLink.click();

        return this;
    }

    @Step("Fill login credentials")
    public LoginSteps fillLoginCredentials(String username, String password) {
        loginPage.loginBtn.waitFor();
        loginPage.emailInput.fill(username);
        loginPage.passwordInput.fill(password);

        return this;
    }

    @Step("Click login")
    public LoginSteps logIn() {
        loginPage.loginBtn.click();

        return this;
    }
}
