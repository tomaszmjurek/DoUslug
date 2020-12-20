package pp.inzynierka.douslug.db

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_d_b_test.*
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.model.Client
import pp.inzynierka.douslug.model.Service


private val appUserId = "100"

class DBTestActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private var TAG: String = "DB_TEST_ACTIVITY"

    private lateinit var result : RealmResults<Client>
    private lateinit var clientList : ArrayList<Client>

    override fun onStart() {
        super.onStart()

        // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
        Realm.getInstanceAsync(Realm.getDefaultConfiguration(), object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@DBTestActivity.realm = realm
            }
        })
    }


    private fun showServices() {
        val services = DBController.findAllServices()
        if (services != null) {
            textView.text = services.toString()
        } else {
            Log.v(TAG, "Retrieved services list is null $services")
        }
    }


    private fun showClients() {
        val clients = DBController.findAllClients()
//        if (clients != null) {
//            textView.text = clients.toString()
//            Log.v(TAG, "Retrieved client list is $clients")
//        } else {
//            Log.v(TAG, "Retrieved client list is null $clients")
//        }
    }

//    private fun insertUser() {
////        //todo auto-inc partition https://github.com/realm/realm-java/issues/469
//        val user = pp.inzynierka.douslug.model.User(30, "windows@test.pl", "Windows - profesjonalne mycie okien", "haslo", "1239123538", partition)
//
//        val backgroundRealm = Realm.getDefaultInstance()
//        backgroundRealm.executeTransactionAsync {realm ->
//            realm.insert(user)
//        }
//        backgroundRealm.close()
//    }

    private fun insertClient() {
        var client = Client("Mostowa 1", "", "Adam", "Zfrajeraminiegadam", "293123123", appUserId)
        DBController.insertClient(client)
    }

    private fun insertService() {
        var service = Service(20, "Mycie okna małego", 15.00, appUserId)
        DBController.insertService(service)
    }

//    private fun insertVisit() {
//        //todo getServiceId
//        //todo getClientID
//        val date = Date(2020,12,13,12,30,0)
//        val dataFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//        val data = dataFormat.parse("2020-12-13T12:30:00.000+00:00")
//        val client = realm.where<Client>().findFirst()
//        val service = realm.where<Service>().findFirst()
////        var visit = Visit(client, data, service)
//
//        val backgroundRealm = Realm.getDefaultInstance()
//        backgroundRealm.executeTransactionAsync {realm ->
////            realm.insert(visit)
//        }
//        backgroundRealm.close()
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_b_test)

        buttonService.setOnClickListener { insertService() }
        showServicesButton.setOnClickListener { showServices() }
        buttonClient.setOnClickListener { insertClient() }
        showClientsButton.setOnClickListener { showClients() }
    }


//    override fun onStop() {
//        super.onStop()
//        user.run {
//            realm.close()
//        }
//    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}