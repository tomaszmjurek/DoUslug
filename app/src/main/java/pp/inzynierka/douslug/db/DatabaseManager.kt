package pp.inzynierka.douslug.db

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import org.bson.Document
import pp.inzynierka.douslug.BuildConfig

lateinit var realmApp : App

class DatabaseManager : Application() {
    lateinit var uiThreadRealm: Realm
    private var mongoClient: MongoClient? = null
    private lateinit var mongoCollection: MongoCollection<Document>
    private lateinit var user: User
    private var TAG: String = "EXAMPLE DB CONTROLLER"

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        realmApp = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .baseUrl(BuildConfig.MONGODB_REALM_URL)
                .appName(BuildConfig.VERSION_NAME)
                .appVersion(BuildConfig.VERSION_CODE.toString())
                .build()
        )

        // Enable more logging in DEBUG MODE
        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(LogLevel.ALL)
        }

        // an authenticated user is required to access a MongoDB instance
        // THIS CODE SHOULD BE EXECUTED AFTER SUCCESSFUL USER LOGIN chyba
//        afterLogin()
    }

    fun afterLogin() {
        val credentials :  Credentials = Credentials.apiKey("cOJH91AmPbUKlH8Z7UhwRze7YcdCBYJbTmFgZXqwtwChUlrJ0W5fZWAMRPXpza0r")

        realmApp.loginAsync(credentials) {
            if (it.isSuccess) {
                val user = realmApp.currentUser()!!
//                mongoClient = user.getMongoClient("mongodb+srv://full_db_user:svErN0MgTvrgCuWE8AZr@douslug-cluster.mmign.mongodb.net/DoUslugDB?retryWrites=true&w=majority")
                if (mongoClient != null) {
                    val mongoDatabase : MongoDatabase = mongoClient!!.getDatabase("DoUslugDB")//!!.getCollection("collection name")
                    Log.v(TAG, "Successfully connected to the MongoDB instance." )
                } else {
                    Log.e(TAG, "Error connecting to the MongoDB instance.")
                }
            }
            else {
                Log.e(TAG, "Error logging into the Realm app. Make sure that API KEY is correct.")
            }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        // the ui thread realm uses asynchronous transactions, so we can only safely close the realm
//        // when the activity ends and we can safely assume that those transactions have completed
//        uiThreadRealm.close()
//        realmApp.currentUser()?.logOutAsync() {
//            if (it.isSuccess) {
//                Log.v("QUICKSTART", "Successfully logged out.")
//            } else {
//                Log.e("QUICKSTART", "Failed to log out, error: ${it.error}")
//            }
//        }
//    }
}