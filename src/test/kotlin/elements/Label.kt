package elements

import com.codeborne.selenide.Condition
import com.codeborne.selenide.SelenideElement

class Label(root: SelenideElement) : BaseElement(root) {

    val text: String
        get() = root.text()

    fun shouldHaveText(expected: String) {
        root.shouldHave(Condition.exactText(expected))
    }

    fun shouldContainText(expected: String) {
        root.shouldHave(Condition.text(expected))
    }
}
