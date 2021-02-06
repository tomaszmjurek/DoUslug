package pp.inzynierka.douslug.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar_month.*
import kotlinx.android.synthetic.main.calendar_top_layout.*
import kotlinx.android.synthetic.main.change_calendar_type.*
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.VisitActivity
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.Visit
import java.util.*


class CalendarMonthActivity : AppCompatActivity() {
    private val TAG: String = "CALENDAR_MONTH_ACTIVITY"
    private val calendar = Calendar.getInstance()
    private var selectedDay : Int = calendar.get(Calendar.DAY_OF_MONTH)
    private var selectedMonth : Int = calendar.get(Calendar.MONTH)
    private var selectedYear : Int = calendar.get(Calendar.YEAR)
    private var visitsCountMap : Map<String, Int> = mapOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_month)
        title_text_view.text = "Kalendarz"

        updateMonthVisitsNumber(selectedYear, selectedMonth, selectedDay)
        showDayVisitsNumber()

        calendar_type_button.setOnClickListener { showCalendarChange() }
        back_button.setOnClickListener { onBackPressed() }
        day_button.setOnClickListener { openCalendarDayActivity() }
        week_button.setOnClickListener { openCalendarWeekActivity() }
        month_button.setOnClickListener { showCalendarChange() }
        add_visit_button.setOnClickListener { openVisitActivity() }
        show_visits_day.setOnClickListener { openCalendarDayActivity(true) }
        show_visits_week.setOnClickListener { openCalendarWeekActivity(true) }

        calendar_view.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedYear = year
            if /* month and day switched */ (selectedMonth != month) {
                selectedMonth = month
                updateMonthVisitsNumber(year, month, dayOfMonth)
                selectedDay = dayOfMonth
                showDayVisitsNumber()
            } else if /* only day switched */ (selectedDay != dayOfMonth) {
                selectedDay = dayOfMonth
                showDayVisitsNumber()
            }
        }
    }

    private fun makeTwoDigitsIfOne(text: String) : String {
        if (text.length == 1) return "0$text"
        return text
    }

    private fun updateMonthVisitsNumber(year: Int, month: Int, day: Int) {
        Log.v(TAG, "Getting month visits...")
        val timestamps = DateConverter.getTimestampsOfMonth(year.toString(),
            makeTwoDigitsIfOne((month+1).toString()), makeTwoDigitsIfOne(day.toString()))
        val visits = DBController.findVisitsByDates(timestamps)

        var visitsList = mutableListOf<Visit>()
        for (v in visits) {
            visitsList.add(v)
        }

        Log.v(TAG, "Number of visits in month: ${visitsList.size}")
        visitsCountMap = visitsList.groupingBy { DateConverter.timestampToDateStringShort(it.date)!! }.eachCount()
    }

    private fun showDayVisitsNumber() {
        Log.v(TAG, "Getting day visits number")
        val selectedDate = DateConverter.generateProperDateFromInts(selectedYear, selectedMonth, selectedDay)
        val numberOfVisits = visitsCountMap[selectedDate] ?: 0
        visits_number_text.text = numberOfVisits.toString()
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
            val selectedDate = DateConverter.generateProperDateFromInts(selectedYear, selectedMonth, selectedDay)
            intent.putExtra("selectedDate", selectedDate)
        }
        startActivity(intent)
    }

    private fun openCalendarWeekActivity(withExtras: Boolean = false) {
        val intent = Intent(this@CalendarMonthActivity, CalendarWeekActivity::class.java)
        if (withExtras) {
            intent.putExtra("selectedDay", selectedDay)
            intent.putExtra("selectedMonth", selectedMonth)
            intent.putExtra("selectedYear", selectedYear)
        }
        startActivity(intent)
    }

    private fun openVisitActivity() {
        val intent = Intent(this@CalendarMonthActivity, VisitActivity::class.java)
        intent.putExtra("selectedDate", selectedDate)
        startActivity(intent)
    }
}