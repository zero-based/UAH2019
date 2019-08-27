package uah.vaccination.models

import com.google.firebase.Timestamp

data class Child(
    var name: String? = null,
    val surname: String? = null,
    var dateOfBirth: Timestamp? = null
)