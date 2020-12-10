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
import io.realm.kotlin.where
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.activity_d_b_test.*
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.model.Client
import pp.inzynierka.douslug.model.Service
import pp.inzynierka.douslug.model.Visit
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

        val credentials: Credentials = //Credentials.anonymous()
            Credentials.apiKey("cOJH91AmPbUKlH8Z7UhwRze7YcdCBYJbTmFgZXqwtwChUlrJ0W5fZWAMRPXpza0r")

        realmApp.loginAsync(credentials) {
            if (it.isSuccess) {
                user = realmApp.currentUser()
                Log.v(TAG, "realm user = $user")
                partition = "10001"
//                val sharedPreference = getSharedPreferences("prefs name", Context.MODE_PRIVATE)
//                partition = sharedPreference.getString("partition", "10001")!!
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

        button.setOnClickListener { updateClientView() }
    }

    private fun updateClientView() {
        clientList = ArrayList(realm.where<Client>().findAllAsync())

        if (clientList != null) {
            textView.text = clientList.getOrNull(0).toString()
        } else {
            Log.v(TAG, "Retrieved list is null $clientList")
        }
    }

    private fun insertUser() {
        //todo auto-inc partition https://github.com/realm/realm-java/issues/469
        val user = pp.inzynierka.douslug.model.User("10002", 30, "windows@test.pl", "Windows - profesjonalne mycie okien", "haslo", "1239123538")

        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync {realm ->
            realm.insert(user)
        }
        backgroundRealm.close()
    }

    private fun insertClient() {
        var client = Client("10001", "Mostowa 1", "", "Kamil", "Bednarek", "293123123")

        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync {realm ->
            realm.insert(client)
        }
        backgroundRealm.close()
    }

    private fun insertService() {
        //todo getUserId
        var service = Service(partition, "Mycie okna duzego", 60, 15.00)

        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync {realm ->
            realm.insert(service)
        }
        backgroundRealm.close()
    }

    private fun insertVisit() {
        val clientId = "10001"
        //todo getServiceId
        //todo getClientID
        val date = Date("2020-12-14T12:30:00.000+00:00")

//        var visit = Visit(partition, "0001", date, "")

        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync {realm ->
//            realm.insert(visit)
        }
        backgroundRealm.close()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_b_test)
    }



    fun readClients(realm : Realm) : RealmResults<Client> {
        var clients : RealmResults<Client> = realm.where<Client>().findAll()

        return clients
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}