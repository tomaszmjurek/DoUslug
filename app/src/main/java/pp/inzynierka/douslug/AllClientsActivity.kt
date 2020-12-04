package pp.inzynierka.douslug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_all_clients.*


class AllClientsActivity : AppCompatActivity() {
    var dates = arrayOf(
        "20.11.2020 12:30 - 13:30", "20.11.2020 13:30 - 14:30", "20.11.2020 14:30 - 16:00","20.11.2020 12:30 - 13:30", "20.11.2020 13:30 - 14:30", "20.11.2020 14:30 - 16:00","20.11.2020 12:30 - 13:30", "20.11.2020 13:30 - 14:30", "20.11.2020 14:30 - 16:00"
        ,"20.11.2020 12:30 - 13:30", "20.11.2020 13:30 - 14:30", "20.11.2020 14:30 - 16:00"
    )

    var titles = arrayOf(
        "John Lennon", "Maryla Rodowicz", "Krzysztof Ibisz","John Lennon", "Maryla Rodowicz", "Krzysztof Ibisz","John Lennon", "Maryla Rodowicz", "Krzysztof Ibisz","John Lennon", "Maryla Rodowicz", "Krzysztof Ibisz"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_clients)


        val adapter = VisitListView(this, dates,  titles)
        clients_list.adapter = adapter
    }
}