package pp.inzynierka.douslug

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_all_visits.*
import kotlinx.android.synthetic.main.calendar_top_layout.*
import kotlinx.android.synthetic.main.calendar_top_layout.back_button
import pp.inzynierka.douslug.adapters.ClientAdapter
import pp.inzynierka.douslug.db.DBController

class AllClientsActivity : AppCompatActivity(), ClientAdapter.OnItemClickListener {
        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: ClientAdapter
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_all_visits)

            setUpRecyclerView()
            back_button.setOnClickListener { onBackPressed() }
            add_button.setOnClickListener {addVisit()}


        }
        private fun setUpRecyclerView() {
            recyclerView = findViewById(R.id.task_list)
            val clients = DBController.findAllClients()
            adapter = ClientAdapter(clients,this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        }
        override fun onItemClick(position: Int) {
            val id = adapter.getItem(position)?._id // check
            openVisitView(id.toString())
        }
        private fun openVisitView(ClientID: String) {
            val intent = Intent(this@AllClientsActivity, ClientActivity::class.java)
            intent.putExtra("ClientID", ClientID)
            startActivity(intent)
        }

        private fun addVisit(){
            val intent = Intent(this@AllClientsActivity, ClientActivity::class.java)
            startActivity(intent)
        }
    }


