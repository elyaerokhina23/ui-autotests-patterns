package pages

import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import elements.Button
import elements.CartItems
import elements.Label
import model.Money
import model.Product

class CheckoutOverviewPage : LoadablePage<CheckoutOverviewPage>("/checkout-step-two.html") {
    private val title = Label(element(".title"))
    private val items = CartItems(elements(".cart_item"))
    private val subtotal = Label(element(".summary_subtotal_label"))
    private val finishButton = Button(element("#finish"))

    override fun isLoaded() {
        title.shouldHaveText("Checkout: Overview")
    }

    fun shouldContainExactly(products: List<Product>): CheckoutOverviewPage {
        items.shouldContainExactly(products)
        return this
    }

    fun shouldHaveItemTotal(expected: Money): CheckoutOverviewPage {
        subtotal.shouldHaveText("Item total: $expected")
        return this
    }

    fun finish(): CheckoutCompletePage {
        finishButton.click()
        return CheckoutCompletePage().shouldBeOpened()
    }
}
