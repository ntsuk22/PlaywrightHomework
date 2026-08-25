package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ContactPage {
    public Locator firstNameInput;
    public Locator lastNameInput;
    public Locator emailInput;
    public Locator subjectSelect;
    public Locator messageInput;
    public Locator attachmentInput;
    public Locator submitBtn;
    public Locator firstNameError;
    public Locator lastNameError;
    public Locator emailError;
    public Locator subjectError;
    public Locator messageError;
    public Locator attachmentError;
    public Locator successAlert;

    public ContactPage(Page page) {
        firstNameInput = page.locator("[data-test='first-name']");
        lastNameInput = page.locator("[data-test='last-name']");
        emailInput = page.locator("[data-test='email']");
        subjectSelect = page.locator("[data-test='subject']");
        messageInput = page.locator("[data-test='message']");
        attachmentInput = page.locator("[data-test='attachment']");
        submitBtn = page.locator("[data-test='contact-submit']");
        firstNameError = page.locator("[data-test='first-name-error']");
        lastNameError = page.locator("[data-test='last-name-error']");
        emailError = page.locator("[data-test='email-error']");
        subjectError = page.locator("[data-test='subject-error']");
        messageError = page.locator("[data-test='message-error']");
        attachmentError = page.locator("[data-test='attachment-error']");
        successAlert = page.getByRole(AriaRole.ALERT).filter(new Locator.FilterOptions().setHasText("Thanks for your message"));
    }
}
