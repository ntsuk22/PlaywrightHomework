package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.ForgotPasswordPage;

public class ForgotPasswordSteps {
    Page page;
    ForgotPasswordPage forgotPasswordPage;

    public ForgotPasswordSteps(Page page) {
        this.page = page;
        forgotPasswordPage = new ForgotPasswordPage(page);
    }

    public ForgotPasswordSteps validateForgotPasswordPage() {
        PlaywrightAssertions.assertThat(forgotPasswordPage.form).isVisible();
        PlaywrightAssertions.assertThat(forgotPasswordPage.title).isVisible();
        PlaywrightAssertions.assertThat(forgotPasswordPage.emailInput).isVisible();
        PlaywrightAssertions.assertThat(forgotPasswordPage.submitBtn).isVisible();

        return this;
    }

    public ForgotPasswordSteps requestReset(String email) {
        forgotPasswordPage.emailInput.fill(email);
        forgotPasswordPage.submitBtn.click();

        return this;
    }

    public ForgotPasswordSteps validateResetConfirmation() {
        PlaywrightAssertions.assertThat(forgotPasswordPage.successAlert).isVisible();

        return this;
    }
}
