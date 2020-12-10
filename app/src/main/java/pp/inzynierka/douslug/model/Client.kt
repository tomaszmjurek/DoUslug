package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import org.bson.types.ObjectId;

open class Client(user_id: String = "",
                  address: String = "",
                  comment: String = "",
                  first_name: String = "",
                  last_name: String = "",
                  phone_num: String = "") : RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var user_id: String = user_id
    var address: String = address
    var comment: String = comment
    @Required
    var first_name: String = first_name
    var last_name: String = last_name
    var phone_num: String = phone_num
}