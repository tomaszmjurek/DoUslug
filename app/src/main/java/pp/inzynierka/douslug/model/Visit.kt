package pp.inzynierka.douslug.model
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import java.util.Date;
import org.bson.types.ObjectId;

open class visit(
    @PrimaryKey var _id: ObjectId = ObjectId(),
    var client_id: client? = null,
    var date: Date = Date(),
    var service_id: service? = null,
    var user_id: user? = null
): RealmObject() {}