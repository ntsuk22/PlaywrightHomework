package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TechcanvassRegisterPage {
    public Locator firstNameInput;
    public Locator lastNameInput;
    public Locator modelSelect;
    public Locator address1Input;
    public Locator address2Input;
    public Locator cityInput;
    public Locator contact1Input;
    public Locator contact2Input;
    public Locator registerBtn;
    private Page page;

    public TechcanvassRegisterPage(Page page) {
        this.page = page;
        Locator textInputs = page.locator("#login input[type='text']");
        firstNameInput = textInputs.nth(0);
        lastNameInput = textInputs.nth(1);
        address1Input = textInputs.nth(2);
        address2Input = textInputs.nth(3);
        cityInput = textInputs.nth(4);
        contact1Input = textInputs.nth(5);
        contact2Input = textInputs.nth(6);
        modelSelect = page.locator("select[name='model']");
        registerBtn = page.locator("input[type='submit'][value='Register']");
    }

    public Locator genderRadio(String gender) {
        return page.locator("input[name='gender'][value='" + gender.toLowerCase() + "']");
    }
}
