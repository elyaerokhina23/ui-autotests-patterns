package data

import model.User

enum class UserType {
    STANDARD,
    LOCKED_OUT,
}

object Users {
    private const val PASSWORD = "secret_sauce"

    private val users = mapOf(
        UserType.STANDARD to User("standard_user", PASSWORD),
        UserType.LOCKED_OUT to User("locked_out_user", PASSWORD),
    )

    operator fun get(type: UserType): User = users.getValue(type)

    val standard get() = get(UserType.STANDARD)
}
