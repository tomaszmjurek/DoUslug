package pp.inzynierka.douslug.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_calendar_day.*
import kotlinx.android.synthetic.main.calendar_top_layout.*
import kotlinx.android.synthetic.main.change_calendar_type.*
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.VisitAdapter
import pp.inzynierka.douslug.VisitListView
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.Visit
import java.text.SimpleDateFormat
import java.util.*

var titles = arrayOf(
    "John Lennon", "Maryla Rodowicz", "Krzysztof Ibisz"
)
var dates = arrayOf(
    "20.11.2020 12:30 - 13:30", "20.11.2020 13:30 - 14:30", "20.11.2020 14:30 - 16:00"
)

var notes = arrayOf(
    "włosy posklejane czymś czerwonym", "najpierw trzeba rozmrozić", "5 razy musisz mu wszystko potwierdzać"
)

class CalendarDayActivity : AppCompatActivity() {
    private val TAG: String = "CALENDAR_DAY_ACTIVITY"

    private lateinit var realm: Realm
    private lateinit var selectedDate: String
    private lateinit var realmVisits: RealmResults<Visit>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VisitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_day)


        selectedDate = intent.getStringExtra("selectedDate") ?: getCurrentDate()
        title_text_view.text = selectedDate


//        displayVisits()
//
////        if (realmVisits != null) {
//////            Log.v(TAG, "RealmList = $realmVisits")
//
////            val adapter = VisitAdapter(realmVisits)
//            task_list.adapter = adapter
////            val adapter = MyVisitAdapter(this, realmVisits)
////            upcomingVisitsList.adapter = adapter
//            Log.v(TAG, "Adapter set, list size = ${realmVisits.size}")
//        }

        // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
        Realm.getInstanceAsync(Realm.getDefaultConfiguration(), object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@CalendarDayActivity.realm = realm
                setUpRecyclerView(realm)
            }
        })

        calendar_type_button.setOnClickListener { showCalendarChange() }
        day_button.setOnClickListener { showCalendarChange() }
        week_button.setOnClickListener { openCalendarWeekActivity() }
        month_button.setOnClickListener { openCalendarMonthActivity() }
        back_button.setOnClickListener { onBackPressed() }
    }

    private fun setUpRecyclerView(realm: Realm) {
        adapter = VisitAdapter(realm.where<Visit>().findAll())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

//    override fun onStart() {
//        super.onStart()
//        selectedDate = intent.getStringExtra("selectedDate") ?: getCurrentDate()
//        title_text_view.text = selectedDate
////        displayVisits()
//    }

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

    private fun displayVisits() {
//        val timePair = DateConverter.getTimestampsOfDay(selectedDate)
//        if (timePair != null) {
//            Log.v(TAG, "Time pair not null")
//            realmVisits = DBController.findVisitsByDay(timePair)
            realm = Realm.getDefaultInstance()
            realmVisits = realm.where<Visit>().findAll()
//            Log.v(TAG, "Retrieved realm visits = $realmVisits.toS")
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView.adapter = null
        realm.close()
    }
}