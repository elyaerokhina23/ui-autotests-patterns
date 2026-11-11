import data.Catalog
import data.CustomerFactory
import data.Users
import model.Customer
import model.Product
import model.totalPrice
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import pages.CartPage
import pages.LoginPage
import java.util.stream.Stream

class CheckoutTest : BaseTest() {

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("orders")
    fun orderIsPlaced(products: List<Product>) {
        LoginPage().get()
            .loginAs(Users.standard)
            .addToCart(products)
            .shouldHaveCartCount(products.size)
            .openCart()
            .shouldContainExactly(products)
            .checkout()
            .fill(CustomerFactory.valid())
            .continueToOverview()
            .shouldContainExactly(products)
            .shouldHaveItemTotal(products.totalPrice())
            .finish()
            .shouldShowThankYou()
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

    @ParameterizedTest(name = "{1}")
    @MethodSource("incompleteCustomers")
    fun checkoutRequiresCustomerInfo(customer: Customer, expectedError: String) {
        LoginPage().get()
            .loginAs(Users.standard)
            .addToCart(Catalog.BIKE_LIGHT)

        // корзину открываем сразу по url, без клика по иконке
        CartPage().get()
            .checkout()
            .fill(customer)
            .continueExpectingError()
            .shouldShowError(expectedError)
    }

    companion object {
        @JvmStatic
        fun orders(): Stream<List<Product>> = Stream.of(
            listOf(Catalog.BACKPACK),
            listOf(Catalog.BIKE_LIGHT, Catalog.BOLT_TSHIRT),
            listOf(Catalog.FLEECE_JACKET, Catalog.ONESIE, Catalog.RED_TSHIRT),
        )

        @JvmStatic
        fun incompleteCustomers(): Stream<Arguments> = Stream.of(
            Arguments.of(CustomerFactory.withoutFirstName(), "First Name is required"),
            Arguments.of(CustomerFactory.withoutLastName(), "Last Name is required"),
            Arguments.of(CustomerFactory.withoutPostalCode(), "Postal Code is required"),
        )
    }
}
