package pages

import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import elements.Button
import elements.CartItems
import elements.Label
import locators.Locators.dataTest
import model.Product

class CartPage : LoadablePage<CartPage>("/cart.html") {
    private val title = Label(element(dataTest("title")))
    private val items = CartItems(elements("${dataTest("cart-list")} ${dataTest("inventory-item")}"))
    private val checkoutButton = Button(element(dataTest("checkout")))

    override fun isLoaded() {
        title.shouldHaveText("Your Cart")
    }

    fun shouldContainExactly(products: List<Product>): CartPage {
        items.shouldContainExactly(products)
        return this
    }

    fun checkout(): CheckoutInfoPage {
        checkoutButton.click()
        return CheckoutInfoPage().shouldBeOpened()
    }
}
