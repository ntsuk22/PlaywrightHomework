package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckoutPage extends CommonPage {
    public Locator guestTab;
    public Locator guestEmailInput;
    public Locator guestFirstNameInput;
    public Locator guestLastNameInput;
    public Locator guestSubmitBtn;
    public Locator proceedAsGuestBtn;
    public Locator countrySelect;
    public Locator postalCodeInput;
    public Locator houseNumberInput;
    public Locator streetInput;
    public Locator cityInput;
    public Locator stateInput;
    public Locator proceedToPaymentBtn;
    public Locator paymentMethodSelect;
    public Locator finishBtn;
    public Locator paymentSuccessMessage;
    public Locator orderConfirmation;

    public CheckoutPage(Page page) {
        super(page);
        guestTab = page.locator("a[href='#guest-tab']");
        guestEmailInput = page.locator("[data-test='guest-email']");
        guestFirstNameInput = page.locator("[data-test='guest-first-name']");
        guestLastNameInput = page.locator("[data-test='guest-last-name']");
        guestSubmitBtn = page.locator("[data-test='guest-submit']");
        proceedAsGuestBtn = page.locator("[data-test='proceed-2-guest']");
        countrySelect = page.locator("[data-test='country']");
        postalCodeInput = page.locator("[data-test='postal_code']");
        houseNumberInput = page.locator("[data-test='house_number']");
        streetInput = page.locator("[data-test='street']");
        cityInput = page.locator("[data-test='city']");
        stateInput = page.locator("[data-test='state']");
        proceedToPaymentBtn = page.locator("[data-test='proceed-3']");
        paymentMethodSelect = page.locator("[data-test='payment-method']");
        finishBtn = page.locator("[data-test='finish']");
        paymentSuccessMessage = page.locator("[data-test='payment-success-message']");
        orderConfirmation = page.locator("#order-confirmation");
    }
}
