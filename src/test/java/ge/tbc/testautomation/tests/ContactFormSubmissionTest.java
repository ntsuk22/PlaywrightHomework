package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.ContactSteps;
import ge.tbc.testautomation.steps.HomeSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

@Epic("Playwright Homework")
@Feature("Contact form")
public class ContactFormSubmissionTest extends BaseTest {
    HomeSteps homeSteps;
    ContactSteps contactSteps;

    @BeforeClass
    public void innerSetup() {
        homeSteps = new HomeSteps(page);
        contactSteps = new ContactSteps(page);
    }

    @Description("SCRUM-T25: submit the Toolshop contact form with validation and attachment checks.")
    @Test(description = "SCRUM-T25: საკონტაქტო ფორმის გაგზავნა და ვალიდაცია")
    public void submitContactFormWithValidation() {
        homeSteps.goToContact();
        contactSteps.validateContactFormDisplayed();
        contactSteps
                .submit()
                .validateRequiredFieldErrors();
        contactSteps
                .fillIdentity(Constants.FIRST_NAME, Constants.LAST_NAME, Constants.GUEST_EMAIL)
                .selectSubject(Constants.CONTACT_SUBJECT)
                .fillMessage(Constants.CONTACT_MESSAGE);
        contactSteps
                .attachFile(contactFile(Constants.INVALID_ATTACHMENT_FILE))
                .validateAttachmentTypeError();
        contactSteps
                .attachFile(contactFile(Constants.VALID_ATTACHMENT_FILE))
                .validateAttachmentAccepted()
                .submit()
                .validateSuccess(Constants.CONTACT_SUCCESS_MESSAGE);

        homeSteps.goToHome();
        homeSteps.goToContact();
        contactSteps.validateFormReset();
    }

    private Path contactFile(String fileName) {
        return Paths.get("src", "test", "resources", "contact", fileName);
    }
}
