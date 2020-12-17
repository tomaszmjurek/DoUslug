package pp.inzynierka.douslug.db

import android.util.Log
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import pp.inzynierka.douslug.model.Client
import pp.inzynierka.douslug.model.Service

object DBController {

    private val realm = Realm.getDefaultInstance()
    private val TAG: String = "DB CONTROLLER"

//    fun insertVisit(visit: Visit) {
//        val backgroundRealm = Realm.getDefaultInstance()
//        backgroundRealm.executeTransactionAsync {realm ->
//            realm.insert(visit)
//            Log.v(TAG, "Inserted visit $visit into Realm")
//        }
//        backgroundRealm.close()
//    }

    fun generateUserId() : String {
        val maxId = realm.where<Client>().max("user_id") ?: return "100"
        return (maxId.toInt() + 1).toString()
    }

    fun findAllServices() : RealmResults<Service> {
        return realm.where<Service>().findAllAsync()
    }


    fun findAllClients() : RealmResults<Client> {
        var clients : RealmResults<Client> = realm.where<Client>().findAllAsync() //user_id == nasz
        Log.v(TAG, "Retrieved clients result $clients")
        val clients2 = ArrayList<Client>(realm.where<Client>().findAllAsync())
        Log.v(TAG, "Retrieved clients result $clients")
//        val clientList = clients.toList() ?
        return clients
//        if (clients != null) {
////            textView.text = clients.getOrNull(0).toString()
//            textView.text = clients.toString()
//            Log.v(TAG, "Retrieved client list is $clients")
//        } else {
//            Log.v(TAG, "Retrieved client list is null $clients")
//        }
    }

    fun findClientByPhoneNum(phoneNum: String) : RealmResults<Client> {
        return realm.where<Client>().equalTo("phone_num", phoneNum).findAllAsync()
    }

    fun insertService(service: Service) {
        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync { realm ->
            realm.insert(service)
            Log.v(TAG, "Inserted service $service into Realm")
        }
        backgroundRealm.close()
    }

    //
    fun insertClient(client: Client) {
        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync { realm ->
            realm.insert(client)
            Log.v(TAG, "Inserted client $client into Realm")
        }
        backgroundRealm.close()
    }

    fun updateClient() {
        //to musi byc chyba find najpierw a potem poprostu zmieniamy i bedzie synchronizacja
    }
}