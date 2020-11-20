package pp.inzynierka.douslug

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class VisitListView // TODO Auto-generated constructor stub
    (
    private val context: Activity,
    private val visitTitle: Array<String>,
    private val visitDate: Array<String>,
    private val visitNotes: Array<String>
) : ArrayAdapter<String?>(context, R.layout.visit_list, visitTitle) {
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
        titleText.text = visitTitle[position]
        dateText.text = visitDate[position]
        notesText.text = visitNotes[position]

        return rowView
    }

}