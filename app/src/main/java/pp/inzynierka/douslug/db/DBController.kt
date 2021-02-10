package pp.inzynierka.douslug.db

import android.util.Log
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import org.bson.types.ObjectId
import pp.inzynierka.douslug.calendar.DateConverter
import pp.inzynierka.douslug.model.Client
import pp.inzynierka.douslug.model.Service
import pp.inzynierka.douslug.model.Visit
import pp.inzynierka.douslug.model.appUser

//todo po zalogowaniu ustawić globalna zmienna appUserId i wykorzystać tutaj przy każdym find()
object DBController {

    private val realm = Realm.getDefaultInstance()
    private val TAG: String = "DB CONTROLLER"

    private lateinit var userId: String

    fun setUserId(id: String) {
        userId = id
    }

    fun getUserId() : String {
        return userId
    }

    fun generateUserId() : String {
//        val maxId = realm.where<Client>().max("user_id") ?: return "100"
        return java.util.UUID.randomUUID().toString()
    }

    fun findAllServices() : RealmResults<Service> {
        return realm.where<Service>().equalTo("user_id", userId).findAllAsync()
    }

    fun findServiceByName(name: String) : Service? {
        return realm.where<Service>().equalTo("name", name).findAllAsync().first()
    }

    fun findServiceById(serviceID: String?) : Service? {
        return realm.where<Service>().equalTo("_id", ObjectId(serviceID)).findAllAsync().first()
    }

    fun findAllClients() : RealmResults<Client> {
        val clients : RealmResults<Client> = realm.where<Client>().equalTo("user_id", userId).findAllAsync()
        Log.v(TAG, "Retrieved clients result $clients")
        return clients
    }

    fun findClientByPhoneNum(phoneNum: String) : Client? {
        return realm.where<Client>().equalTo("user_id", userId).equalTo("phone_num", phoneNum).findAllAsync().first()
    }

    fun findClientById(clientID: String) : Client? {
        return realm.where<Client>().equalTo("_id", ObjectId(clientID)).findAllAsync().first()
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

    fun updateClient(client: Client) {
        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync { realm ->
            realm.insertOrUpdate(client)
            Log.v(TAG, "Updated client $client")
        }
        backgroundRealm.close()
    }

    fun updateVisit(visit: Visit) {
        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync { realm ->
            realm.insertOrUpdate(visit)
            Log.v(TAG, "Updated visit $visit")
        }
        backgroundRealm.close()
    }

    fun safeDeleteClient(clientID : ObjectId) {
        val backgroundRealm = Realm.getDefaultInstance()
        backgroundRealm.executeTransactionAsync { realm ->
            val client = realm.where<Client>().equalTo("_id", clientID).findFirst()
//            if (!visitWithClientExists(client)) {
                client?.deleteFromRealm()
//            }
        }
        backgroundRealm.close()
    }

//    private fun visitWithClientExists(client: Client?) : Boolean {
//        val visitsNum = realm.where<Visit>().equalTo("user_id", userId).equalTo("client_id", client._id).findAllAsync().size
//        if (visitsNum > 0) return true
//        return false
//    }


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

    fun findAllVisits(): RealmResults<Visit> {
        return realm.where<Visit>().equalTo("user_id", userId).findAllAsync()
    }

    fun findVisitById(visitID: String?) :  Visit? {
        return realm.where<Visit>().equalTo("_id", ObjectId(visitID)).findAllAsync().first()
    }

    fun findVisitsByDates(timestamp: Pair<Long?, Long?>): RealmResults<Visit> {
        Log.v(TAG, "Getting visits with date <${timestamp.first}, ${timestamp.second}>")
        return realm.where<Visit>().equalTo("user_id", userId)
            .between("date", timestamp.first!!, timestamp.second!!)
            .sort("date")
            .findAllAsync()
    }

    fun findUserByEmail(email: String) : appUser? {
        return realm.where<appUser>().equalTo("email", email).findFirst()
    }

    fun findUserByUserId(token: String) : appUser? {
        return realm.where<appUser>().equalTo("user_id", token).findFirst()
    }

    fun findPaidVisitsByDate(timestamp: Pair<Long?, Long?>) : RealmResults<Visit> {
        return realm.where<Visit>().equalTo("user_id", userId)
            .equalTo("wasPaid", true)
            .between("date", timestamp.first!!, timestamp.second!!)
            .findAllAsync()
    }

    fun findNotPaidVisits() : RealmResults<Visit> {
        return realm.where<Visit>().equalTo("user_id", userId)
            .equalTo("wasPaid", false)
            .lessThan("date", DateConverter.getCurrentTimestamp())
            .findAllAsync()
    }
}