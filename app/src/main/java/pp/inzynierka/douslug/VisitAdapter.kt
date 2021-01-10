package pp.inzynierka.douslug

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import pp.inzynierka.douslug.model.Visit


/*
 * ListAdapter: extends the Realm-provided RealmRecyclerViewAdapter to provide data for a RecyclerView to display
 * Realm objects on screen to a user.
 */
internal class VisitAdapter(data: OrderedRealmCollection<Visit>) : RealmRecyclerViewAdapter<Visit, VisitAdapter.TaskViewHolder?>(data, true) {
    lateinit var _parent: ViewGroup
//    private val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item_1, parent, false)
//        val inflater = context.layoutInflater
//        val itemView = inflater.inflate(pp.inzynierka.douslug.R.layout.visit_list, parent, false)
        _parent = parent
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val obj: Visit? = getItem(position)
        holder.data = obj
        holder.client_id.text = obj?.client_id?.first_name
//        holder.date.text = obj?.date.toString()
//        holder.service_id.text = obj?.service_id?.name
    }

    internal inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var data: Visit? = null
        var client_id = view.findViewById<View>(R.id.title) as TextView
//        var date: TextView = view.findViewById(R.id.text1)
//        var service_id: TextView = view.findViewById(R.id.text2)

    }
}