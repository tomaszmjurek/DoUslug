package pp.inzynierka.douslug.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.calendar.DateConverter
import pp.inzynierka.douslug.model.Client


class ClientAdapter(
    data: OrderedRealmCollection<Client>,
    private val listener: OnItemClickListener
) : RealmRecyclerViewAdapter<Client, ClientAdapter.TaskViewHolder?>(data, true) {
    lateinit var _parent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        _parent = parent
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val obj: Client? = getItem(position)
        holder.data = obj
        holder.title.text = obj?.first_name + " " + obj?.last_name
        holder.text1.text = obj?.comment
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        var data: Client? = null
        var title: TextView = itemView.findViewById(R.id.itemTitle)
        var text1: TextView = itemView.findViewById(R.id.itemText1)

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface  OnItemClickListener{
        fun onItemClick(position: Int)
    }
}

