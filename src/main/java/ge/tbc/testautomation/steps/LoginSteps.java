package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.LoginPage;

public class LoginSteps {
    Page page;
    LoginPage loginPage;

    public LoginSteps(Page page) {
        this.page = page;
        loginPage = new LoginPage(page);
    }

    public LoginSteps validateLoginPage() {
        PlaywrightAssertions.assertThat(loginPage.emailInput).isVisible();
        PlaywrightAssertions.assertThat(loginPage.passwordInput).isVisible();
        PlaywrightAssertions.assertThat(loginPage.loginBtn).isVisible();
        PlaywrightAssertions.assertThat(loginPage.registerLink).isVisible();
        PlaywrightAssertions.assertThat(loginPage.forgotPasswordLink).isVisible();

        return this;
    }

    public LoginSteps goToRegister() {
        loginPage.registerLink.click();

        return this;
    }

    public LoginSteps goToForgotPassword() {
        loginPage.forgotPasswordLink.click();

        return this;
    }

    public LoginSteps fillLoginCredentials(String username, String password) {
        loginPage.loginBtn.waitFor();
        loginPage.emailInput.fill(username);
        loginPage.passwordInput.fill(password);

        return this;
    }

    public LoginSteps logIn() {
        loginPage.loginBtn.click();

        return this;
    }
}
