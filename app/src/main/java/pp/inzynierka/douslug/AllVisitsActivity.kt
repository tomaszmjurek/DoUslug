package pp.inzynierka.douslug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_all_visits.*
import kotlinx.android.synthetic.main.calendar_top_layout.back_button
import kotlinx.android.synthetic.main.right_drawer_menu.*
import pp.inzynierka.douslug.adapters.VisitAdapter
import pp.inzynierka.douslug.calendar.CalendarDayActivity
import pp.inzynierka.douslug.calendar.CalendarMonthActivity
import pp.inzynierka.douslug.calendar.CalendarWeekActivity
import pp.inzynierka.douslug.db.DBController

class AllVisitsActivity : AppCompatActivity(), VisitAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VisitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_visits)

        setUpRecyclerView()

        back_button.setOnClickListener { onBackPressed() }
        add_button.setOnClickListener {addVisit()}

        clientImageView.setOnClickListener {
            toggleRightDrawer()
        }

        drawer_menu_close.setOnClickListener{ drawerClientLayout.closeDrawer(rightClientDrawerMenu) }
        drawer_menu_calendar.setOnClickListener { openCalendar() }
        drawer_menu_clients.setOnClickListener { openAllClients() }
        drawer_menu_services.setOnClickListener { openAllServices() }
        drawer_menu_settings.setOnClickListener { openSettings() }
        drawer_menu_finances.setOnClickListener { openFinances() }
        drawer_menu_home_screen.setOnClickListener{ openMain() }
    }

    private fun setUpRecyclerView() {
        recyclerView = findViewById(R.id.task_list)
        val visits = DBController.findAllVisits()
        adapter = VisitAdapter(visits,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onItemClick(position: Int) {
        val id = adapter.getItem(position)?._id
        openVisitView(id.toString())
    }

    private fun openVisitView(visitID: String) {
        val intent = Intent(this@AllVisitsActivity, VisitActivity::class.java)
        intent.putExtra("visitID", visitID)
        startActivity(intent)
    }

    private fun addVisit(){
        val intent = Intent(this@AllVisitsActivity, ClientActivity::class.java)
        startActivity(intent)
    }

    private fun toggleRightDrawer() {
        if (drawerClientLayout.isDrawerOpen(rightClientDrawerMenu)) {
            drawerClientLayout.closeDrawer(rightClientDrawerMenu)
        } else {
            drawerClientLayout.openDrawer(rightClientDrawerMenu)
        }
    }

    private fun openCalendar() {
        val sharedPref = getSharedPreferences("app_shared", Context.MODE_PRIVATE)
        val calendarMode = sharedPref.getString("calendar_mode", "month")
        when (calendarMode) {
            "month" -> open(CalendarMonthActivity::class.java)
            "week" -> open(CalendarWeekActivity::class.java)
            "day" -> open(CalendarDayActivity::class.java)
        }
    }

    private fun openSettings() {
        open(SettingsActivity::class.java)
    }

    private fun openFinances() {
        open(FinanceActivity::class.java)
    }

    private fun openAllClients() {
        open(AllClientsActivity::class.java)
    }

    private fun openAllServices() {
        open(AllServicesActivity::class.java)
    }

    private fun openMain(){
        open(MainActivity::class.java)
    }

    private fun open(activity: Class<out AppCompatActivity>) {
        val intent = Intent(this@AllVisitsActivity, activity)
        startActivity(intent)
        drawerClientLayout.closeDrawer(rightClientDrawerMenu)
    }
}

