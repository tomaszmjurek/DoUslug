package pp.inzynierka.douslug

import android.content.Intent
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
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.Visit
import java.text.SimpleDateFormat
import java.util.*

class FinanceActivity : AppCompatActivity() {
    private val TAG: String = "FINANCE_ACTIVITY"

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotPaidVisitAdapter
    private var monthlyRevenue: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        calculateMonthlyRevenue()
        setUpRecyclerView()
        back_button.setOnClickListener { onBackPressed() }
    }


    private fun setUpRecyclerView() {
        recyclerView = findViewById(R.id.not_paid_visits_list)
        val visits = DBController.findNotPaidVisits()
        showNotPaidAmount(visits)
        adapter = NotPaidVisitAdapter(visits)
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
        monthly_revenue_text.text = "${getString(R.string.monthly_revenue)}     $monthlyRevenue"
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
        to_settle_text.text = "${getString(R.string.to_settle)}       $notPaidAmount"
    }
}