package uah.vaccination.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signing.*
import uah.vaccination.R

class SigningActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signing)

        sign_up_button.setOnClickListener {
            if (validateInput()) {
                val intent = Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                finish()
                startActivity(intent)
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

}
