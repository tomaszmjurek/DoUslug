package pp.inzynierka.douslug.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.calendar_top_layout.*
import kotlinx.android.synthetic.main.change_calendar_type.*
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.adapters.VisitAdapter
import pp.inzynierka.douslug.db.DBController
import java.text.SimpleDateFormat
import java.util.*


class CalendarDayActivity : AppCompatActivity() {
    private val TAG: String = "CALENDAR_DAY_ACTIVITY"
    private lateinit var selectedDate: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VisitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_day)

        selectedDate = intent.getStringExtra("selectedDate") ?: DateConverter.getCurrentDate()
        title_text_view.text = selectedDate

        setUpRecyclerView()

        calendar_type_button.setOnClickListener { showCalendarChange() }
        day_button.setOnClickListener { showCalendarChange() }
        week_button.setOnClickListener { openCalendarWeekActivity() }
        month_button.setOnClickListener { openCalendarMonthActivity() }
        back_button.setOnClickListener { onBackPressed() }
    }

    private fun setUpRecyclerView() {
        recyclerView = findViewById(R.id.task_list)
        val visits = DBController.findAllVisits()
        adapter = VisitAdapter(visits)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
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
        } else {
            calendar_type_layout.visibility = View.GONE
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

    override fun onDestroy() {
        super.onDestroy()
        recyclerView.adapter = null
    }
}