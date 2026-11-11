import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

abstract class BaseTest {

    @BeforeEach
    fun setUp() {
        Configuration.baseUrl = "https://www.saucedemo.com"
        Configuration.browserSize = "1920x1080"
        Configuration.timeout = 6000
    }

    @AfterEach
    fun tearDown() {
        Selenide.closeWebDriver()
    }
}
