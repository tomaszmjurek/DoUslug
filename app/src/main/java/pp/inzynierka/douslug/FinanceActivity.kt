package pp.inzynierka.douslug

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
import pp.inzynierka.douslug.adapters.NotPaidVisitAdapter
import pp.inzynierka.douslug.calendar.DateConverter
import pp.inzynierka.douslug.data.LoginHelper
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.Visit
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class FinanceActivity : AppCompatActivity(), NotPaidVisitAdapter.OnItemClickListener {
    private val TAG: String = "FINANCE_ACTIVITY"

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotPaidVisitAdapter
    private var monthlyRevenue: Double = 0.0
    private val polishFormatter: NumberFormat = NumberFormat.getInstance(Locale.forLanguageTag("pl-PL"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        polishFormatter.maximumFractionDigits = 2
        polishFormatter.minimumFractionDigits = 2

        calculateMonthlyRevenue()
        setUpRecyclerView()
        back_button.setOnClickListener { onBackPressed() }
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
        val year = Calendar.getInstance().get(Calendar.YEAR).toString()
        val month = SimpleDateFormat("MM").format(Calendar.getInstance().get(Calendar.MONTH)).toString()
        val timestamps = DateConverter.getTimestampsOfMonth(year, month)
        return DBController.findPaidVisitsByDate(timestamps)
    }

    private fun showNotPaidAmount(visits: RealmResults<Visit>) {
        var notPaidAmount = 0.0
        for (v in visits) {
            notPaidAmount += v.service_id?.price!!
        }
        to_settle_text.text = "${getString(R.string.to_settle)}       ${polishFormatter.format(notPaidAmount)}"
    }

    override fun onItemClick(position: Int) {
        val visitForReminder = adapter.getItem(position)
        if (visitForReminder != null){
            remindAboutVisit(visitForReminder)
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
//            sendIntent.data = Uri.parse("smsto:$phone")
            startActivity(sendIntent)
        }
    }

    private fun prepareSmsBody(serviceName: String, date: String, price: Double, userName: String) : String {
        val loggedUser = LoginHelper.getLoggedUserOrLogout(this)
        return "Przypomnienie o nieopłaconej wizycie \"$serviceName\" z dnia: $date na kwotę ${polishFormatter.format(price)} zł." +
            "\n\n~ $userName"
    }
}