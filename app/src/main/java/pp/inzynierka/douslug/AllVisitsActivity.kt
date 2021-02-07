package pp.inzynierka.douslug

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_all_visits.*
import kotlinx.android.synthetic.main.calendar_top_layout.back_button
import pp.inzynierka.douslug.adapters.VisitAdapter
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
        val intent = Intent(this@AllVisitsActivity, VisitActivity::class.java)
        startActivity(intent)
    }
}

