package data

import model.Customer
import kotlin.random.Random

object CustomerFactory {
    private val firstNames = listOf("Anna", "Ivan", "Maria", "Oleg", "Daria", "Pavel")
    private val lastNames = listOf("Smirnova", "Petrov", "Volkova", "Sokolov", "Orlova", "Kuznetsov")

    fun valid() = Customer(
        firstName = firstNames.random(),
        lastName = lastNames.random(),
        postalCode = Random.nextInt(100000, 999999).toString(),
    )

    fun withoutFirstName() = valid().copy(firstName = "")

    fun withoutPostalCode() = valid().copy(postalCode = "")
}
