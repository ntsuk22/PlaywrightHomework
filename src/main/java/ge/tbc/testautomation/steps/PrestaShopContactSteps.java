package ge.tbc.testautomation.steps;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.SelectOption;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.pages.PrestaShopContactPage;
import io.qameta.allure.Step;

import java.nio.file.Path;

public class PrestaShopContactSteps {
    Page page;
    FrameLocator shop;
    PrestaShopContactPage contactPage;

    public PrestaShopContactSteps(Page page) {
        this.page = page;
        this.shop = page.frameLocator("iframe[name='" + Constants.PRESTASHOP_FRAME_NAME + "']");
        this.contactPage = new PrestaShopContactPage(shop);
    }

    @Step("Validate PrestaShop contact form")
    public PrestaShopContactSteps validateFormDisplayed() {
        PlaywrightAssertions.assertThat(contactPage.emailInput).isVisible();
        PlaywrightAssertions.assertThat(contactPage.messageInput).isVisible();
        PlaywrightAssertions.assertThat(contactPage.attachmentInput).isVisible();
        PlaywrightAssertions.assertThat(contactPage.sendBtn).isVisible();

        return this;
    }

    @Step("Fill PrestaShop contact form")
    public PrestaShopContactSteps fillForm(String subject, String email, String message) {
        if (contactPage.subjectSelect.count() > 0) {
            contactPage.subjectSelect.selectOption(new SelectOption().setLabel(subject));
        }
        contactPage.emailInput.fill(email);
        contactPage.messageInput.fill(message);
        acceptConsentIfPresent();

        return this;
    }

    @Step("Attach file to PrestaShop contact form")
    public PrestaShopContactSteps attachFile(Path file) {
        contactPage.attachmentInput.setInputFiles(file);

        return this;
    }

    @Step("Send PrestaShop contact form")
    public PrestaShopContactSteps send() {
        contactPage.sendBtn.click();

        return this;
    }

    @Step("Validate PrestaShop success message")
    public PrestaShopContactSteps validateSuccess(String message) {
        PlaywrightAssertions.assertThat(contactPage.successAlert).isVisible();
        PlaywrightAssertions.assertThat(contactPage.successAlert).containsText(message);

        return this;
    }

    private void acceptConsentIfPresent() {
        if (contactPage.gdprCheckbox.count() == 0) {
            return;
        }
        Locator checkbox = contactPage.gdprCheckbox.first();
        if (checkbox.isVisible() && !checkbox.isChecked()) {
            checkbox.check();
        }
    }
}
