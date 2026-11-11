package pages

import com.codeborne.selenide.Selenide.element
import elements.Button
import elements.Input
import elements.Label
import model.User

class LoginPage : LoadablePage<LoginPage>("/") {
    private val usernameInput = Input(element("#user-name"))
    private val passwordInput = Input(element("#password"))
    private val loginButton = Button(element("#login-button"))
    private val error = Label(element("[data-test='error']"))

    override fun isLoaded() {
        loginButton.shouldBeVisible()
    }

    private fun submit(user: User) {
        usernameInput.fill(user.username)
        passwordInput.fill(user.password)
        loginButton.click()
    }

    fun loginAs(user: User): InventoryPage {
        submit(user)
        return InventoryPage().shouldBeOpened()
    }

    fun loginExpectingError(user: User): LoginPage {
        submit(user)
        return this
    }

    fun shouldShowError(message: String): LoginPage {
        error.shouldContainText(message)
        return this
    }
}
