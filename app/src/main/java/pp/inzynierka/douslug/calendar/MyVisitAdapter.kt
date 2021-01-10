package pp.inzynierka.douslug.calendar

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import io.realm.RealmResults
import pp.inzynierka.douslug.R
import pp.inzynierka.douslug.model.Visit

class MyVisitAdapter (private val context: Activity, private val realmResults: OrderedRealmCollection<Visit?>?) : //?
    RealmBaseAdapter<Visit?>(realmResults), ListAdapter {
//    (
//    private val context: Activity,
//    private val realmResults: RealmResults<Visit>
////    private val visitTitle: Array<String>,
////    private val visitDate: Array<String>,
////    private val visitNotes: Array<String>
//) : RealmBaseAdapter<Visit?>( realmResults) {
    override fun getView(
        position: Int,
        view: View?,
        parent: ViewGroup
    ): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.visit_list, null, true)
        val titleText =
            rowView.findViewById<View>(R.id.visitListTitle) as TextView
        val dateText =
            rowView.findViewById<View>(R.id.visitListDate) as TextView
        val notesText =
            rowView.findViewById<View>(R.id.visitListNotes) as TextView
        titleText.text = realmResults?.get(position)?.client_id?.first_name.toString()
        dateText.text = realmResults?.get(position)?.service_id?.name.toString()
        notesText.text = realmResults?.get(position)?.date.toString()

        return rowView
    }

}