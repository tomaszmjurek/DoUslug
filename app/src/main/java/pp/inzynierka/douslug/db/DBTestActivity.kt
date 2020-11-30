package pp.inzynierka.douslug.db

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.Realm
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.mongo.MongoDatabase
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.activity_d_b_test.*
import pp.inzynierka.douslug.BuildConfig
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.model.Client

class DBTestActivity : AppCompatActivity() {
//    val config = SyncConfiguration.Builder(user!!, "user=${user!!.id}")
    private lateinit var realm: Realm
    private var user: User? = null
    private lateinit var partition: String
    private var TAG: String = "DB_TEST_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_b_test)

        val credentials : Credentials = Credentials.apiKey("cOJH91AmPbUKlH8Z7UhwRze7YcdCBYJbTmFgZXqwtwChUlrJ0W5fZWAMRPXpza0r")


        realmApp.loginAsync(credentials) {
            if (it.isSuccess) {
                val user = realmApp.currentUser()!!
//                mongoClient = user.getMongoClient("mongodb+srv://full_db_user:svErN0MgTvrgCuWE8AZr@douslug-cluster.mmign.mongodb.net/DoUslugDB?retryWrites=true&w=majority")
//                if (mongoClient != null) {
//                    val mongoDatabase : MongoDatabase = mongoClient!!.getDatabase("DoUslugDB")//!!.getCollection("collection name")
                Log.v(TAG, "Successfully connected to the MongoDB instance.")
                textView.text = "Successfully connected to the MongoDB instance."
//                } else {
//                    Log.e(TAG, "Error connecting to the MongoDB instance.")
//                }
            } else {
                textView.text = "Error logging into the Realm app. Make sure that API KEY is correct."
                Log.e(TAG, "Error logging into the Realm app. Make sure that API KEY is correct.")
            }
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
    }

    fun readClients() {
        val clients: List<Client> = realm!!.where(Client::class.java).findAll()

        var text = StringBuilder()
        for (i in clients.indices) {
            text.append(i)
        }

        textView.text = text
    }
}