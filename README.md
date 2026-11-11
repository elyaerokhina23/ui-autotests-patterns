# ui-autotests-patterns

ДЗ №6 — UI-автотесты для [saucedemo.com](https://www.saucedemo.com) с паттернами проектирования.

Стек: Kotlin, Selenide, JUnit 5, Gradle.

## Запуск

```
./gradlew test
```

Без открытия браузера:

```
./gradlew test -Dselenide.headless=true
```

Нужен Chrome и JDK 17.

## Что проверяется

- логин: обычный пользователь видит каталог, заблокированный пользователь и неверные данные дают ошибку
- сортировка каталога по имени и цене (все 4 варианта)
- оформление заказа для нескольких наборов товаров, сумма Item total сходится с ценами
- удаление товара из корзины
- обязательные поля на шаге оформления заказа

## Паттерны

**Page Object** — `pages/`, отдельный класс на каждую страницу.

**Loadable Component** — `pages/LoadablePage.kt`. У страницы есть путь и проверка `isLoaded()`. `get()` открывает страницу, только если мы сейчас не на ней, и проверяет, что она загрузилась. Например, в `CheckoutTest` корзина открывается сразу по url через `CartPage().get()`.

**Page Element / Wrapper** — `elements/`. Простые обертки над `SelenideElement` (`Button`, `Input`, `Label`, `Dropdown`) и составные элементы: карточка товара `ProductCard`, список позиций `CartItems` (используется в корзине и на шаге Overview), шапка с корзиной `Header`.

**Chain of invocations** — методы страниц возвращают либо эту же страницу, либо следующую, поэтому сценарий пишется одной цепочкой (см. `CheckoutTest.orderIsPlaced`).

**Value Object** — `model/`: `Money`, `Product`, `User`, `Customer`, `SortOrder`. `Money` хранит сумму в центах, чтобы не складывать цены в Double.

**Data Provider** — параметризованные тесты через `@MethodSource` (`LoginTest`, `CheckoutTest`) и `@EnumSource` (`SortingTest`).

**Data Registry** — `data/Users.kt` (пользователи по типу) и `data/Catalog.kt` (товары с ценами).

**Factory** — `data/CustomerFactory.kt`, создает данные покупателя для оформления заказа: валидные и с пустыми полями.

## Структура

```
src/test/kotlin
├── data/       тестовые данные
├── elements/   обертки над элементами
├── model/      value objects
├── pages/      page objects
└── *Test.kt    тесты
```
