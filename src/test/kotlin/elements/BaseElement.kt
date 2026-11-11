package elements

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.SelenideElement

abstract class BaseElement(protected val root: SelenideElement) {

    fun shouldBeVisible() {
        root.shouldBe(visible)
    }
}
