package model

@JvmInline
value class Money(val cents: Long) : Comparable<Money> {

    operator fun plus(other: Money) = Money(cents + other.cents)

    override fun compareTo(other: Money) = cents.compareTo(other.cents)

    override fun toString() = "\$%d.%02d".format(cents / 100, cents % 100)

    companion object {
        val ZERO = Money(0)

        // "$29.99", "Item total: $39.98", "7.99"
        fun parse(text: String): Money {
            val amount = text.substringAfter('$').trim()
            val dollars = amount.substringBefore('.')
            val rest = amount.substringAfter('.', "0").padEnd(2, '0')
            return Money(dollars.toLong() * 100 + rest.toLong())
        }
    }
}

fun Iterable<Money>.sum(): Money = fold(Money.ZERO) { acc, m -> acc + m }
