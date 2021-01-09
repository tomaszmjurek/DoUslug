package pp.inzynierka.douslug

import android.content.Context
import android.content.Intent
import android.view.Window
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.right_drawer_menu.*
import pp.inzynierka.douslug.calendar.CalendarMonthActivity
import pp.inzynierka.douslug.db.DBTestActivity
import pp.inzynierka.douslug.ui.login.LoginActivity


class MainActivity : AppCompatActivity() {

    var titles = arrayOf(
        "John Lennon", "Maryla Rodowicz", "Krzysztof Ibisz"
    )
    var dates = arrayOf(
        "20.11.2020 12:30 - 13:30", "20.11.2020 13:30 - 14:30", "20.11.2020 14:30 - 16:00"
    )
    var notes = arrayOf(
        "włosy posklejane czymś czerwonym", "najpierw trzeba rozmrozić", "5 razy musisz mu wszystko potwierdzać"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this@MainActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main)

        val sharedPref = this.getSharedPreferences("app_shared", Context.MODE_PRIVATE)
        val userToken = sharedPref.getString("user_token", null);
        Toast.makeText(
            applicationContext,
            "TEST: $userToken",
            Toast.LENGTH_LONG
        ).show()

        imageView.setOnClickListener {
            toggleRightDrawer()
        }

        val adapter = VisitListView(this, titles, dates, notes)
        upcomingVisitsList.adapter = adapter

        imageButton.setOnClickListener{ openCalendarView() }
        imageButton2.setOnClickListener { openDbTest() }

        drawer_menu_close.setOnClickListener{ drawerLayout.closeDrawer(rightDrawerMenu) }
        drawer_menu_calendar.setOnClickListener { openCalendarView() }

        logout.setOnClickListener {
            logout()
        }



    }

    private fun logout() {
        val sharedPref = this.getSharedPreferences("app_shared", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("user_token")
            apply()
        }

        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)

        Toast.makeText(
            applicationContext,
            "Pomyślnie wylogowano",
            Toast.LENGTH_LONG
        ).show()


        finish()
    }

    private fun openCalendarView() {
        val intent = Intent(this@MainActivity, CalendarMonthActivity::class.java)
        startActivity(intent)
    }

    private fun openDbTest() {
        val intent = Intent(this@MainActivity, DBTestActivity::class.java)
        startActivity(intent)
    }

    private fun toggleRightDrawer() {
        if (drawerLayout.isDrawerOpen(rightDrawerMenu)) {
            drawerLayout.closeDrawer(rightDrawerMenu)
        } else {
            drawerLayout.openDrawer(rightDrawerMenu)
        }
    }
}