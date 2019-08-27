package uah.vaccination.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import uah.vaccination.R

class SignInActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().currentUser ?: return
        navigateToActivity(true, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        sign_in_button.setOnClickListener {
            if (validateInput()) {
                signIn(email_edit_text.text.toString(), password_edit_text.text.toString())
            }
        }

        createAnAccount_text_view.setOnClickListener {
            navigateToActivity(false, SignUpActivity::class.java)
        }

    }

    private fun validateInput(): Boolean {
        val isEmailEmpty = email_edit_text.text.isNullOrEmpty()
        val isPasswordEmpty = password_edit_text.text.isNullOrEmpty()

        if (!isEmailEmpty &&
            !isPasswordEmpty
        )
            return true
        else {
            if (isEmailEmpty) email_edit_text.error = "Required Field."
            if (isPasswordEmpty) password_edit_text.error = "Required Field."
        }
        return false
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    navigateToActivity(true, MainActivity::class.java)
                }
            }
    }

    private fun navigateToActivity(addFlags: Boolean, activity: Class<out AppCompatActivity>) {
        val intent = Intent(this, activity)
        if (addFlags) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        finish()
        startActivity(intent)
    }

}



