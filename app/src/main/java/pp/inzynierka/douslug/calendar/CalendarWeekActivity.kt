package pp.inzynierka.douslug.calendar

import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import kotlinx.android.synthetic.main.calendar_top_layout.*
import kotlinx.android.synthetic.main.change_calendar_type.*
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.adapters.VisitAdapter
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.Visit
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.*


class CalendarWeekActivity : AppCompatActivity() {
    private val TAG: String = "CALENDAR_WEEK_ACTIVITY"
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: VisitAdapter
    private var selectedDay = 0
    private var selectedMonth = 0
    private var selectedYear = 0
    private lateinit var weekTimestamps : Pair<Long?, Long?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_week)

        val calendar = Calendar.getInstance()
        selectedDay = intent.getIntExtra("selectedDay", calendar.get(Calendar.DAY_OF_MONTH))
        selectedMonth = intent.getIntExtra("selectedMonth", calendar.get(Calendar.MONTH))
        selectedYear = intent.getIntExtra("selectedYear", calendar.get(Calendar.YEAR))

        setUpRecycleView()
        setUpTitle()

        calendar_type_button.setOnClickListener { showCalendarChange() }
        day_button.setOnClickListener { openCalendarDayActivity() }
        week_button.setOnClickListener { showCalendarChange() }
        month_button.setOnClickListener { openCalendarMonthActivity() }
        back_button.setOnClickListener { onBackPressed() }
    }

    private fun showCalendarChange() {
        if (calendar_type_layout.visibility == View.GONE) {
            calendar_type_layout.visibility = View.VISIBLE
        } else {
            calendar_type_layout.visibility = View.GONE
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

    private fun setUpRecycleView() {
        recyclerView = findViewById(R.id.visits_list)
        val weekVisits = getWeekVisits()
        adapter = VisitAdapter(weekVisits)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun getWeekVisits() : RealmResults<Visit> {
        weekTimestamps = DateConverter.getTimestampsOfWeek(selectedYear, selectedMonth, selectedDay)
        setUpTitle()
        return DBController.findVisitsByDates(weekTimestamps)
    }

    private fun setUpTitle() {
        if (weekTimestamps.first != null && weekTimestamps.second != null) {
            val dateFrom = DateConverter.timestampToDateString(weekTimestamps.first!!)?.substring(0, 10)
            val dateTo = DateConverter.timestampToDateString(weekTimestamps.second!!)?.substring(5, 10)
            title_text_view.text = "$dateFrom - $dateTo"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView.adapter = null
    }
}