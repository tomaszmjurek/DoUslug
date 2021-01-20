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


class CalendarWeekActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: VisitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_week)

        setUpRecycleView()

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
        val weekVisits = DBController.findAllVisits()
        adapter = VisitAdapter(weekVisits)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView.adapter = null
    }
}