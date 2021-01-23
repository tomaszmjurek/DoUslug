package pp.inzynierka.douslug.calendar

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar_month.*
import kotlinx.android.synthetic.main.calendar_top_layout.*
import kotlinx.android.synthetic.main.change_calendar_type.*
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.db.DBController
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
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

        calendar_type_button.setOnClickListener { showCalendarChange() }
        back_button.setOnClickListener { onBackPressed() }
        day_button.setOnClickListener { openCalendarDayActivity() }
        week_button.setOnClickListener { openCalendarWeekActivity() }
        month_button.setOnClickListener { showCalendarChange() }
        show_visits_button.setOnClickListener { openCalendarDayActivity() }

        calendar_view.setOnDateChangeListener { view, year, month, dayOfMonth ->
            selectedDate = "$year/${makeTwoDigitsIfOne((month+1).toString())}/${makeTwoDigitsIfOne(dayOfMonth.toString())}"
            //getINstance
//            calendar.set(year, month, dayOfMonth)
            selectedYear = year
            selectedMonth = month
            selectedDay = dayOfMonth
//            date = Date(year, month, dayOfMonth)
//            selectedVisitsNumber.text = getNumberOfVisits()
        }
    }

    override fun onStart() {
        super.onStart()
        selectedDate = DateConverter.getCurrentDate()
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

    //todo wybranie widoku dnia z rozwijanej listy otwiera bieżący dzień a nie wybrany
    private fun openCalendarDayActivity() {
        val intent = Intent(this@CalendarMonthActivity, CalendarDayActivity::class.java)
        intent.putExtra("selectedDate", selectedDate)
        startActivity(intent)
    }

    private fun openCalendarWeekActivity() {
        val intent = Intent(this@CalendarMonthActivity, CalendarWeekActivity::class.java)
        intent.putExtra("selectedDay", selectedDay)
        intent.putExtra("selectedMonth", selectedMonth)
        intent.putExtra("selectedYear", selectedYear)
        startActivity(intent)
    }



}