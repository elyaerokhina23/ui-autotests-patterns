package pages

import com.codeborne.selenide.Selenide.element
import elements.Button
import elements.Input
import elements.Label
import locators.Locators.dataTest
import model.Customer

class CheckoutInfoPage : LoadablePage<CheckoutInfoPage>("/checkout-step-one.html") {
    private val title = Label(element(dataTest("title")))
    private val firstName = Input(element(dataTest("firstName")))
    private val lastName = Input(element(dataTest("lastName")))
    private val postalCode = Input(element(dataTest("postalCode")))
    private val continueButton = Button(element(dataTest("continue")))
    private val error = Label(element(dataTest("error")))

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
