package pp.inzynierka.douslug.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class Visit(
    client_id: Client? = null,
    date: Long = System.currentTimeMillis(),
    service_id: Service? = null,
    user_id: String = "",
    wasPaid: Boolean = false
): RealmObject() {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var _partition: String = pp.inzynierka.douslug.db.partition
    var client_id: Client? = client_id
    var date: Long = date
    var service_id: Service? = service_id
    var user_id = user_id
    var wasPaid = wasPaid
}