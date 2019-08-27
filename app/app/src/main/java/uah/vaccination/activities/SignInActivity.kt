package uah.vaccination.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.email_edit_text
import kotlinx.android.synthetic.main.activity_sign_up.*
import uah.vaccination.R

class SignInActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().currentUser ?: return
        startMainActivity(true)
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
            startMainActivity(false)
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
                    startMainActivity(true)
                }
            }
    }

    private fun startMainActivity(addFlags: Boolean) {
        val intent = Intent(this, SignUpActivity::class.java)
        if (addFlags) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        finish()
        startActivity(intent)
    }

}



