package pp.inzynierka.douslug

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_visit.*
import pp.inzynierka.douslug.calendar.DateConverter
import pp.inzynierka.douslug.db.DBController

class VisitActivity : AppCompatActivity() {
    private val TAG = "VISITS_ACTIVITY"
    private var editMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit)
        val fields = arrayOf(
//            findViewById<EditText>(R.id.clientEdit),
//            findViewById<EditText>(R.id.servicesEdit), //todo to sa inne komponenty, wiec nie moga byc w tej liście, reszta się przesuneła o 2 indeksy, to trzeba poprawic
            findViewById<EditText>(R.id.timeEdit),
            findViewById<EditText>(R.id.durationEdit),
            findViewById<EditText>(R.id.placeEdit),
            findViewById<EditText>(R.id.amountEdit),
            findViewById<EditText>(R.id.commentsEdit)
        )


        val editButton = findViewById<Button>(R.id.edit_button)
        val deleteButton = findViewById<Button>(R.id.delete_button)
        val hamburgerButton = findViewById<Button>(R.id.hamburger)

        val visitID: String? = intent.getStringExtra("visitID")
        if (visitID != null) {
            var visit = DBController.findVisitById(visitID)
            if (visit != null) {
                fields[0].setText(visit.client_id?.first_name + " " +  visit.client_id?.last_name)
                fields[1].setText(visit.service_id?.name)
                fields[2].setText(DateConverter.timestampToDateString(visit.date!!))
                fields[3].setText(visit.service_id?.duration_min.toString())
                fields[4].setText(visit.client_id?.address)
                fields[5].setText(visit.service_id?.price.toString())
                fields[6].setText(visit.client_id?.comment)
            }
        }


        setEditable(false, fields)
        editButton.setOnClickListener { editClicked(fields, editButton, deleteButton, hamburgerButton) }
        deleteButton.setOnClickListener { deleteClicked(fields, editButton, deleteButton, hamburgerButton) }

        setUpClientSpinner()
        setUpServiceSpinner()
    }

    private fun setEditable(editable: Boolean, fields: Array<EditText>) {
        this.editMode = editable
        for (field in fields) {
            field.isEnabled = editable
        }
    }

    private fun editClicked(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button) {
        if (this.editMode) { // button "Zatwierdź" clicked
            disableEditMode(fields, edit, delete, hamburger)
            // TODO: save changes to database
        }
        else { // button "Edytuj" clicked
            enableEditMode(fields, edit, delete, hamburger)
        }
    }

    private fun deleteClicked(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button) {
        if (this.editMode) { // button "Anuluj" clicked
            disableEditMode(fields, edit, delete, hamburger)
            // TODO: discard changes
        }
        else { // button "Usuń" clicked
            // TODO
            // delete record from database
            // return to the list of all services
        }
    }

    private fun enableEditMode(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button) {
        setEditable(true, fields)
        edit.text = "Zatwierdź"
        delete.text = "Anuluj"
        hamburger.visibility = View.INVISIBLE
    }

    private fun disableEditMode(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button) {
        setEditable(false, fields)
        edit.text = "Edytuj"
        delete.text = "Usuń"
        hamburger.visibility = View.VISIBLE
    }

    private fun setUpClientSpinner() {
        val clients = DBController.findAllClients()

        val clientsNames = mutableListOf<String>()
        for (c in clients) {
            clientsNames.add("${c.first_name} ${c.last_name}")
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, clientsNames)
        clients_spinner.adapter = adapter
        clients_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedClient = clients[position]
                Log.v(TAG, "Selected Client = ${selectedClient.toString()}")
                // todo dodać przypisanie do wizyty
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }
    }

    private fun setUpServiceSpinner() {
        val services = DBController.findAllServices()

        val servicesNames = mutableListOf<String>()
        for (s in services) {
            servicesNames.add(s.name)
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, servicesNames)
        services_spinner.adapter = adapter
        services_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedService = services[position]
                Log.v(TAG, "Selected Service = ${selectedService.toString()}")
                // todo dodać przypisanie do wizyty
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }
    }
}