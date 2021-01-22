package pp.inzynierka.douslug

import android.content.Context
import android.content.Intent
import android.view.Window
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.right_drawer_menu.*
import pp.inzynierka.douslug.calendar.CalendarDayActivity
import pp.inzynierka.douslug.calendar.CalendarMonthActivity
import pp.inzynierka.douslug.db.DBTestActivity

import pp.inzynierka.douslug.calendar.CalendarWeekActivity
import pp.inzynierka.douslug.data.LoginHelper


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

        val loggedUser = LoginHelper.getLoggedUserOrLogout(this)

        imageView.setOnClickListener {
            toggleRightDrawer()
        }

        val adapter = VisitListView(this, titles, dates, notes)
        upcomingVisitsList.adapter = adapter

        imageButton.setOnClickListener{ openCalendarView() }
        imageButton2.setOnClickListener { openDbTest() }

        drawer_menu_close.setOnClickListener{ drawerLayout.closeDrawer(rightDrawerMenu) }
        drawer_menu_calendar.setOnClickListener { openCalendarView() }
        drawer_menu_settings.setOnClickListener { openSettings() }
        drawer_menu_finances.setOnClickListener { openFinances() }

        logout.setOnClickListener {
            LoginHelper.logout(this)
        }

    }

    private fun openCalendarView() {
        val sharedPref = getSharedPreferences("app_shared", Context.MODE_PRIVATE)
        val calendarMode = sharedPref.getString("calendar_mode", "month")
        when (calendarMode) {
            "month" -> open(CalendarMonthActivity::class.java)
            "week" -> open(CalendarWeekActivity::class.java)
            "day" -> open(CalendarDayActivity::class.java)
        }
    }

    private fun openSettings() {
        open(SettingsActivity::class.java)
    }

    private fun openDbTest() {
        open(DBTestActivity::class.java)
    }

    private fun openFinances() {
        open(FinanceActivity::class.java)
    }

    private fun open(activity: Class<out AppCompatActivity>) {
        val intent = Intent(this@MainActivity, activity)
        startActivity(intent)
        drawerLayout.closeDrawer(rightDrawerMenu)
    }

    private fun toggleRightDrawer() {
        if (drawerLayout.isDrawerOpen(rightDrawerMenu)) {
            drawerLayout.closeDrawer(rightDrawerMenu)
        } else {
            drawerLayout.openDrawer(rightDrawerMenu)
        }
    }
}