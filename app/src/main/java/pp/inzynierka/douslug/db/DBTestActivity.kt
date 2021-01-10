package pp.inzynierka.douslug.db

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_d_b_test.*
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.calendar.DateConverter
import pp.inzynierka.douslug.model.Client
import pp.inzynierka.douslug.model.Service
import pp.inzynierka.douslug.model.Visit


private val appUserId = "100"

class DBTestActivity : AppCompatActivity() {
    private var TAG: String = "DB_TEST_ACTIVITY"

//    private lateinit var result : RealmResults<Client>
//    private lateinit var clientList : ArrayList<Client>

    override fun onStart() {
        super.onStart()

//        // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
//        Realm.getInstanceAsync(Realm.getDefaultConfiguration(), object: Realm.Callback() {
//            override fun onSuccess(realm: Realm) {
//                // since this realm should live exactly as long as this activity, assign the realm to a member variable
//                this@DBTestActivity.realm = realm
//            }
//        })
    }


    private fun showServices() {
        val services = DBController.findAllServices()
        if (services != null) {
            textView.text = services.toString()
        } else {
            Log.v(TAG, "Retrieved services list is null $services")
        }
    }

    private fun showVisits() {
        val visits = DBController.findAllVisits()

        for (c in visits) {
            Log.v(TAG, "Visit: Client: ${c.client_id?.first_name} ${c.date} ${c._partition}")
        }

//        if (visits != null) {
//            textView.text = visits.toString()
//        } else {
//            Log.v(TAG, "Retrieved services list is null $visits")
//        }
    }


    private fun showClients() {
        val clients = DBController.findAllClients()
        for (c in clients) {
            Log.v(TAG, "Client: ${c.first_name} ${c.last_name}")
        }



        if (clients != null) {
            textView.text = clients.toString()
            Log.v(TAG, "Retrieved client list is $clients")
        } else {
            Log.v(TAG, "Retrieved client list is null $clients")
        }
    }

    private fun insertClient() {
        var client = Client("Mostowa 1", "", "Adam", "Zfrajeraminiegadam", "293123123", appUserId)
        DBController.insertClient(client)
    }

    private fun insertService() {
        var service = Service(20, "Mycie okna małego", 15.00, appUserId)
        DBController.insertService(service)
    }

    private fun insertVisit() {
        val dateString = DateConverter.generateProperDate("2021", "01", "14", "14", "30")
        val date = DateConverter.dateStringToTimestamp(dateString)
        textView.text = date.toString()

        val client = DBController.findClientByPhoneNum("293123123")
        val service = DBController.findServiceByName("Mycie okna małego")

        if (client != null && service != null && date != null) {
            val visit = Visit(client, date, service)
            DBController.insertVisit(visit)
            Log.v(TAG, "Successfully added new Visit ($visit)")
        } else {
            Log.v(TAG, "Error while inserting Visit: Client ($client) or Service ($service) or Date ($date) is null")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_b_test)

        buttonService.setOnClickListener { insertService() }
        showVisitsButton.setOnClickListener { showVisits() }
        buttonClient.setOnClickListener { insertClient() }
        showClientsButton.setOnClickListener { showClients() }
        buttonVisit.setOnClickListener {
            try {
                insertVisit()
            } catch (e: Exception) {
                textView.text = "error"
                Log.v(TAG, "$e")
            }
        }
        buttonDate.setOnClickListener { useDate() }
    }

    private fun useDate() {
        val timestamp = 1610634600000//System.currentTimeMillis()
        val date = DateConverter.timestampToDateString(timestamp)
        textView.text = date
    }

//    override fun onStop() {
//        super.onStop()
//        user.run {
//            realm.close()
//        }
//    }


    override fun onDestroy() {
        super.onDestroy()
//        realm.close()
    }


}