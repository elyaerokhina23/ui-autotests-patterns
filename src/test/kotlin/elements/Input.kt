package elements

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.SelenideElement

class Input(root: SelenideElement) : BaseElement(root) {

    fun fill(value: String) {
        root.shouldBe(visible).setValue(value)
    }
}
