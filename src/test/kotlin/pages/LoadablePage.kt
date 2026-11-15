package pages

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.WebDriverRunner
import java.net.URI

// Loadable Component - страница знает свой адрес и как проверить, что она загрузилась
abstract class LoadablePage<T : LoadablePage<T>>(private val path: String) {

    protected abstract fun isLoaded()

    protected open fun load() {
        Selenide.open(path)
    }

    // открывает страницу, только если мы сейчас не на ней
    fun get(): T {
        if (!isCurrentPage()) {
            load()
        }
        return shouldBeOpened()
    }

    fun shouldBeOpened(): T {
        isLoaded()
        return self()
    }

    private fun isCurrentPage(): Boolean =
        WebDriverRunner.hasWebDriverStarted() && URI(WebDriverRunner.url()).path == path

    @Suppress("UNCHECKED_CAST")
    private fun self(): T = this as T
}
