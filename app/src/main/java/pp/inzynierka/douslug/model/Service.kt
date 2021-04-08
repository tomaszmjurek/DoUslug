package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId;

open class Service(
    duration_min: Long = 0,
    name: String = "",
    price: Double = 0.0,
    user_id: String = ""
) : RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var _partition: String = pp.inzynierka.douslug.db.partition
    var duration_min: Long = duration_min
    var name: String = name
    var price: Double = price
    var user_id: String = user_id
}



