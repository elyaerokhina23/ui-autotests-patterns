package model

enum class SortOrder(val value: String, val comparator: Comparator<Product>) {
    NAME_A_TO_Z("az", compareBy<Product> { it.name }),
    NAME_Z_TO_A("za", compareByDescending<Product> { it.name }),
    PRICE_LOW_TO_HIGH("lohi", compareBy<Product> { it.price }),
    PRICE_HIGH_TO_LOW("hilo", compareByDescending<Product> { it.price }),
}
