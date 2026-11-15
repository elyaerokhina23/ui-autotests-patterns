package pages

import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import elements.Button
import elements.CartItems
import elements.Label
import locators.Locators.dataTest
import model.Money
import model.Product

class CheckoutOverviewPage : LoadablePage<CheckoutOverviewPage>("/checkout-step-two.html") {
    private val title = Label(element(dataTest("title")))
    private val items = CartItems(elements("${dataTest("cart-list")} ${dataTest("inventory-item")}"))
    private val subtotal = Label(element(dataTest("subtotal-label")))
    private val finishButton = Button(element(dataTest("finish")))

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
