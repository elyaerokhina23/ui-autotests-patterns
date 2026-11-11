package elements

import com.codeborne.selenide.Condition.exactText
import com.codeborne.selenide.Condition.exist
import com.codeborne.selenide.Selenide.element
import pages.CartPage

class Header {
    private val cartLink = Button(element(".shopping_cart_link"))
    private val cartBadge = element(".shopping_cart_badge")

    fun shouldHaveCartCount(count: Int) {
        if (count == 0) {
            cartBadge.shouldNot(exist)
        } else {
            cartBadge.shouldHave(exactText(count.toString()))
        }
    }

    fun openCart(): CartPage {
        cartLink.click()
        return CartPage().shouldBeOpened()
    }
}
