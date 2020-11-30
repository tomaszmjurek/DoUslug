package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import java.util.Date;
import org.bson.types.ObjectId;

open class Visit(
    @PrimaryKey var _id: ObjectId = ObjectId(),
    var client_id: Client? = null,
    var date: Date = Date(),
    var service_id: Service? = null,
    var user_id: User? = null
): RealmObject() {}