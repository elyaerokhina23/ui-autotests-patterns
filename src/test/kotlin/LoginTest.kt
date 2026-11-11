import data.Catalog
import data.UserType
import data.Users
import model.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import pages.LoginPage
import java.util.stream.Stream

class LoginTest : BaseTest() {

    @Test
    fun standardUserSeesWholeCatalog() {
        LoginPage().get()
            .loginAs(Users.standard)
            .shouldHaveItems(Catalog.all.size)
            .shouldHaveCartCount(0)
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @MethodSource("invalidCredentials")
    fun loginFails(user: User, expectedError: String) {
        LoginPage().get()
            .loginExpectingError(user)
            .shouldShowError(expectedError)
            .shouldBeOpened()
    }

    companion object {
        @JvmStatic
        fun invalidCredentials(): Stream<Arguments> = Stream.of(
            Arguments.of(Users[UserType.LOCKED_OUT], "Sorry, this user has been locked out."),
            Arguments.of(User("standard_user", "wrong_password"), "Username and password do not match"),
            Arguments.of(User("", "secret_sauce"), "Username is required"),
            Arguments.of(User("standard_user", ""), "Password is required"),
        )
    }
}
