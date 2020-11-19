package pp.inzynierka.douslug.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_calendar_day.*
import kotlinx.android.synthetic.main.activity_calendar_month.*
import kotlinx.android.synthetic.main.activity_calendar_month.back_button
import pp.inzynierka.douslug.R

class CalendarDayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_day)

        back_button.setOnClickListener { onBackPressed() }
    }
}