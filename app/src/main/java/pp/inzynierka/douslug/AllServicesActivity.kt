package pp.inzynierka.douslug

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.calendar_top_layout.*
import pp.inzynierka.douslug.adapters.ServiceAdapter
import pp.inzynierka.douslug.db.DBController

class AllServicesActivity  : AppCompatActivity(), ServiceAdapter.OnItemClickListener {
        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: ServiceAdapter
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_all_visits)

            setUpRecyclerView()
            back_button.setOnClickListener { onBackPressed() }


        }
        private fun setUpRecyclerView() {
            recyclerView = findViewById(R.id.task_list)
            val services = DBController.findAllServices()
            adapter = ServiceAdapter(services,this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        }
        override fun onItemClick(position: Int) {
            val id = adapter.getItem(position)?._id // check
            openVisitView(id.toString())
        }
        private fun openVisitView(ServiceID: String) {
            val intent = Intent(this@AllServicesActivity, ServiceActivity::class.java)
            intent.putExtra("ServiceID", ServiceID)
            startActivity(intent)
        }
    }
