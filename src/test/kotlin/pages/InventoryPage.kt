package pages

import com.codeborne.selenide.CollectionCondition.size
import com.codeborne.selenide.CollectionCondition.sizeGreaterThan
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import elements.Dropdown
import elements.Header
import elements.Label
import elements.ProductCard
import model.Product
import model.SortOrder

class InventoryPage : LoadablePage<InventoryPage>("/inventory.html") {
    private val title = Label(element(".title"))
    private val items = elements(".inventory_item")
    private val sort = Dropdown(element("[data-test='product-sort-container']"))

    val header = Header()

    override fun isLoaded() {
        title.shouldHaveText("Products")
    }

    private fun cards(): List<ProductCard> {
        items.shouldHave(sizeGreaterThan(0))
        return items.asFixedIterable().map { ProductCard(it) }
    }

    private fun card(product: Product): ProductCard =
        cards().firstOrNull { it.toProduct().name == product.name }
            ?: throw AssertionError("Товар '${product.name}' не найден в каталоге")

    fun products(): List<Product> = cards().map { it.toProduct() }

    fun shouldHaveItems(count: Int): InventoryPage {
        items.shouldHave(size(count))
        return this
    }

    fun sortBy(order: SortOrder): InventoryPage {
        sort.selectByValue(order.value)
        return this
    }

    fun addToCart(products: List<Product>): InventoryPage {
        products.forEach { card(it).addToCart() }
        return this
    }

    fun addToCart(vararg products: Product): InventoryPage = addToCart(products.toList())

    fun removeFromCart(product: Product): InventoryPage {
        card(product).removeFromCart()
        return this
    }

    fun shouldHaveCartCount(count: Int): InventoryPage {
        header.shouldHaveCartCount(count)
        return this
    }

    fun openCart(): CartPage = header.openCart()
}
