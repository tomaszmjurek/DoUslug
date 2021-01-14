package pp.inzynierka.douslug.data

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.appUser
import pp.inzynierka.douslug.ui.login.LoginActivity

object LoginHelper {

    fun getLoggedUserOrNull(activity: AppCompatActivity): appUser? {
        val sharedPref = activity.getSharedPreferences("app_shared", Context.MODE_PRIVATE)
        val userToken = sharedPref.getString("user_token", null);
        if (userToken != null) {
            val loggedUser = DBController.findUserByUserId(userToken)
            return loggedUser
        }
        return null
    }

    fun logout(activity: AppCompatActivity, toast: Boolean = true, redirect: Boolean = true, finish: Boolean = true) {
        val sharedPref = activity.getSharedPreferences("app_shared", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("user_token")
            apply()
        }

        if (redirect) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
        if (toast) {
            Toast.makeText(
                activity.applicationContext,
                R.string.logout_success,
                Toast.LENGTH_LONG
            ).show()
        }

        if (finish) {
            activity.finish()
        }
    }

}