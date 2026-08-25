package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.ForgotPasswordPage;
import io.qameta.allure.Step;

public class ForgotPasswordSteps {
    Page page;
    ForgotPasswordPage forgotPasswordPage;

    public ForgotPasswordSteps(Page page) {
        this.page = page;
        forgotPasswordPage = new ForgotPasswordPage(page);
    }

    @Step("Validate forgot password page")
    public ForgotPasswordSteps validateForgotPasswordPage() {
        PlaywrightAssertions.assertThat(forgotPasswordPage.form).isVisible();
        PlaywrightAssertions.assertThat(forgotPasswordPage.title).isVisible();
        PlaywrightAssertions.assertThat(forgotPasswordPage.emailInput).isVisible();
        PlaywrightAssertions.assertThat(forgotPasswordPage.submitBtn).isVisible();

        return this;
    }

    @Step("Request password reset for {email}")
    public ForgotPasswordSteps requestReset(String email) {
        forgotPasswordPage.emailInput.fill(email);
        forgotPasswordPage.submitBtn.click();

        return this;
    }

    @Step("Validate password reset confirmation")
    public ForgotPasswordSteps validateResetConfirmation() {
        PlaywrightAssertions.assertThat(forgotPasswordPage.successAlert).isVisible();

        return this;
    }
}
