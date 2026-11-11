package elements

import com.codeborne.selenide.Condition.enabled
import com.codeborne.selenide.Condition.exactText
import com.codeborne.selenide.SelenideElement

class Button(root: SelenideElement) : BaseElement(root) {

    val text: String
        get() = root.text()

    fun click() {
        root.shouldBe(enabled).click()
    }

    fun shouldHaveText(expected: String) {
        root.shouldHave(exactText(expected))
    }
}
