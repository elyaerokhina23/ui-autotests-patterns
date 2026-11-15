package elements

import com.codeborne.selenide.Condition.exactText
import com.codeborne.selenide.Condition.exist
import com.codeborne.selenide.Selenide.element
import locators.Locators.dataTest
import pages.CartPage

class Header {
    private val cartLink = Button(element(dataTest("shopping-cart-link")))
    private val cartBadge = element(dataTest("shopping-cart-badge"))

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
