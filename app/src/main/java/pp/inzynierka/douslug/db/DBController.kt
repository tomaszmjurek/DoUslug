package pp.inzynierka.douslug.db

import android.app.Application
import io.realm.Realm
import java.util.*

abstract class DBController : Application() {

    fun insertVisit() {
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
}