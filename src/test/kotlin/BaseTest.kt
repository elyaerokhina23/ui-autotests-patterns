import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.chrome.ChromeOptions

abstract class BaseTest {

    @BeforeEach
    fun setUp() {
        Configuration.baseUrl = "https://www.saucedemo.com"
        Configuration.browserSize = "1920x1080"
        Configuration.timeout = 6000
        // иначе хром после логина показывает окно "смените пароль" (secret_sauce есть в базах утечек)
        Configuration.browserCapabilities = ChromeOptions().setExperimentalOption(
            "prefs",
            mapOf(
                "credentials_enable_service" to false,
                "profile.password_manager_enabled" to false,
                "profile.password_manager_leak_detection" to false,
            ),
        )
    }

    @AfterEach
    fun tearDown() {
        Selenide.closeWebDriver()
    }
}
