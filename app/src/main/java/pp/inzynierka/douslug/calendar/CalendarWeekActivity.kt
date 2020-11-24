package pp.inzynierka.douslug.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.calendar_top_layout.*
import kotlinx.android.synthetic.main.change_calendar_type.*
import pp.inzynierka.douslug.R

class CalendarWeekActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_week)

        calendar_type_button.setOnClickListener { showCalendarChange() }
        day_button.setOnClickListener { openCalendarDayActivity() }
        week_button.setOnClickListener { showCalendarChange() }
        month_button.setOnClickListener { openCalendarMonthActivity() }
        back_button.setOnClickListener { onBackPressed() }
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

    private fun openCalendarDayActivity() {
        val intent = Intent(this@CalendarWeekActivity, CalendarDayActivity::class.java)
        startActivity(intent)
    }

    private fun openCalendarMonthActivity() {
        val intent = Intent(this@CalendarWeekActivity, CalendarMonthActivity::class.java)
        startActivity(intent)
    }
}