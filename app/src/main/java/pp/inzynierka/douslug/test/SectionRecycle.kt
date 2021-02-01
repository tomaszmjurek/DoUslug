package pp.inzynierka.douslug.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.calendar.DateConverter
import pp.inzynierka.douslug.model.Visit

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

internal class SectionRecycle (private val sectionedVisits: List<RecyclerItem>, val listener: OnItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var _parent: ViewGroup
    private val TYPE_SECTION = 0
    private val TYPE_VISIT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {
        TYPE_SECTION -> WeekNameViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.week_section_title, parent, false))
        TYPE_VISIT -> VisitViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
        else -> WeekNameViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.week_section_title, parent, false)) //error
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = sectionedVisits[holder.adapterPosition]) {
            is RecyclerItem.WeekVisit ->  {
                        (holder as VisitViewHolder).data = item.visit
                        holder.title.text = item.visit.service_id?.name + ": " + item.visit.client_id?.first_name + " " + item.visit.client_id?.last_name
                        holder.text1.text = DateConverter.combineTimestampWithDuration(item.visit.date, item.visit.service_id?.duration_min)
            }
            is RecyclerItem.Section -> {
                (holder as WeekNameViewHolder).weekName.text = item.title
            }
        }
//        val obj: Visit? = getItem(position)
//        holder.data = obj
//        holder.title.text = obj?.service_id?.name + ": " + obj?.client_id?.first_name + " " + obj?.client_id?.last_name
//        holder.text1.text = DateConverter.combineTimestampWithDuration(obj?.date, obj?.service_id?.duration_min)
    }

     //todo przekazywać zamiast realmresult List<RecycleItem>
    override fun getItemViewType(position: Int) = when(sectionedVisits[position]) {
        is RecyclerItem.Section -> TYPE_SECTION
        is RecyclerItem.WeekVisit -> TYPE_VISIT
    }

    inner class WeekNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var weekName: TextView = itemView.findViewById(R.id.day_name_text)
        var button: Button = itemView.findViewById(R.id.add_visit_button)
    }

    inner class VisitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var data: Visit? = null
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

    override fun getItemCount() = sectionedVisits.size
}