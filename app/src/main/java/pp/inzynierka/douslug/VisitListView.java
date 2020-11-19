package pp.inzynierka.douslug;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class VisitListView extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] visitTitle;
    private final String[] visitDate;
    private final String[] visitNotes;

    public VisitListView(Activity context, String[] visitTitle,String[] visitDate, String[] visitNotes) {
        super(context, R.layout.visit_list, visitTitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.visitTitle=visitTitle;
        this.visitDate=visitDate;
        this.visitNotes=visitNotes;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.visit_list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.visitListTitle);
        TextView dateText = (TextView) rowView.findViewById(R.id.visitListDate);
        TextView notesText = (TextView) rowView.findViewById(R.id.visitListNotes);

        titleText.setText(visitTitle[position]);
        dateText.setText(visitDate[position]);
        notesText.setText(visitNotes[position]);

        return rowView;

    };
}