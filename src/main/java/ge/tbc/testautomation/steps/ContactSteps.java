package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.pages.ContactPage;

import java.nio.file.Path;

public class ContactSteps {
    Page page;
    ContactPage contactPage;

    public ContactSteps(Page page) {
        this.page = page;
        contactPage = new ContactPage(page);
    }

    public ContactSteps validateContactFormDisplayed() {
        PlaywrightAssertions.assertThat(contactPage.firstNameInput).isVisible();
        PlaywrightAssertions.assertThat(contactPage.lastNameInput).isVisible();
        PlaywrightAssertions.assertThat(contactPage.emailInput).isVisible();
        PlaywrightAssertions.assertThat(contactPage.subjectSelect).isVisible();
        PlaywrightAssertions.assertThat(contactPage.messageInput).isVisible();
        PlaywrightAssertions.assertThat(contactPage.attachmentInput).isVisible();
        PlaywrightAssertions.assertThat(contactPage.submitBtn).isVisible();

        return this;
    }

    public ContactSteps submit() {
        contactPage.submitBtn.click();

        return this;
    }

    public ContactSteps validateRequiredFieldErrors() {
        PlaywrightAssertions.assertThat(contactPage.firstNameError).isVisible();
        PlaywrightAssertions.assertThat(contactPage.lastNameError).isVisible();
        PlaywrightAssertions.assertThat(contactPage.emailError).isVisible();
        PlaywrightAssertions.assertThat(contactPage.subjectError).isVisible();
        PlaywrightAssertions.assertThat(contactPage.messageError).isVisible();

        return this;
    }

    public ContactSteps fillIdentity(String firstName, String lastName, String email) {
        fillAndBlur(contactPage.firstNameInput, firstName);
        fillAndBlur(contactPage.lastNameInput, lastName);
        fillAndBlur(contactPage.emailInput, email);
        PlaywrightAssertions.assertThat(contactPage.firstNameError).isHidden();
        PlaywrightAssertions.assertThat(contactPage.lastNameError).isHidden();
        PlaywrightAssertions.assertThat(contactPage.emailError).isHidden();

        return this;
    }

    public ContactSteps selectSubject(String subject) {
        contactPage.subjectSelect.selectOption(subject);
        PlaywrightAssertions.assertThat(contactPage.subjectError).isHidden();

        return this;
    }

    public ContactSteps fillMessage(String message) {
        fillAndBlur(contactPage.messageInput, message);
        PlaywrightAssertions.assertThat(contactPage.messageError).isHidden();

        return this;
    }

    public ContactSteps attachFile(Path file) {
        contactPage.attachmentInput.setInputFiles(file);

        return this;
    }

    public ContactSteps validateAttachmentTypeError() {
        PlaywrightAssertions.assertThat(contactPage.attachmentError).isVisible();
        PlaywrightAssertions.assertThat(contactPage.attachmentError).containsText(Constants.CONTACT_ATTACHMENT_TYPE_ERROR);

        return this;
    }

    public ContactSteps validateAttachmentAccepted() {
        PlaywrightAssertions.assertThat(contactPage.attachmentError).isHidden();

        return this;
    }

    public ContactSteps validateSuccess(String message) {
        PlaywrightAssertions.assertThat(contactPage.successAlert).isVisible();
        PlaywrightAssertions.assertThat(contactPage.successAlert).containsText(message);
        PlaywrightAssertions.assertThat(contactPage.submitBtn).isHidden();

        return this;
    }

    public ContactSteps validateFormReset() {
        PlaywrightAssertions.assertThat(contactPage.firstNameInput).isVisible();
        PlaywrightAssertions.assertThat(contactPage.firstNameInput).hasValue("");
        PlaywrightAssertions.assertThat(contactPage.lastNameInput).hasValue("");
        PlaywrightAssertions.assertThat(contactPage.emailInput).hasValue("");
        PlaywrightAssertions.assertThat(contactPage.messageInput).hasValue("");
        PlaywrightAssertions.assertThat(contactPage.successAlert).isHidden();

        return this;
    }

    private void fillAndBlur(Locator locator, String value) {
        locator.click();
        locator.fill(value);
        locator.press("Tab");
    }
}
