package pp.inzynierka.douslug.db

import android.util.Log
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import pp.inzynierka.douslug.model.Client
import pp.inzynierka.douslug.model.Service
import pp.inzynierka.douslug.model.Visit
import pp.inzynierka.douslug.model.appUser

//todo po zalogowaniu ustawić globalna zmienna appUserId i wykorzystać tutaj przy każdym find()
object DBController {

    private val realm = Realm.getDefaultInstance()
    private val TAG: String = "DB CONTROLLER"

    fun generateUserId() : String {
        val maxId = realm.where<Client>().max("user_id") ?: return "100"
        return (maxId.toInt() + 1).toString()
    }

    fun findAllServices() : RealmResults<Service> {
        return realm.where<Service>().findAllAsync()
    }

    fun findServiceByName(name: String) : Service? {
        return realm.where<Service>().equalTo("name", name).findAllAsync().first()
    }


    fun findAllClients() : RealmResults<Client> {
        var clients : RealmResults<Client> = realm.where<Client>().findAllAsync()
        Log.v(TAG, "Retrieved clients result $clients")
//        val clients2 = ArrayList<Client>(realm.where<Client>().findAllAsync())
        return clients
    }

    fun findClientByPhoneNum(phoneNum: String) : Client? {
        return realm.where<Client>().equalTo("phone_num", phoneNum).findAllAsync().first() //.equalTo("user_id", appUserId)
    }

    fun insertService(service: Service) {
        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync { realm ->
            realm.insert(service)
            Log.v(TAG, "Inserted service $service into Realm")
        }
        backgroundRealm.close()
    }

    fun insertClient(client: Client) {
        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync { realm ->
            realm.insert(client)
            Log.v(TAG, "Inserted client $client into Realm")
        }
        backgroundRealm.close()
    }

    fun insertVisit(visit: Visit) {
        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync {realm ->
            realm.insert(visit)
            Log.v(TAG, "Inserted visit $visit into Realm")
        }
        backgroundRealm.close()
    }

    fun insertUser(user: appUser) {
        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync {realm ->
            realm.insert(user)
            Log.v(TAG, "Inserted user $user into Realm")
        }
        backgroundRealm.close()
    }
}