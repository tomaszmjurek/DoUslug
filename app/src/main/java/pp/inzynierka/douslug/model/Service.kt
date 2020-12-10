package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId;

open class Service(user_id: String = "",
                   name: String = "",
                   duration_min: Long = 0,
                   price: Double = 0.0) : RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var user_id: String = user_id
    var name: String = name
    var duration_min: Long = duration_min
    var price: Double = price
}



