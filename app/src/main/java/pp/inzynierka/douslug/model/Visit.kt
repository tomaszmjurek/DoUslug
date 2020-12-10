package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import java.util.Date;
import org.bson.types.ObjectId;

//todo: user_id zaczytywane z partition (najpiew dodać logowanie)
open class Visit(
    var user_id: String = "",
    var client_id: ObjectId? = null,
    var date: Date = Date(),
    var service_id: ObjectId? = null
) : RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
}