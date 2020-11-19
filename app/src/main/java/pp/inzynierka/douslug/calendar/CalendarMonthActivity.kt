package pp.inzynierka.douslug.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar_month.*
import pp.inzynierka.douslug.R


class CalendarMonthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_month)

        calendar_type_button.setOnClickListener { showCalendarChange() }
        back_button.setOnClickListener { onBackPressed() }
        day_button.setOnClickListener { openCalendarDayActivity() }
//        week_button.setOnClickListener { openWeekDayActivity() }

        calendar_view.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Toast.makeText(
                this@CalendarMonthActivity,
                "You have Selected : " + dayOfMonth + " / " + (month + 1) + " / " + year,
                Toast.LENGTH_LONG
            ).show()
        }
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
        val intent = Intent(this@CalendarMonthActivity, CalendarDayActivity::class.java)
        startActivity(intent)
    }


}