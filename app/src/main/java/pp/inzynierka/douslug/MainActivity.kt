package pp.inzynierka.douslug

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_main.*
import pp.inzynierka.douslug.calendar.CalendarMonthActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this@MainActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main)

        CalendarBtn.setOnClickListener { openCalendarActivity() }
    }

    private fun openCalendarActivity() {
        val intent = Intent(this@MainActivity, CalendarMonthActivity::class.java)
        startActivity(intent)
    }
}