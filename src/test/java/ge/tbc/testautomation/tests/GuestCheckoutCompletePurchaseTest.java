package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.CartSteps;
import ge.tbc.testautomation.steps.CheckoutSteps;
import ge.tbc.testautomation.steps.HomeSteps;
import ge.tbc.testautomation.steps.ProductSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Playwright Homework")
@Feature("Guest checkout")
public class GuestCheckoutCompletePurchaseTest extends BaseTest {
    HomeSteps homeSteps;
    ProductSteps productSteps;
    CartSteps cartSteps;
    CheckoutSteps checkoutSteps;

    @BeforeClass
    public void innerSetup() {
        homeSteps = new HomeSteps(page);
        productSteps = new ProductSteps(page);
        cartSteps = new CartSteps(page);
        checkoutSteps = new CheckoutSteps(page);
    }

    @Description("SCRUM-T23: complete a Toolshop purchase without signing in.")
    @Test(description = "SCRUM-T23: შეკვეთის სრული პროცესი ავტორიზაციის გარეშე")
    public void completePurchaseAsGuest() {
        homeSteps.validateCatalogDisplayed();
        homeSteps
                .searchFor(Constants.SEARCH_QUERY)
                .validateSearchResultsContain(Constants.SEARCH_QUERY);
        homeSteps
                .selectCategory(Constants.FIRST_CATEGORY)
                .selectBrand(Constants.EXPECTED_BRAND_TAG)
                .openProduct(Constants.GUEST_CHECKOUT_PRODUCT);

        productSteps.validateProductDetails();
        double unitPrice = productSteps.getUnitPrice();
        productSteps
                .increaseQuantityTo(Constants.QUANTITY_TWO)
                .addToCart()
                .validateCartToast(Constants.CART_TOAST_MESSAGE);
        homeSteps.validateCartBadge(Constants.QUANTITY_TWO);

        homeSteps.goToCart();
        cartSteps.validateCartItem(Constants.GUEST_CHECKOUT_PRODUCT, Constants.QUANTITY_TWO, unitPrice);
        cartSteps
                .updateQuantity(Constants.QUANTITY_THREE)
                .validateTotal(unitPrice, Constants.QUANTITY_THREE)
                .proceedToCheckout();

        checkoutSteps
                .validateSignInStep()
                .continueAsGuest(Constants.GUEST_EMAIL, Constants.FIRST_NAME, Constants.LAST_NAME)
                .fillBillingAddress(
                        Constants.COUNTRY,
                        Constants.POSTAL_CODE,
                        Constants.HOUSE_NUMBER,
                        Constants.STREET,
                        Constants.CITY,
                        Constants.STATE)
                .completePayment(Constants.PAYMENT_CASH_ON_DELIVERY)
                .validateOrderConfirmation();
    }
}
