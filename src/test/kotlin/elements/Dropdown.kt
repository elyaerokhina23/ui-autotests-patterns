package elements

import com.codeborne.selenide.SelenideElement

class Dropdown(root: SelenideElement) : BaseElement(root) {

    fun selectByValue(value: String) {
        root.selectOptionByValue(value)
    }
}
