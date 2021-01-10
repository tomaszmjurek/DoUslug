package pp.inzynierka.douslug.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import org.mindrot.jbcrypt.BCrypt

import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.appUser

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_username.afterTextChanged { error_email.text = "" }
        register_official_name.afterTextChanged { error_official_name.text = "" }
        register_phone.afterTextChanged { error_phone.text = "" }
        register_password.afterTextChanged { error_password.text = "" }
        register_password2.afterTextChanged { error_password_repeat.text = "" }

        register.setOnClickListener {
            val username = register_username.text.toString()
            val official_name = register_official_name.text.toString()
            val password = register_password.text.toString()
            val password2 = register_password2.text.toString()
            val phone = register_phone.text.toString()

            var error = false
            if (TextUtils.isEmpty(username) || !android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                error = true
                error_email.text = getString(R.string.email_error)
            } else {
                val user = DBController.findUserByEmail(username);
                if (user != null) {
                    error = true
                    error_email.text = getString(R.string.email_duplicate_error)
                }
            }

            if (TextUtils.isEmpty(official_name) || TextUtils.isDigitsOnly(official_name)) {
                error = true
                error_official_name.text = getString(R.string.official_name_error)
            }

            if (TextUtils.isEmpty(phone) || !android.util.Patterns.PHONE.matcher(phone).matches()) {
                error = true
                error_phone.text = getString(R.string.phone_error)
            }

            if (TextUtils.isEmpty(password) || TextUtils.getTrimmedLength(password) < 8) {
                error = true
                error_password.text = getString(R.string.password_error)
            }

            if (password2 != password) {
                error = true
                error_password_repeat.text = getString(R.string.password_repeat_error)
            }


            if (!error) {
                val passwordhash = BCrypt.hashpw(password, BCrypt.gensalt())
                val newUser = appUser(username, official_name, passwordhash, phone)
                DBController.insertUser(newUser)

                Toast.makeText(
                    applicationContext,
                    "Pomyślnie zarejestrowano, możesz się teraz zalogować",
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }

    }
}