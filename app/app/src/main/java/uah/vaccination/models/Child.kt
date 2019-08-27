package uah.vaccination.models

import com.google.firebase.Timestamp

data class Child(
    val name: String? = null,
    val surname: String? = null,
    val dateOfBirth: Timestamp? = null
)