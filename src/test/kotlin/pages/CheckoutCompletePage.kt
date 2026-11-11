package pages

import com.codeborne.selenide.Selenide.element
import elements.Label

class CheckoutCompletePage : LoadablePage<CheckoutCompletePage>("/checkout-complete.html") {
    private val title = Label(element(".title"))
    private val header = Label(element(".complete-header"))

    override fun isLoaded() {
        title.shouldHaveText("Checkout: Complete!")
    }

    fun shouldShowThankYou(): CheckoutCompletePage {
        header.shouldHaveText("Thank you for your order!")
        return this
    }
}
