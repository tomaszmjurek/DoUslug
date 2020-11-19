package pp.inzynierka.douslug

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var titles = arrayOf(
        "John Lennon", "Maryla Rodowicz", "Krzysztof Ibisz"
    )
    var dates = arrayOf(
        "20.11.2020 12:30 - 13:30", "20.11.2020 13:30 - 14:30", "20.11.2020 14:30 - 16:00"
    )
    var notes = arrayOf(
        "włosy posklejane czymś czerwonym", "najpierw trzeba rozmrozić", "5 razy musisz mu wszystko potwierdzać"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val adapter = VisitListView(this, titles, dates, notes)
        val list = findViewById<View>(R.id.upcomingVisitsList) as ListView
        list.adapter = adapter
    }
}