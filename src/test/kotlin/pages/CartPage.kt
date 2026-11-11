package pages

import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import elements.Button
import elements.CartItems
import elements.Label
import model.Product

class CartPage : LoadablePage<CartPage>("/cart.html") {
    private val title = Label(element(".title"))
    private val items = CartItems(elements(".cart_item"))
    private val checkoutButton = Button(element("#checkout"))

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
