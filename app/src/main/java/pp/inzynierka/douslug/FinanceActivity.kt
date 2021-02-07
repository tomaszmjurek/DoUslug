package pp.inzynierka.douslug

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_finance.*
import kotlinx.android.synthetic.main.right_drawer_menu.*
import pp.inzynierka.douslug.adapters.NotPaidVisitAdapter
import pp.inzynierka.douslug.calendar.CalendarDayActivity
import pp.inzynierka.douslug.calendar.CalendarMonthActivity
import pp.inzynierka.douslug.calendar.CalendarWeekActivity
import pp.inzynierka.douslug.calendar.DateConverter
import pp.inzynierka.douslug.data.LoginHelper
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.Visit
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class FinanceActivity : AppCompatActivity(), NotPaidVisitAdapter.OnItemClickListener {
    private val TAG: String = "FINANCE_ACTIVITY"

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotPaidVisitAdapter
    private var monthlyRevenue: Double = 0.0
    private val polishFormatter: NumberFormat = NumberFormat.getInstance(Locale.forLanguageTag("pl-PL"))
    private val CLICKED_BUTTON = 0
    private val CLICKED_LIST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        polishFormatter.maximumFractionDigits = 2
        polishFormatter.minimumFractionDigits = 2

        calculateMonthlyRevenue()
        setUpRecyclerView()

        menu_button.setOnClickListener { toggleRightDrawer() }
        back_button.setOnClickListener { onBackPressed() }
        drawer_menu_close.setOnClickListener{ toggleRightDrawer() }
        drawer_menu_calendar.setOnClickListener { openCalendar() }
        drawer_menu_clients.setOnClickListener { openAllClients() }
        drawer_menu_services.setOnClickListener { openAllServices() }
        drawer_menu_settings.setOnClickListener { openSettings() }
        drawer_menu_finances.setOnClickListener { toggleRightDrawer() }
        drawer_menu_home_screen.setOnClickListener { openMain() }
    }


    private fun setUpRecyclerView() {
        recyclerView = findViewById(R.id.not_paid_visits_list)
        val visits = DBController.findNotPaidVisits()
        showNotPaidAmount(visits)
        adapter = NotPaidVisitAdapter(visits, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun calculateMonthlyRevenue() {
        val visits = getMonthlyVisits()
        Log.v(TAG, "$visits")
        monthlyRevenue = 0.0
        for(v in visits) {
            monthlyRevenue += v.service_id?.price!!
        }
        monthly_revenue_text.text = "${getString(R.string.monthly_revenue)}     ${polishFormatter.format(monthlyRevenue)}"
    }

    private fun getMonthlyVisits() : RealmResults<Visit> {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR).toString()
        val month = makeTwoDigitsIfOne((calendar.get(Calendar.MONTH)+1).toString())
        val day =makeTwoDigitsIfOne(calendar.get(Calendar.DAY_OF_MONTH).toString())

        val timestamps = DateConverter.getTimestampsOfMonth(year, month, day)
        return DBController.findPaidVisitsByDate(timestamps)
    }

    private fun showNotPaidAmount(visits: RealmResults<Visit>) {
        var notPaidAmount = 0.0
        for (v in visits) {
            notPaidAmount += v.service_id?.price!!
        }
        to_settle_text.text = "${getString(R.string.to_settle)}       ${polishFormatter.format(notPaidAmount)}"
    }

    override fun onItemClick(position: Int, mode: Int) {
        val visitForReminder = adapter.getItem(position)
        if (visitForReminder != null) when(mode) {
            CLICKED_BUTTON -> remindAboutVisit(visitForReminder)
            CLICKED_LIST -> openVisitView(visitForReminder._id.toString())
        }
    }

    private fun remindAboutVisit(visit: Visit) {
        val serviceName = visit.service_id?.name
        val date = DateConverter.timestampToDateString(visit.date)
        val phone = visit.client_id?.phone_num
        val price = visit.service_id?.price
        val userName = LoginHelper.getLoggedUserOrLogout(this)?.official_name

        if (serviceName != null && phone != null && date != null && price != null && userName != null) {
            val smsBody = prepareSmsBody(serviceName, date, price, userName)
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.data = Uri.parse("smsto:$phone")
            sendIntent.putExtra("sms_body", smsBody)
            startActivity(sendIntent)
        }
    }

    private fun prepareSmsBody(serviceName: String, date: String, price: Double, userName: String) : String {
        return "Przypomnienie o nieopłaconej wizycie \"$serviceName\" z dnia: $date na kwotę ${polishFormatter.format(price)} zł." +
            "\n\n~ $userName"
    }

    private fun openVisitView(visitID: String) {
        val intent = Intent(this@FinanceActivity, VisitActivity::class.java)
        intent.putExtra("visitID", visitID)
        startActivity(intent)
    }

    private fun openCalendar() {
        val sharedPref = getSharedPreferences("app_shared", Context.MODE_PRIVATE)
        when (sharedPref.getString("calendar_mode", "month")) {
            "month" -> open(CalendarMonthActivity::class.java)
            "week" -> open(CalendarWeekActivity::class.java)
            "day" -> open(CalendarDayActivity::class.java)
        }
    }

    private fun openSettings() {
        open(SettingsActivity::class.java)
    }

    private fun openAllClients() {
        open(AllClientsActivity::class.java)
    }

    private fun openAllServices() {
        open(AllServicesActivity::class.java)
    }

    private fun openMain() {
        open(MainActivity::class.java)
    }

    private fun open(activity: Class<out AppCompatActivity>) {
        val intent = Intent(this@FinanceActivity, activity)
        startActivity(intent)
        drawerLayoutF.closeDrawer(rightDrawerMenuF)
    }

    private fun toggleRightDrawer() {
        if (drawerLayoutF.isDrawerOpen(rightDrawerMenuF)) {
            drawerLayoutF.closeDrawer(rightDrawerMenuF)
        } else {
            drawerLayoutF.openDrawer(rightDrawerMenuF)
        }
    }

    private fun makeTwoDigitsIfOne(text: String) : String {
        if (text.length == 1) return "0$text"
        return text
    }
}