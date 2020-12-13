package pp.inzynierka.douslug.model
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

//todo byc moze zmienic na Profile, moze sie mylic z Realm.User
open class User(
    var default_calendar: Long = 0, //todo enum na 1, 7 lub 30
    var email: String = "",
    var official_name: String = "",
    var password: String = "",
    var phone_num: String = "",
    var user_id: String = ""
) : RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var worker_mode: Boolean = true
}