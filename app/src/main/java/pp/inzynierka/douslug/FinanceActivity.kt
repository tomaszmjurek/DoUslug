package pp.inzynierka.douslug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pp.inzynierka.douslug.adapters.NotPaidVisitAdapter
import pp.inzynierka.douslug.adapters.VisitAdapter
import pp.inzynierka.douslug.db.DBController

class FinanceActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotPaidVisitAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        setUpRecyclerView()
    }


    private fun setUpRecyclerView() {
        recyclerView = findViewById(R.id.not_paid_visits_list)
        val visits = DBController.findAllVisits()
        adapter = NotPaidVisitAdapter(visits)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun calculateMonthlyRevenue() {

    }
}