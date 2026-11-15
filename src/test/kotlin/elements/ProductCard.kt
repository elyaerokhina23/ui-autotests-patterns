package elements

import com.codeborne.selenide.SelenideElement
import locators.Locators.dataTest
import locators.Locators.dataTestStartsWith
import model.Money
import model.Product

class ProductCard(root: SelenideElement) : BaseElement(root) {
    private val name = Label(root.find(dataTest("inventory-item-name")))
    private val price = Label(root.find(dataTest("inventory-item-price")))

    // у карточки одна кнопка, data-test меняется с "add-to-cart-..." на "remove-..." после клика
    private val addButton = Button(root.find(dataTestStartsWith("add-to-cart-")))
    private val removeButton = Button(root.find(dataTestStartsWith("remove-")))

    fun toProduct() = Product(name.text, Money.parse(price.text))

    fun addToCart() {
        addButton.click()
        removeButton.shouldBeVisible()
    }

    fun removeFromCart() {
        removeButton.click()
        addButton.shouldBeVisible()
    }
}
