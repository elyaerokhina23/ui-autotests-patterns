package pages

import com.codeborne.selenide.Selenide.element
import elements.Label
import locators.Locators.dataTest

class CheckoutCompletePage : LoadablePage<CheckoutCompletePage>("/checkout-complete.html") {
    private val title = Label(element(dataTest("title")))
    private val header = Label(element(dataTest("complete-header")))

    override fun isLoaded() {
        title.shouldHaveText("Checkout: Complete!")
    }

    fun shouldShowThankYou(): CheckoutCompletePage {
        header.shouldHaveText("Thank you for your order!")
        return this
    }
}
