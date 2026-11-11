package elements

import com.codeborne.selenide.SelenideElement
import model.Money
import model.Product

class ProductCard(root: SelenideElement) : BaseElement(root) {
    private val name = Label(root.find(".inventory_item_name"))
    private val price = Label(root.find(".inventory_item_price"))
    private val actionButton = Button(root.find("button"))

    fun toProduct() = Product(name.text, Money.parse(price.text))

    fun addToCart() {
        actionButton.shouldHaveText("Add to cart")
        actionButton.click()
        actionButton.shouldHaveText("Remove")
    }

    fun removeFromCart() {
        actionButton.shouldHaveText("Remove")
        actionButton.click()
        actionButton.shouldHaveText("Add to cart")
    }
}
