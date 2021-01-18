package pp.inzynierka.douslug.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.calendar.DateConverter
import pp.inzynierka.douslug.model.Visit


internal class NotPaidVisitAdapter(data: OrderedRealmCollection<Visit>) : RealmRecyclerViewAdapter<Visit, NotPaidVisitAdapter.TaskViewHolder?>(data, true) {
    lateinit var _parent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.not_paid_item_view, parent, false)
        _parent = parent
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val obj: Visit? = getItem(position)
        holder.data = obj
        holder.title.text = "${obj?.service_id?.name}: ${obj?.client_id?.first_name} ${obj?.client_id?.last_name}"
        holder.text1.text = DateConverter.timestampToDateString(obj!!.date)
//        holder.button.setOnClickListener {  }
    }

    internal inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var data: Visit? = null
        var title: TextView = view.findViewById(R.id.itemTitle)
        var text1: TextView = view.findViewById(R.id.itemText1)
        var button: Button = view.findViewById(R.id.itemButton)
    }
}