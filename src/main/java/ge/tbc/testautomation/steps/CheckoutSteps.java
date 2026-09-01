package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.pages.CheckoutPage;
import io.qameta.allure.Step;

public class CheckoutSteps {
    Page page;
    CheckoutPage checkoutPage;

    public CheckoutSteps(Page page) {
        this.page = page;
        checkoutPage = new CheckoutPage(page);
    }

    @Step("Validate checkout sign-in step")
    public CheckoutSteps validateSignInStep() {
        PlaywrightAssertions.assertThat(checkoutPage.guestTab).isVisible();

        return this;
    }

    @Step("Continue checkout as guest")
    public CheckoutSteps continueAsGuest(String email, String firstName, String lastName) {
        checkoutPage.guestTab.click();
        fillAndBlur(checkoutPage.guestEmailInput, email);
        fillAndBlur(checkoutPage.guestFirstNameInput, firstName);
        fillAndBlur(checkoutPage.guestLastNameInput, lastName);
        checkoutPage.guestSubmitBtn.click();
        PlaywrightAssertions.assertThat(checkoutPage.proceedAsGuestBtn).isEnabled();
        checkoutPage.proceedAsGuestBtn.click();

        return this;
    }

    @Step("Fill billing address")
    public CheckoutSteps fillBillingAddress(String country, String postalCode, String houseNumber,
                                            String street, String city, String state) {
        checkoutPage.countrySelect.selectOption(country);
        fillAndBlur(checkoutPage.postalCodeInput, postalCode);
        fillAndBlur(checkoutPage.houseNumberInput, houseNumber);
        waitForPostcodeLookup();
        fillAndBlur(checkoutPage.streetInput, street);
        fillAndBlur(checkoutPage.cityInput, city);
        fillAndBlur(checkoutPage.stateInput, state);
        PlaywrightAssertions.assertThat(checkoutPage.proceedToPaymentBtn).isEnabled();
        checkoutPage.proceedToPaymentBtn.click();

        return this;
    }

    @Step("Complete payment with {paymentMethod}")
    public CheckoutSteps completePayment(String paymentMethod) {
        checkoutPage.paymentMethodSelect.waitFor();
        checkoutPage.paymentMethodSelect.selectOption(paymentMethod);
        PlaywrightAssertions.assertThat(checkoutPage.finishBtn).isEnabled();
        checkoutPage.finishBtn.click();
        checkoutPage.paymentSuccessMessage.waitFor();
        checkoutPage.finishBtn.click();

        return this;
    }

    @Step("Validate order confirmation")
    public CheckoutSteps validateOrderConfirmation() {
        PlaywrightAssertions.assertThat(checkoutPage.orderConfirmation.or(checkoutPage.paymentSuccessMessage)).isVisible();

        return this;
    }

    private void fillAndBlur(Locator locator, String value) {
        locator.click();
        locator.fill(value);
        locator.press("Tab");
    }

    private void waitForPostcodeLookup() {
        Locator loading = page.locator("[data-test='postcode-lookup-loading']");
        loading.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }
}
