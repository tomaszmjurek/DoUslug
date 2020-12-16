package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId;

open class Service(
    duration_min: Long = 0,
    name: String = "",
    price: Double = 0.0
) : RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var _partition: String = "dev"
    var duration_min: Long = duration_min
    var name: String = name
    var price: Double = price
}



