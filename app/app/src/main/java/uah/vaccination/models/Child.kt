package uah.vaccination.Models

import com.google.firebase.Timestamp


data class Child(
    val id: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val dateOfBirth: Timestamp? = null,
    val parentId : String? = null

)