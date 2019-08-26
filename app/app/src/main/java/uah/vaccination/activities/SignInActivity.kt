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

    private lateinit var auth: FirebaseAuth
    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().currentUser ?: return

        val intent = Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        finish()
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        sign_in_button.setOnClickListener {
            if(validateInput()){
                SignIn(email_edit_text.text.toString(),password_edit_text.text.toString())
            }
        }

        createAnAccount_text_view.setOnClickListener {
            StartMainActivity()
        }

    }

    fun validateInput() :Boolean{
        val isEmailEmpty = email_edit_text.text.isNullOrEmpty()
        val isPasswordEmpty = password_edit_text.text.isNullOrEmpty()

        if (!isEmailEmpty &&
            !isPasswordEmpty)
            return true
        else {
            if (isEmailEmpty) email_edit_text.error = "Required Field."
            if (isPasswordEmpty) password_edit_text.error = "Required Field."
        }
        return false
    }
    fun SignIn(email:String, password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    StartMainActivity()
                }
            }
    }
    fun StartMainActivity(){
        val intent = Intent(this, SignUpActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        finish()
        startActivity(intent)
    }


}



