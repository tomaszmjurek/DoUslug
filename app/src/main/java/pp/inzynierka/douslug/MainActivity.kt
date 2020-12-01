package pp.inzynierka.douslug

import android.content.Intent
import android.view.Window
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.right_drawer_menu.*
import pp.inzynierka.douslug.calendar.CalendarMonthActivity
import pp.inzynierka.douslug.db.DBTestActivity


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

        imageView.setOnClickListener {
            toggleRightDrawer()
        }

        val adapter = VisitListView(this, titles, dates, notes)
        upcomingVisitsList.adapter = adapter

        imageButton.setOnClickListener{ openCalendarView() }

        drawer_menu_close.setOnClickListener{ drawerLayout.closeDrawer(rightDrawerMenu) }
        drawer_menu_calendar.setOnClickListener { openCalendarView() }

    }

    private fun openCalendarView() {
//        val intent = Intent(this@MainActivity, CalendarMonthActivity::class.java)
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