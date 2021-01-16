package pp.inzynierka.douslug

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_settings.*
import pp.inzynierka.douslug.data.LoginHelper
import pp.inzynierka.douslug.db.DBController

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settings_back_button.setOnClickListener {
            finish()
        }

        val loggedUser = LoginHelper.getLoggedUserOrLogout(this)

        if (loggedUser != null) {
            val sharedPref = getSharedPreferences("app_shared", Context.MODE_PRIVATE)
            var calendarMode = sharedPref.getString("calendar_mode", "month")

            settingsEmail.setText(loggedUser.email)
            settingsOfficialName.setText(loggedUser.official_name)
            settingsPhoneNumber.setText(loggedUser.phone_num)

            if (calendarMode == "month")
                radioCalendarMonth.isChecked = true
            if (calendarMode == "week")
                radioCalendarWeek.isChecked = true
            if (calendarMode == "day")
                radioCalendarDay.isChecked = true

            settings_save.setOnClickListener {
                Realm.getDefaultInstance().executeTransaction {
                    if (settingsOfficialName.text.toString() != loggedUser.official_name) {
                        loggedUser.official_name = settingsOfficialName.text.toString()
                    }
                    if (settingsPhoneNumber.text.toString() != loggedUser.phone_num) {
                        loggedUser.phone_num = settingsPhoneNumber.text.toString()
                    }
                }

                if (radioCalendarMonth.isChecked)
                    calendarMode = "month"
                if (radioCalendarWeek.isChecked)
                    calendarMode = "week"
                if (radioCalendarDay.isChecked)
                    calendarMode = "day"

                with (sharedPref.edit()) {
                    putString("calendar_mode", calendarMode)
                    apply()
                }

                Toast.makeText(
                    this.applicationContext,
                    R.string.settings_saved,
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }

    }

}