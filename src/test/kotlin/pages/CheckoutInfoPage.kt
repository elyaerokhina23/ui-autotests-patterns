package pages

import com.codeborne.selenide.Selenide.element
import elements.Button
import elements.Input
import elements.Label
import model.Customer

class CheckoutInfoPage : LoadablePage<CheckoutInfoPage>("/checkout-step-one.html") {
    private val title = Label(element(".title"))
    private val firstName = Input(element("#first-name"))
    private val lastName = Input(element("#last-name"))
    private val postalCode = Input(element("#postal-code"))
    private val continueButton = Button(element("#continue"))
    private val error = Label(element("[data-test='error']"))

    override fun isLoaded() {
        title.shouldHaveText("Checkout: Your Information")
    }

    fun fill(customer: Customer): CheckoutInfoPage {
        firstName.fill(customer.firstName)
        lastName.fill(customer.lastName)
        postalCode.fill(customer.postalCode)
        return this
    }

    fun continueToOverview(): CheckoutOverviewPage {
        continueButton.click()
        return CheckoutOverviewPage().shouldBeOpened()
    }

    fun continueExpectingError(): CheckoutInfoPage {
        continueButton.click()
        return this
    }

    fun shouldShowError(message: String): CheckoutInfoPage {
        error.shouldContainText(message)
        return this
    }
}
