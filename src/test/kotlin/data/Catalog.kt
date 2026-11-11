package data

import model.Money
import model.Product

object Catalog {
    val BACKPACK = Product("Sauce Labs Backpack", Money.parse("29.99"))
    val BIKE_LIGHT = Product("Sauce Labs Bike Light", Money.parse("9.99"))
    val BOLT_TSHIRT = Product("Sauce Labs Bolt T-Shirt", Money.parse("15.99"))
    val FLEECE_JACKET = Product("Sauce Labs Fleece Jacket", Money.parse("49.99"))
    val ONESIE = Product("Sauce Labs Onesie", Money.parse("7.99"))
    val RED_TSHIRT = Product("Test.allTheThings() T-Shirt (Red)", Money.parse("15.99"))

    val all = listOf(BACKPACK, BIKE_LIGHT, BOLT_TSHIRT, FLEECE_JACKET, ONESIE, RED_TSHIRT)
}
