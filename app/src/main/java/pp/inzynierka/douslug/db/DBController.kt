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

//    fun insertVisit() {
//        val clientId = "10001"
//        //todo getServiceId
//        //todo getClientID
//        val date = Date("2020-12-14T12:30:00.000+00:00")
//
////        var visit = Visit(partition, "0001", date, "")
//
//        val backgroundRealm = Realm.getDefaultInstance()
//        backgroundRealm.executeTransactionAsync {realm ->
//            realm.insert(visit)
//        }
//        backgroundRealm.close()
//    }

    fun showServices() : RealmResults<Service> {
        return realm.where<Service>().findAllAsync()
    }


    fun showClients() : RealmResults<Client> {
        var clients : RealmResults<Client> = realm.where<Client>().findAllAsync()
//        val clients = ArrayList<Client>(realm.where<Client>().findAllAsync())
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
}