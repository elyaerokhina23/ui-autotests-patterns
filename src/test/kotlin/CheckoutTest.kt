import data.Catalog
import data.CustomerFactory
import data.Users
import model.Product
import model.totalPrice
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import pages.CartPage
import pages.LoginPage
import java.util.stream.Stream

class CheckoutTest : BaseTest() {

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("orders")
    fun orderIsPlaced(products: List<Product>) {
        val overview = LoginPage().get()
            .loginAs(Users.standard)
            .addToCart(products)
            .shouldHaveCartCount(products.size)
            .openCart()
            .shouldContainExactly(products)
            .checkout()
            .fill(CustomerFactory.valid())
            .continueToOverview()
            .shouldContainExactly(products)

        assertEquals(products.totalPrice(), overview.itemTotal())

        overview.finish().shouldShowThankYou()
    }

    @Test
    fun removedProductIsNotInCart() {
        LoginPage().get()
            .loginAs(Users.standard)
            .addToCart(Catalog.BACKPACK, Catalog.ONESIE)
            .removeFromCart(Catalog.BACKPACK)
            .shouldHaveCartCount(1)
            .openCart()
            .shouldContainExactly(listOf(Catalog.ONESIE))
    }

    @Test
    fun postalCodeIsRequired() {
        LoginPage().get()
            .loginAs(Users.standard)
            .addToCart(Catalog.BIKE_LIGHT)

        // корзину открываем сразу по url, без клика по иконке
        CartPage().get()
            .checkout()
            .fill(CustomerFactory.withoutPostalCode())
            .continueExpectingError()
            .shouldShowError("Postal Code is required")
    }

    companion object {
        @JvmStatic
        fun orders(): Stream<List<Product>> = Stream.of(
            listOf(Catalog.BACKPACK),
            listOf(Catalog.BIKE_LIGHT, Catalog.BOLT_TSHIRT),
            listOf(Catalog.FLEECE_JACKET, Catalog.ONESIE, Catalog.RED_TSHIRT),
        )
    }
}
