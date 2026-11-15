# ui-autotests-patterns

ДЗ №7 - автотесты для [saucedemo.com](https://www.saucedemo.com) с локаторами по правилам (XPath/CSS) и JUnit 5

Стек: Kotlin, Selenide, JUnit 5, Gradle

## Запуск

```
./gradlew test
```

Без открытия браузера:

```
./gradlew test -Dselenide.headless=true
```

Нужны Chrome и JDK 17

## Что изменилось в ДЗ №7

Все локаторы переписаны по правилам ниже, сценарии и паттерны из ДЗ №6 сохранены

### Правила локаторов

1. Основной атрибут - `data-test`, он есть на всех нужных элементах saucedemo и не зависит от верстки и стилей
2. Не используются классы оформления (`.title`, `.inventory_item`, `.cart_item` и т.п.) - их могут переименовать при редизайне
3. Не используются `id`, теги и тексты кнопок, если есть `data-test`
4. По умолчанию CSS - он короче и быстрее, XPath только там, где CSS не справится
5. Никаких абсолютных путей и индексов вида `div[3]/div[2]` - только относительные локаторы
6. Коллекции ищутся внутри своего контейнера, например `[data-test='cart-list'] [data-test='inventory-item']`, чтобы не зацепить лишнее
7. Вложенные элементы ищутся от корня своего компонента (`root.find(...)`), а не от всей страницы
8. Локаторы собираются в одном месте - `locators/Locators.kt`, поэтому формат легко поменять сразу везде

### Где XPath и почему

Карточка товара в каталоге ищется по названию товара внутри нее:

```
//*[@data-test='inventory-list']
  //*[@data-test='inventory-item'][.//*[@data-test='inventory-item-name'][normalize-space()='Sauce Labs Backpack']]
```

В CSS нет поиска по тексту и условия "элемент, внутри которого есть потомок с таким текстом", поэтому здесь XPath

- `*` вместо тега - локатор не ломается, если `div` заменят на другой тег
- `normalize-space()` убирает лишние пробелы вокруг названия
- название подставляется через `xpathLiteral`, поэтому кавычки в названии не ломают выражение

Раньше для этого перебирались все карточки и сравнивались их названия, теперь карточка находится одним запросом

### Кнопка в карточке товара

Раньше кнопка искалась по тегу `button` и проверялась по тексту "Add to cart"/"Remove"
Теперь используется префикс `data-test`: `[data-test^='add-to-cart-']` и `[data-test^='remove-']`, так как атрибут меняется после клика и не зависит от языка интерфейса

### Было - стало

| Элемент | Было | Стало |
|---|---|---|
| Логин | `#user-name`, `#password`, `#login-button` | `[data-test='username']`, `[data-test='password']`, `[data-test='login-button']` |
| Заголовок страницы | `.title` | `[data-test='title']` |
| Товары в каталоге | `.inventory_item` | `[data-test='inventory-list'] [data-test='inventory-item']` |
| Карточка по названию | перебор всех карточек | XPath, см. выше |
| Название и цена товара | `.inventory_item_name`, `.inventory_item_price` | `[data-test='inventory-item-name']`, `[data-test='inventory-item-price']` |
| Кнопка в карточке | `button` + текст | `[data-test^='add-to-cart-']`, `[data-test^='remove-']` |
| Корзина в шапке | `.shopping_cart_link`, `.shopping_cart_badge` | `[data-test='shopping-cart-link']`, `[data-test='shopping-cart-badge']` |
| Позиции в корзине | `.cart_item` | `[data-test='cart-list'] [data-test='inventory-item']` |
| Поля покупателя | `#first-name`, `#last-name`, `#postal-code` | `[data-test='firstName']`, `[data-test='lastName']`, `[data-test='postalCode']` |
| Кнопки | `#checkout`, `#continue`, `#finish` | `[data-test='checkout']`, `[data-test='continue']`, `[data-test='finish']` |
| Сумма заказа | `.summary_subtotal_label` | `[data-test='subtotal-label']` |
| Заказ оформлен | `.complete-header` | `[data-test='complete-header']` |

### JUnit

- `@DisplayName` на классах и тестах - в отчете понятные русские названия
- `@ParameterizedTest` с `@MethodSource` и `@EnumSource` - один сценарий на разных данных
- `@BeforeEach`/`@AfterEach` в `BaseTest` - настройка и закрытие браузера для каждого теста

## Что проверяется

- логин: обычный пользователь видит каталог, заблокированный пользователь и неверные данные дают ошибку
- сортировка каталога по имени и цене (все 4 варианта)
- оформление заказа для нескольких наборов товаров, сумма Item total сходится с ценами
- удаление товара из корзины
- обязательные поля на шаге оформления заказа

## Паттерны

**Page Object** - `pages/`, отдельный класс на каждую страницу

**Loadable Component** - `pages/LoadablePage.kt`, у страницы есть путь и проверка `isLoaded()`, а `get()` открывает страницу, только если мы сейчас не на ней (например, в `CheckoutTest` корзина открывается сразу по url через `CartPage().get()`)

**Page Element / Wrapper** - `elements/`, простые обертки над `SelenideElement` (`Button`, `Input`, `Label`, `Dropdown`) и составные элементы: карточка товара `ProductCard`, список позиций `CartItems`, шапка с корзиной `Header`

**Chain of invocations** - методы страниц возвращают эту же страницу или следующую, поэтому сценарий пишется одной цепочкой (см. `CheckoutTest.orderIsPlaced`)

**Value Object** - `model/`: `Money`, `Product`, `User`, `Customer`, `SortOrder`, `Money` хранит сумму в центах, чтобы не складывать цены в Double

**Data Provider** - параметризованные тесты через `@MethodSource` (`LoginTest`, `CheckoutTest`) и `@EnumSource` (`SortingTest`)

**Data Registry** - `data/Users.kt` (пользователи по типу) и `data/Catalog.kt` (товары с ценами)

**Factory** - `data/CustomerFactory.kt`, создает данные покупателя: валидные и с пустыми полями

## Структура

```
src/test/kotlin
├── data/       тестовые данные
├── elements/   обертки над элементами
├── locators/   построение локаторов
├── model/      value objects
├── pages/      page objects
└── *Test.kt    тесты
```
