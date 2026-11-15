package model

data class Product(val name: String, val price: Money) {
    override fun toString() = "$name ($price)"
}

fun List<Product>.totalPrice(): Money = map { it.price }.sum()
