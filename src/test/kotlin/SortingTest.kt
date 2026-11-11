import data.Catalog
import data.Users
import model.SortOrder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import pages.LoginPage

class SortingTest : BaseTest() {

    @ParameterizedTest
    @EnumSource(SortOrder::class)
    fun productsAreSorted(order: SortOrder) {
        val products = LoginPage().get()
            .loginAs(Users.standard)
            .sortBy(order)
            .products()

        assertEquals(Catalog.all.sortedWith(order.comparator).map { it.price }, products.map { it.price })
        assertEquals(products.sortedWith(order.comparator), products)
    }
}
