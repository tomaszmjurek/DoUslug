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
import pp.inzynierka.douslug.model.Visit

class VisitActivity : AppCompatActivity() {
    private val TAG = "VISITS_ACTIVITY"
    private var editMode: Boolean = false
    private var addMode: Boolean = false
    private var visit: Visit = Visit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit)
        val fields = arrayOf(
            findViewById<EditText>(R.id.timeEdit),
            findViewById<EditText>(R.id.durationEdit),
            findViewById<EditText>(R.id.placeEdit),
            findViewById<EditText>(R.id.amountEdit),
            findViewById<EditText>(R.id.commentsEdit)
        )
        setEditable(false, fields)

        val editButton = findViewById<Button>(R.id.edit_button)
        val deleteButton = findViewById<Button>(R.id.delete_button)
        val hamburgerButton = findViewById<Button>(R.id.hamburger)

        val visitID: String? = intent.getStringExtra("visitID")
        val selectedDate: String? = intent.getStringExtra("selectedDate")

        if (visitID != null) {
            visit = DBController.findVisitById(visitID)!!
            if (visit != null) {
                displayVisit(fields)
            }
        }
        else { // add new record
            addMode = true
            emptyFields(fields)
            if (selectedDate != null) { // got the date from calendar view
                fields[0].setText(selectedDate)
            }
            enableEditMode(fields, editButton, deleteButton, hamburgerButton)
        }

        editButton.setOnClickListener { editClicked(fields, editButton, deleteButton, hamburgerButton) }
        deleteButton.setOnClickListener { deleteClicked(fields, editButton, deleteButton, hamburgerButton) }

        setUpClientSpinner()
        setUpServiceSpinner()
    }

    private fun setEditable(editable: Boolean, fields: Array<EditText>) {
        this.editMode = editable
        for (field in fields) {
            field.isEnabled = editable
            // TODO: enable / disable clicking on spinners (according to value of editable)
        }
    }

    private fun editClicked(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button) {
        if (this.editMode) { // button "Zatwierdź" clicked
            disableEditMode(fields, edit, delete, hamburger)
            if (addMode) {
                // TODO: get text from EditTexts (fields) and from the spinners
                // TODO: and save it to database as a new record
                addMode = false
            }
            else {
                // TODO: get text from EditTexts (fields) and from the spinners
                // TODO: and save it to the existing record in DB
            }
        }
        else { // button "Edytuj" clicked
            enableEditMode(fields, edit, delete, hamburger)
        }
    }

    private fun deleteClicked(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button) {
        if (this.editMode) { // button "Anuluj" clicked
            disableEditMode(fields, edit, delete, hamburger)
            if (addMode) {
                addMode = false
                onBackPressed()
            }
            else {
                displayVisit(fields)
            }
        }
        else { // button "Usuń" clicked
            // TODO: delete record from database
            onBackPressed()
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
        var clientPosition= 0
        for ((i, c) in clients.withIndex()) {
            clientsNames.add("${c.first_name} ${c.last_name}")
            if (c == visit.client_id) clientPosition = i
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, clientsNames)
        clients_spinner.adapter = adapter
        clients_spinner.setSelection(clientPosition)
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
        var servicePosition = 0
        for ((i, s) in services.withIndex()) {
            servicesNames.add(s.name)
            if (s == visit.service_id) servicePosition = i
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, servicesNames)
        services_spinner.adapter = adapter
        services_spinner.setSelection(servicePosition)
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

    private fun displayVisit(fields: Array<EditText>) {
        // TODO: wyświetl klienta i usługę (pola ze spinnerami)
        //  zgodnie z otrzymanymi danymi o wizycie
        fields[0].setText(DateConverter.timestampToDateString(visit.date!!))
        fields[1].setText(visit.service_id?.duration_min.toString())
        fields[2].setText(visit.client_id?.address)
        fields[3].setText(visit.service_id?.price.toString())
        fields[4].setText(visit.client_id?.comment)
    }

    private fun emptyFields(fields: Array<EditText>) {
        for (field in fields) {
            field.setText("")
        }
        // TODO: if possible, set spinners to display empty value
    }
}