package pp.inzynierka.douslug.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import pp.inzynierka.douslug.db.DBController

open class appUser (
    email: String = "",
    official_name: String = "",
    password: String = "",
    phone_num: String = ""
) : RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var _partition: String = pp.inzynierka.douslug.db.partition
    var default_calendar: Long = 30
    var email: String = email
    var official_name: String = official_name
    var password: String = password
    var phone_num: String = phone_num
    var user_id: String = DBController.generateUserId()
    var worker_mode = false
}