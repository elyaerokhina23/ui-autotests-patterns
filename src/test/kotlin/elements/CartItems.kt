package elements

import com.codeborne.selenide.CollectionCondition.size
import com.codeborne.selenide.ElementsCollection
import model.Money
import model.Product
import org.junit.jupiter.api.Assertions.assertEquals

// список позиций на странице корзины и на шаге Checkout: Overview
class CartItems(private val items: ElementsCollection) {

    fun products(): List<Product> = items.asFixedIterable().map {
        Product(
            name = it.find(".inventory_item_name").text(),
            price = Money.parse(it.find(".inventory_item_price").text()),
        )
    }

    fun shouldContainExactly(expected: List<Product>) {
        items.shouldHave(size(expected.size))
        assertEquals(expected.sortedBy { it.name }, products().sortedBy { it.name })
    }
}
