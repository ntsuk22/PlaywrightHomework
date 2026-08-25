package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ForgotPasswordPage {
    public Locator form;
    public Locator title;
    public Locator emailInput;
    public Locator submitBtn;
    public Locator successAlert;

    public ForgotPasswordPage(Page page) {
        form = page.locator("[data-test='forgot-password-form']");
        title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Forgot Password"));
        emailInput = page.locator("[data-test='forgot-password-form'] [data-test='email']");
        submitBtn = page.locator("[data-test='forgot-password-submit']");
        successAlert = page.locator(".alert-success");
    }
}
