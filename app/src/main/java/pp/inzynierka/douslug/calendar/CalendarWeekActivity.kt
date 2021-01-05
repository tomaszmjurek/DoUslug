package pp.inzynierka.douslug.calendar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import kotlinx.android.synthetic.main.activity_calendar_week.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.calendar_top_layout.*
import kotlinx.android.synthetic.main.calendar_week_element.view.*
import kotlinx.android.synthetic.main.change_calendar_type.*
import pp.inzynierka.douslug.R


class CalendarWeekActivity : AppCompatActivity() {

    private lateinit var mondayLayout : RelativeLayout
    private lateinit var tuesdayLayout : RelativeLayout
    private lateinit var wednesdayLayout : RelativeLayout
    private lateinit var linearLayout: LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_week)

//        prepareLayout()

        calendar_type_button.setOnClickListener { showCalendarChange() }
        day_button.setOnClickListener { openCalendarDayActivity() }
        week_button.setOnClickListener { showCalendarChange() }
        month_button.setOnClickListener { openCalendarMonthActivity() }
        back_button.setOnClickListener { onBackPressed() }

        var listItems = listOf("element1", "element2", "element3")

//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems)
//        mondayListView.adapter = adapter
    }

    private fun showCalendarChange() {
        if (calendar_type_layout.visibility == View.GONE) {
            calendar_type_layout.visibility = View.VISIBLE
        } else {
            calendar_type_layout.visibility = View.GONE
        }
    }

    private fun openCalendarDayActivity() {
        val intent = Intent(this@CalendarWeekActivity, CalendarDayActivity::class.java)
        startActivity(intent)
    }

    private fun openCalendarMonthActivity() {
        val intent = Intent(this@CalendarWeekActivity, CalendarMonthActivity::class.java)
        startActivity(intent)
    }

    private fun createWeekDayLayout(name: String, view: View, layout: LinearLayout) {
//        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        view.weekName.text = name

        var listView = ListView(this)
        listView.setPadding(0, 0, 0, 20)
//        listView.id = "@+id/listView"

        layout.addView(view)
        layout.addView(listView)

    }

    private fun prepareLayout() {
        val weekNames = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
//        var view = layoutInflater.inflate(R.layout.calendar_week_element, findViewById(R.id.calendarWeekElement))
        for (name in weekNames) {
//            createWeekDayLayout(name, view, weekDaysLayout)
            var view = LayoutInflater.from(this).inflate(R.layout.calendar_week_element, findViewById(mondayLayout.id))
            view.weekName.text = weekNames[0]
            weekDaysLayout.addView(view)
            var dynamicView = TextView(this) //listView
            dynamicView.setPadding(0, 0, 0, 20)
            dynamicView.text = weekNames[1]
            weekDaysLayout.addView(dynamicView)
        }


    }
}