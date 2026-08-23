package ge.tbc.testautomation.pages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

public class PrestaShopContactPage {
    public Locator subjectSelect;
    public Locator emailInput;
    public Locator messageInput;
    public Locator attachmentInput;
    public Locator sendBtn;
    public Locator successAlert;
    public Locator gdprCheckbox;

    public PrestaShopContactPage(FrameLocator shop) {
        subjectSelect = shop.locator("#id_contact").or(shop.locator("#contact-us-subject-select"));
        emailInput = shop.locator("#email").or(shop.locator("#contact-us-email-input"));
        messageInput = shop.locator("textarea[name='message']");
        attachmentInput = shop.locator("input[name='fileUpload']");
        sendBtn = shop.locator("[name='submitMessage']");
        successAlert = shop.locator(".alert-success");
        gdprCheckbox = shop.getByRole(AriaRole.CHECKBOX);
    }
}
