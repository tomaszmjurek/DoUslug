package pp.inzynierka.douslug.test

import android.os.Bundle
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
import pp.inzynierka.douslug.calendar.DateConverter
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.Visit
import java.util.*


class SectionedWeek : AppCompatActivity(), SectionRecycle.OnItemClickListener {

    private val TAG: String = "TEST_WEEK_ACTIVITY"
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: SectionRecycle
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
//        day_button.setOnClickListener { openCalendarDayActivity() }
        week_button.setOnClickListener { showCalendarChange() }
//        month_button.setOnClickListener { openCalendarMonthActivity() }
        back_button.setOnClickListener { onBackPressed() }
    }

    private fun showCalendarChange() {
        if (calendar_type_layout.visibility == View.GONE) {
            calendar_type_layout.visibility = View.VISIBLE
        } else {
            calendar_type_layout.visibility = View.GONE
        }
    }


    private fun setUpRecycleView() {
        recyclerView = findViewById(R.id.visits_list)
        val weekVisits = getWeekVisits()

        val sectionedVisitsWithDay = weekVisits
                .groupBy { DateConverter.timestampToDateStringShort(it.date)!! }
                .flatMap { (title, visits) ->
            listOf<RecyclerItem>(RecyclerItem.Section(title)) + visits.map { RecyclerItem.WeekVisit(it) }
       }

        adapter = SectionRecycle(sectionedVisitsWithDay, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }

//    override fun onItemClick(position: Int) {
//        val id = adapter.getItem(position)?._id
//        openVisitView(id.toString())
//    }

//    private fun openVisitView(visitID: String) {
//        val intent = Intent(this@CalendarWeekActivity, VisitActivity::class.java)// Tu Ada wstawiasz activity obsługujące wizytę
//        intent.putExtra("visitID", visitID)
//        startActivity(intent)
//    }

    private fun getWeekVisits() : RealmResults<Visit> {
        weekTimestamps = DateConverter.getTimestampsOfWeek(selectedYear, selectedMonth, selectedDay)
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

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}