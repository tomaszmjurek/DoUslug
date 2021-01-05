package pp.inzynierka.douslug.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.calendar_top_layout.*
import kotlinx.android.synthetic.main.change_calendar_type.*
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.VisitListView
import java.text.SimpleDateFormat
import java.util.*

var titles = arrayOf(
    "John Lennon", "Maryla Rodowicz", "Krzysztof Ibisz"
)
var dates = arrayOf(
    "20.11.2020 12:30 - 13:30", "20.11.2020 13:30 - 14:30", "20.11.2020 14:30 - 16:00"
)

var notes = arrayOf(
    "włosy posklejane czymś czerwonym", "najpierw trzeba rozmrozić", "5 razy musisz mu wszystko potwierdzać"
)

class CalendarDayActivity : AppCompatActivity() {
    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_day)

        val adapter = VisitListView(this, titles, dates, notes)
        visits_list_view.adapter = adapter

        calendar_type_button.setOnClickListener { showCalendarChange() }
        day_button.setOnClickListener { showCalendarChange() }
        week_button.setOnClickListener { openCalendarWeekActivity() }
        month_button.setOnClickListener { openCalendarMonthActivity() }
        back_button.setOnClickListener { onBackPressed() }
    }

    override fun onStart() {
        super.onStart()
        selectedDate = intent.getStringExtra("selectedDate") ?: getCurrentDate()
        title_text_view.text = selectedDate
    }

    //todo move to DateConverter
    private fun getCurrentDate() : String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        return sdf.format(calendar.time)
    }

    private fun showCalendarChange() {
        if (calendar_type_layout.visibility == View.GONE) {
            calendar_type_layout.visibility = View.VISIBLE
            calendar_type_text.visibility = View.VISIBLE
        } else {
            calendar_type_layout.visibility = View.GONE
            calendar_type_text.visibility = View.GONE
        }
    }

    private fun openCalendarMonthActivity() {
        val intent = Intent(this@CalendarDayActivity, CalendarMonthActivity::class.java)
        startActivity(intent)
    }

    private fun openCalendarWeekActivity() {
        val intent = Intent(this@CalendarDayActivity, CalendarWeekActivity::class.java)
        startActivity(intent)
    }
}