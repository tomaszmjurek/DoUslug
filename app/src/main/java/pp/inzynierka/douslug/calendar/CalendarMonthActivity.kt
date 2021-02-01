package pp.inzynierka.douslug.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar_month.*
import kotlinx.android.synthetic.main.calendar_top_layout.*
import kotlinx.android.synthetic.main.change_calendar_type.*
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.test.SectionedWeek
import java.util.*


class CalendarMonthActivity : AppCompatActivity() {
    private lateinit var selectedDate: String
    private lateinit var date : Date
    private val calendar = Calendar.getInstance()
    private var selectedDay : Int = calendar.get(Calendar.DAY_OF_MONTH)
    private var selectedMonth : Int = calendar.get(Calendar.MONTH)
    private var selectedYear : Int = calendar.get(Calendar.YEAR)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_month)
        title_text_view.text = "Kalendarz"

        selectedVisitsNumber.visibility = View.GONE
        selectedDate = DateConverter.getCurrentDate()

        calendar_type_button.setOnClickListener { showCalendarChange() }
        back_button.setOnClickListener { onBackPressed() }
        day_button.setOnClickListener { openCalendarDayActivity() }
        week_button.setOnClickListener { openCalendarWeekActivity() }
        month_button.setOnClickListener { showCalendarChange() }
        show_visits_day.setOnClickListener { openCalendarDayActivity(true) }
        show_visits_week.setOnClickListener { openCalendarWeekActivity(true) }

        calendar_view.setOnDateChangeListener { view, year, month, dayOfMonth ->
            selectedDate = "$year/${makeTwoDigitsIfOne((month+1).toString())}/${makeTwoDigitsIfOne(dayOfMonth.toString())}"
            selectedYear = year
            selectedMonth = month
            selectedDay = dayOfMonth
        }
    }

    private fun makeTwoDigitsIfOne(text: String) : String {
        if (text.length == 1) return "0$text"
        return text
    }

    private fun getNumberOfVisits() : String {
        val dayTimestamps = DateConverter.getTimestampsOfDay(selectedDate)
        return DBController.findNumberOfVisitsByDates(dayTimestamps).toString()
    }

    private fun toast() {
        Toast.makeText(
            this@CalendarMonthActivity,
            "Selected date: $selectedDate",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showCalendarChange() {
        if (calendar_type_layout.visibility == View.GONE) {
            calendar_type_layout.visibility = View.VISIBLE
        } else {
            calendar_type_layout.visibility = View.GONE
        }
    }

    private fun openCalendarDayActivity(withExtras: Boolean = false) {
        val intent = Intent(this@CalendarMonthActivity, CalendarDayActivity::class.java)
        if (withExtras) {
            intent.putExtra("selectedDate", selectedDate)
        }
        startActivity(intent)
    }

    private fun openCalendarWeekActivity(withExtras: Boolean = false) {
        val intent = Intent(this@CalendarMonthActivity, SectionedWeek::class.java)
        if (withExtras) {
            intent.putExtra("selectedDay", selectedDay)
            intent.putExtra("selectedMonth", selectedMonth)
            intent.putExtra("selectedYear", selectedYear)
        }
        startActivity(intent)
    }
}