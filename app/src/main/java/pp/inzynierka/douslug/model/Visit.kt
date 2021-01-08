package pp.inzynierka.douslug.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

open class Visit(
    client_id: Client? = null,
    date: Date = Date(),
    service_id: Service? = null
): RealmObject() {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var _partition: String = pp.inzynierka.douslug.db.partition
    var client_id: Client? = client_id
    var date: Date = date
    var service_id: Service? = service_id
}