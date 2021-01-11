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
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class CalendarMonthActivity : AppCompatActivity() {
    private lateinit var selectedDate: String
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_month)
        title_text_view.text = "Kalendarz"

        calendar_type_button.setOnClickListener { showCalendarChange() }
        back_button.setOnClickListener { onBackPressed() }
        day_button.setOnClickListener { openCalendarDayActivity() }
        week_button.setOnClickListener { openCalendarWeekActivity() }
        month_button.setOnClickListener { showCalendarChange() }
        show_visits_button.setOnClickListener { openCalendarDayActivity() }

        calendar_view.setOnDateChangeListener { view, year, month, dayOfMonth ->
//            val mFormat = DecimalFormat("00")
//            mFormat.roundingMode = RoundingMode.DOWN
//            val date = DateConverter.generateProperDateShort(year.toString(), month.toString(), dayOfMonth.toString())

            val dateFormat_month = SimpleDateFormat("MM")
            val dateFormat_day = SimpleDateFormat("dd")
//            month = mFormat.format(month)
//            selectedDate = "$year/${dateFormat_month.format(month)}/${dateFormat_day.format(dayOfMonth)}"
            selectedDate = "$year/$month/$dayOfMonth"
            toast()
        }
    }

    override fun onStart() {
        super.onStart()
        selectedDate = getCurrentDate()
    }

    private fun toast() {
        Toast.makeText(
            this@CalendarMonthActivity,
            "Selected date: $selectedDate",
            Toast.LENGTH_SHORT
        ).show()
    }

    //todo move to date converter
    private fun getCurrentDate() : String {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        return sdf.format(calendar.time)
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
        startActivity(intent)
    }



}