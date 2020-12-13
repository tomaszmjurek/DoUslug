package pp.inzynierka.douslug.db

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import io.realm.OrderedRealmCollectionChangeListener
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.activity_d_b_test.*
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.model.Client
import pp.inzynierka.douslug.model.Service
import pp.inzynierka.douslug.model.Visit
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class DBTestActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private var user: User? = null
    private lateinit var partition: String
    private var TAG: String = "DB_TEST_ACTIVITY"

    private lateinit var result : RealmResults<Client>
    private lateinit var clientList : ArrayList<Client>

    override fun onStart() {
        super.onStart()

        val credentials: Credentials = Credentials.anonymous()
//            Credentials.apiKey("cOJH91AmPbUKlH8Z7UhwRze7YcdCBYJbTmFgZXqwtwChUlrJ0W5fZWAMRPXpza0r")

        realmApp.loginAsync(credentials) {
            if (it.isSuccess) {
                user = realmApp.currentUser()
                Log.v(TAG, "realm user = $user")
//                partition = "10001"
                val sharedPreference = getSharedPreferences("prefs name", Context.MODE_PRIVATE)
                partition = sharedPreference.getString("partition", "10001")!!
                val config = SyncConfiguration.Builder(user!!, partition)
                    .waitForInitialRemoteData()
                    .build()

                // This configuration is set as default for the entire app
                Realm.setDefaultConfiguration(config)

                // Sync all realm changes via a new instance
                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {
                        // since this realm should live as long as this activity assign it to member variable
                        this@DBTestActivity.realm = realm
                    }
                })
            }

        }

        buttonService.setOnClickListener { insertService() }
        showServicesButton.setOnClickListener { showServices() }
        buttonClient.setOnClickListener { insertClient() }
        showClientsButton.setOnClickListener { showClients() }
        buttonVisit.setOnClickListener { insertVisit() }
//        deleteButton.setOnClickListener { deleteVisits() }
    }


    private fun showServices() {
//        val realm = Realm.getDefaultInstance()
        var services : RealmResults<Service> = realm.where<Service>().findAllAsync()
        if (services != null) {
            textView.text = services.toString()
        } else {
            Log.v(TAG, "Retrieved services list is null $clientList")
        }

    }

//    private fun deleteVisits() {
//        realm.delete(Visit.class)
//                mongo
//    }

    private fun showClients() {
        var clients : RealmResults<Client> = realm.where<Client>().findAllAsync()
//        val clients = ArrayList<Client>(realm.where<Client>().findAllAsync())

        if (clients != null) {
//            textView.text = clients.getOrNull(0).toString()
            textView.text = clients.toString()
        } else {
            Log.v(TAG, "Retrieved client list is null $clientList")
        }
    }

    private fun insertUser() {
//        //todo auto-inc partition https://github.com/realm/realm-java/issues/469
        val user = pp.inzynierka.douslug.model.User(30, "windows@test.pl", "Windows - profesjonalne mycie okien", "haslo", "1239123538", partition)

        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync {realm ->
            realm.insert(user)
        }
        backgroundRealm.close()
    }

    private fun insertClient() {
        var client = Client("Mostowa 1", "", "Kamil", "Bednarek", "293123123", partition)

        realm.executeTransactionAsync { realm ->
            realm.insert(client)
        }
//        val backgroundRealm = Realm.getDefaultInstance()
//        backgroundRealm.executeTransactionAsync {realm ->
//            realm.insert(client)
//        }
//        backgroundRealm.close()
    }

    private fun insertService() {
        var service = Service(60, "Mycie okna małego", 15.00, partition)

        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync {realm ->
//            realm.insertOrUpdate(service)
            realm.createObject<Service>(service)
        }
        backgroundRealm.close()
    }

    private fun insertVisit() {
        //todo getServiceId
        //todo getClientID
        val date = Date(2020,12,13,12,30,0)
        val dataFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val data = dataFormat.parse("2020-12-13T12:30:00.000+00:00")
        val client = realm.where<Client>().findFirst()
        val service = realm.where<Service>().findFirst()
        var visit = Visit(client, data, service)

        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync {realm ->
            realm.insert(visit)
        }
        backgroundRealm.close()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_b_test)
    }





    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}