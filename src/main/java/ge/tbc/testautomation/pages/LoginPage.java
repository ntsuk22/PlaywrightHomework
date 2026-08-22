package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    public Locator emailInput;
    public Locator passwordInput;
    public Locator loginBtn;
    public Locator registerLink;
    public Locator forgotPasswordLink;

    public LoginPage(Page page) {
        emailInput = page.locator("[data-test='email']");
        passwordInput = page.locator("[data-test='password']");
        loginBtn = page.locator("[data-test='login-submit']");
        registerLink = page.locator("[data-test='register-link']");
        forgotPasswordLink = page.locator("[data-test='forgot-password-link']");
    }
}
