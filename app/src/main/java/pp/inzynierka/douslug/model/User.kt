package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId;

open class User(user_id: String = "",
                default_calendar: Long = 0,
                email: String = "",
                official_name: String = "",
                password: String = "",
                phone_num: String = "") : RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var user_id: String = user_id
    var default_calendar: Long = default_calendar
    var email: String = email
    var official_name: String = official_name
    var password: String = password
    var phone_num: String = phone_num
    var worker_mode: Boolean = false
}