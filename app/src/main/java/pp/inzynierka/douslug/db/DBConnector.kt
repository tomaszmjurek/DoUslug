package pp.inzynierka.douslug.db

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import pp.inzynierka.douslug.BuildConfig

lateinit var realmApp : App
lateinit var partition : String

class DBConnector : Application() {
    private lateinit var user: User
    private val TAG: String = "DB CONNECTOR"

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        realmApp = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .baseUrl(BuildConfig.MONGODB_REALM_URL)
//                .appName(BuildConfig.VERSION_NAME)
//                .appVersion(BuildConfig.VERSION_CODE.toString())
                .build()
        )

        // Enable more logging in DEBUG MODE
        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(LogLevel.ALL)
        }

        val credentials = Credentials.anonymous()
//        val credentials = Credentials.apiKey("HRc2eEszZ8KPwaGJxmXpqtKdhGVtsljbJ2UmQLjycxuSX0Mbi9n0HNrjE9RjrLXt")
        realmApp.loginAsync(credentials) {
            if (it.isSuccess) {
                Log.v(TAG, "Logged in to Atlas successfully")
                try {
                    user = realmApp.currentUser()!!
                    Log.v(TAG, "Realm user = $user")
                } catch (e: IllegalStateException) {
                    RealmLog.warn(e)
                }

                if (user != null) {
                    partition = "dev"
//                    val sharedPreference = getSharedPreferences("prefs name", Context.MODE_PRIVATE)
//                    partition = sharedPreference.getString("partition", "10001")!!
                    Log.v(TAG, "Partition set to $partition")
                    val config = SyncConfiguration.Builder(user!!, partition)
                        .waitForInitialRemoteData()
                        .build()

                    // This configuration is set as default for the entire app
                    Realm.setDefaultConfiguration(config)
                }
            } else {
                Log.v(TAG, it.error.toString())
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