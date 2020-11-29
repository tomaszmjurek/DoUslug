package pp.inzynierka.douslug.db

// Base Realm Packages
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
// Realm Authentication Packages
import io.realm.mongodb.User
import io.realm.mongodb.Credentials

// MongoDB Service Packages
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoDatabase
import io.realm.mongodb.mongo.MongoCollection
import kotlinx.android.synthetic.main.calendar_top_layout.*
import kotlinx.android.synthetic.main.change_calendar_type.*
// Utility Packages
import org.bson.Document
import pp.inzynierka.douslug.R

class DbControllerActivity : AppCompatActivity() {
    private lateinit var realmApp: App
    private var mongoClient: MongoClient? = null
    private lateinit var mongoCollection: MongoCollection<Document>
    private lateinit var user: User
    private var TAG: String = "EXAMPLE DB CONTROLLER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appID : String = "douslug-gemff"
        Realm.init(this)
        realmApp = App(
            AppConfiguration.Builder(appID)
                .build()
        )

        // an authenticated user is required to access a MongoDB instance
        val credentials :  Credentials = Credentials.apiKey("cOJH91AmPbUKlH8Z7UhwRze7YcdCBYJbTmFgZXqwtwChUlrJ0W5fZWAMRPXpza0r")
        realmApp.loginAsync(credentials) {
            if (it.isSuccess) {
                user = realmApp.currentUser()!!
                mongoClient = user.getMongoClient("mongodb+srv://full_db_user:svErN0MgTvrgCuWE8AZr@douslug-cluster.mmign.mongodb.net/DoUslugDB?retryWrites=true&w=majority")
                if (mongoClient != null) {
                    mongoCollection = mongoClient?.getDatabase("DoUslugDB")!!.getCollection("collection name")
                    Log.v(TAG, "Successfully connected to the MongoDB instance." )
                } else {
                    Log.e(TAG, "Error connecting to the MongoDB instance.")
                }
            }
            else {
                Log.e(TAG, "Error logging into the Realm app. Make sure that anonymous authentication is enabled.")
            }
        }
    }
}