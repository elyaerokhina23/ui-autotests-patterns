package locators

// все локаторы строятся через data-test - этот атрибут не меняется при правках верстки и стилей
object Locators {

    // css по умолчанию: короткий, быстрый и читаемый
    fun dataTest(id: String) = "[data-test='$id']"

    // для элементов, у которых data-test зависит от товара, например "add-to-cart-sauce-labs-backpack"
    fun dataTestStartsWith(prefix: String) = "[data-test^='$prefix']"

    // xpath только там, где css не справится - поиск элемента по тексту вложенного узла
    fun xpathDataTest(id: String) = "*[@data-test='$id']"

    // безопасная подстановка текста в xpath, если в нем есть кавычки
    fun xpathLiteral(text: String): String = when {
        '\'' !in text -> "'$text'"
        '"' !in text -> "\"$text\""
        else -> text.split('\'').joinToString(", \"'\", ", "concat(", ")") { "'$it'" }
    }
}
