package uah.vaccination.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*
import uah.vaccination.R
import uah.vaccination.models.Parent

class SignUpActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        sign_up_button.setOnClickListener {
            if (validateInput()) {
                signUp(email_edit_text.text.toString(), password_edit_text.text.toString())
            }

        }
    }

    private fun validateInput(): Boolean {
        val isNameEmpty = name_edit_text.text.isNullOrEmpty()
        val isSurnameEmpty = surname_edit_text.text.isNullOrEmpty()
        val isStreetEmpty = street_edit_text.text.isNullOrEmpty()
        val isCityEmpty = city_edit_text.text.isNullOrEmpty()
        val isEmailEmpty = email_edit_text.text.isNullOrEmpty()
        val isPhoneEmpty = phone_edit_text.text.isNullOrEmpty()
        val isPasswordEmpty = password_edit_text.text.isNullOrEmpty()

        if (!isNameEmpty &&
            !isSurnameEmpty &&
            !isStreetEmpty &&
            !isCityEmpty &&
            !isEmailEmpty &&
            !isPhoneEmpty &&
            !isPasswordEmpty
        )
            return true
        else {
            if (isNameEmpty) name_edit_text.error = "Required Field."
            if (isSurnameEmpty) surname_edit_text.error = "Required Field."
            if (isStreetEmpty) street_edit_text.error = "Required Field."
            if (isCityEmpty) city_edit_text.error = "Required Field."
            if (isEmailEmpty) email_edit_text.error = "Required Field."
            if (isPasswordEmpty) password_edit_text.error = "Required Field."
            if (isPhoneEmpty) phone_edit_text.error = "Required Field."
        }
        return false
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val parent = task.result!!.user
                    addParent(
                        parent!!.uid,
                        name_edit_text.text.toString(),
                        surname_edit_text.text.toString(),
                        street_edit_text.text.toString(),
                        city_edit_text.text.toString(),
                        email,
                        phone_edit_text.text.toString()
                    )
                    val intent = Intent(this, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        baseContext, task.exception.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun addParent(
        id: String,
        name: String,
        surname: String,
        street: String,
        city: String,
        email: String,
        phoneNumber: String
    ) {
        val parent = Parent(id, name, surname, street, city, email, phoneNumber)
        firestore.collection("parents").document(email).set(parent)
    }
}
