data class Parent(
    val id: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val street: String? = null,
    val city: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val child: Array<Child>? = null
)