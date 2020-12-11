package pp.inzynierka.douslug

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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


        val adapter = AllClientsView(this, dates,  titles)
        clients_list.adapter = adapter

        val go_back = findViewById<Button>(R.id.back_button)

        go_back.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }

        val sort = findViewById<Button>(R.id.sort_button)

        sort.setOnClickListener{
            //TODO: Stwórz popup, wybierz elementy wg których ma sortować
        }

        val filter = findViewById<Button>(R.id.filter_button)

        filter.setOnClickListener{
            //TODO: Stwórz popup, wpisz element wg którego ma filtrować
        }

        val hamburger = findViewById<Button>(R.id.hamburger)

        hamburger.setOnClickListener{
            //TODO: zerżnij od patryka slide ustawień
        }

    }
}