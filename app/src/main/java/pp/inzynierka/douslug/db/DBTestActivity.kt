package pp.inzynierka.douslug.db

import android.content.Context
import android.os.Bundle
import android.util.Log
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

class DBTestActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private lateinit var uiThreadRealm: Realm
    private var user: User? = null
    private lateinit var partition: String
    private var TAG: String = "DB_TEST_ACTIVITY"

    private lateinit var result : RealmResults<Client>
//    private


    override fun onStart() {
        super.onStart()

        val credentials: Credentials = Credentials.anonymous()
//            Credentials.apiKey("cOJH91AmPbUKlH8Z7UhwRze7YcdCBYJbTmFgZXqwtwChUlrJ0W5fZWAMRPXpza0r")

        realmApp.loginAsync(credentials) {
            if (it.isSuccess) {
                user = realmApp.currentUser()
                Log.v(TAG, "realm user = $user")
//                val partitionValue: String = "10001"
//                partition = "10001"
                val sharedPreference = getSharedPreferences("prefs name", Context.MODE_PRIVATE)
                partition = sharedPreference.getString("partition", "10001")!!
                val config = SyncConfiguration.Builder(user!!, partition)
//                        .allowQueriesOnUiThread(true)
//                        .allowWritesOnUiThread(true)
                    .waitForInitialRemoteData()
                    .build()

                // This configuration is set as default for the entire app
                Realm.setDefaultConfiguration(config)

                // Sync all realm changes via a new instance
                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {
                        // since this realm should live as long as this activity assign it to member variable
                        this@DBTestActivity.realm = realm

                        var clients : RealmResults<Client> = realm.where<Client>().findAllAsync()
//                        val backgroundRealm = Realm.getDefaultInstance()
////                        var text :
//                        backgroundRealm.executeTransactionAsync {
//                            var text = readClients(it)
//                        }
//                        backgroundRealm.close()
                    }
                })
            }

//                mongoClient = user.getMongoClient("mongodb+srv://full_db_user:svErN0MgTvrgCuWE8AZr@douslug-cluster.mmign.mongodb.net/DoUslugDB?retryWrites=true&w=majority")
//                if (mongoClient != null) {
//                    val mongoDatabase : MongoDatabase = mongoClient!!.getDatabase("DoUslugDB")//!!.getCollection("collection name")
//                Log.v(TAG, "Successfully connected to the MongoDB instance.")
//                textView.text = "Successfully connected to the MongoDB instance."
//            } else {
//                Log.e(TAG, "Error connecting to the MongoDB instance.")
//            }
//            } else {
//                textView.text = "Error logging into the Realm app. Make sure that API KEY is correct."
//                Log.e(TAG, "Error logging into the Realm app. Make sure that API KEY is correct.")


        }

//        result.addChangeListener(callback)
    }

    private fun updateClientView() {

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_b_test)

//        realm = Realm.getDefaultInstance()

    }

//        realm = Realm.getDefaultInstance()
//
//        user = realmApp.currentUser()
//
//        val sharedPreference =  getSharedPreferences("prefs name", Context.MODE_PRIVATE)
//        partition = sharedPreference.getString("partition","101")!!
////        Log.v(TAG "Partition value passed: ${partition}")
//        val config = SyncConfiguration.Builder(user!!, partition)
//            .waitForInitialRemoteData()
//            .build()
//
//        // save this configuration as the default for this entire app so other activities and threads can open their own realm instances
//        Realm.setDefaultConfiguration(config)
//
//        // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
//        Realm.getInstanceAsync(config, object: Realm.Callback() {
//            override fun onSuccess(realm: Realm) {
//                // since this realm should live exactly as long as this activity, assign the realm to a member variable
//                this@DBTestActivity.realm = realm
////                setUpRecyclerView(realm)
//            }
//        })

    fun readClients(realm : Realm) : RealmResults<Client> {
//        val query = realm.where<Client.class>().findAll()
//        val realmBack = Realm.getDefaultInstance()
        var clients : RealmResults<Client> = realm.where<Client>().findAll()

        return clients

//        var query : RealmQuery<Client> = realm.where(Client).findAll()
//
//        val clients: List<Client> = realm.where(Client.class).findAll()

//        var text = StringBuilder()
//        for (i in clients.indices) {
//            text.append(i)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}