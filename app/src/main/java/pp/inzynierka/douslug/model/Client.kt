package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId;

open class Client(
    @PrimaryKey var _id: ObjectId = ObjectId(),
    var address: String = "",
    var comment: String = "",
    var first_name: String = "",
    var last_name: String = "",
    var phone_num: String = "",
    var user_id: String = ""
): RealmObject() {}