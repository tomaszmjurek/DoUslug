package pp.inzynierka.douslug.model
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class Client(
    address: String = "",
    comment: String = "",
    first_name: String = "",
    last_name: String = "",
    phone_num: String = ""
) : RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var _partition: String = "dev"
    var address: String = address
    var comment: String = comment
    var first_name: String = first_name
    var last_name: String = last_name
    var phone_num: String = phone_num
}