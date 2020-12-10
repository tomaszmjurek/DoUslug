package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import java.util.Date;
import org.bson.types.ObjectId;

//todo: user_id zaczytywane z partition (najpiew dodać logowanie)
open class Visit(user_id: String = "",
                 client_id: ObjectId? = null,
                 date: Date = Date(),
                 service_id: ObjectId? = null) : RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var user_id: String = user_id
    var client_id: ObjectId? = client_id
    var date: Date = date
    var service_id: ObjectId? = service_id
}