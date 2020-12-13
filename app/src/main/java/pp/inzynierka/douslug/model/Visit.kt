package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import java.util.Date;
import org.bson.types.ObjectId;

//todo: user_id zaczytywane z partition (najpiew dodać logowanie)
open class Visit(
    client_id: Client? = null,
    date: Date = Date(),
    service_id: Service? = null,
    user_id: String = ""
) : RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var client_id: Client? = null
    var date: Date = Date()
    var service_id: Service? = null
    var user_id: String = ""
}