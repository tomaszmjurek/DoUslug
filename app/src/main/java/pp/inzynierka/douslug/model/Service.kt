package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId;

open class service(
    @PrimaryKey var _id: ObjectId = ObjectId(),
    var duration_min: Long = 0,
    var name: String = "",
    var price: Double = 0.0,
    var user_id: user? = null
): RealmObject() {}