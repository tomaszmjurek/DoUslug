package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId;

open class User(
    @PrimaryKey var _id: ObjectId = ObjectId(),
    var user_id: String = "",
    var default_calendar: Long = 0,
    var email: String = "",
    var official_name: String = "",
    var password: String = "",
    var phone_num: String = "",
    var worker_mode: Boolean = false
): RealmObject() {}