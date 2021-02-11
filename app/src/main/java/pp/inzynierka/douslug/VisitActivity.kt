package pp.inzynierka.douslug

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_visit.*
import pp.inzynierka.douslug.calendar.DateConverter
import pp.inzynierka.douslug.data.LoginHelper
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.Client
import pp.inzynierka.douslug.model.Service
import pp.inzynierka.douslug.model.Visit

class VisitActivity : AppCompatActivity() {
    private val TAG = "VISITS_ACTIVITY"
    private var editMode: Boolean = false
    private var addMode: Boolean = false
    private var visit: Visit = Visit()
    private var serviceListClick : String = ""
    private var clientListClick : String = ""

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
        setEditable(false)


        val editButton = findViewById<Button>(R.id.edit_button)
        val deleteButton = findViewById<Button>(R.id.delete_button)
        val hamburgerButton = findViewById<Button>(R.id.hamburger)
        val backButton = findViewById<Button>(R.id.back_button)

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
        backButton.setOnClickListener { onBackPressed() }

        setUpClientSpinner()
        setUpServiceSpinner()
    }

    private fun setEditable(editable: Boolean) {
        this.editMode = editable
        if (editable == false){
            val mEdit = findViewById<EditText>(R.id.timeEdit)
            mEdit.setEnabled(false)
            val cSpinner = findViewById<Spinner>(R.id.clients_spinner)
            cSpinner.setEnabled(false)
            val sSpinner = findViewById<Spinner>(R.id.services_spinner)
            sSpinner.setEnabled(false)
        }else{
            val mEdit = findViewById<EditText>(R.id.timeEdit)
            mEdit.setEnabled(true)
            val cSpinner = findViewById<Spinner>(R.id.clients_spinner)
            cSpinner.setEnabled(true)
            val sSpinner = findViewById<Spinner>(R.id.services_spinner)
            sSpinner.setEnabled(true)
        }
    }


    private fun editClicked(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button) {
        if (this.editMode) { // button "Zatwierdź" clicked
            disableEditMode(fields, edit, delete, hamburger)
            if (addMode) {
               // createNewVisit(visit)
                addMode = false
                onBackPressed()
            }
            else {
                editVisit(visit,fields)
                onBackPressed()
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
        setEditable(true)
        edit.text = "Zatwierdź"
        delete.text = "Anuluj"
        hamburger.visibility = View.INVISIBLE
    }

    private fun disableEditMode(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button) {
        setEditable(false)
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
                clientListClick = selectedClient?._id.toString()
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
                serviceListClick = selectedService?._id.toString()
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
    private fun createNewVisit(visit: Visit) : Visit {
        val clientID = visit.client_id
        val date = visit.date
        val serviceID = visit.service_id
        val wasPaid = visit.wasPaid
        return Visit(clientID, date, serviceID, LoginHelper.getLoggedUserOrNull(this)?.user_id.toString(), wasPaid)
    }

    private fun editVisit(visit: Visit, fields: Array<EditText>) {
        val updatedVisit = createNewVisit(visit)
        updatedVisit.date =  DateConverter.dateStringToTimestamp(fields[0].text.toString())!!
        updatedVisit.service_id = DBController.findServiceById(serviceListClick)
        updatedVisit.client_id = DBController.findClientById(clientListClick)
        updatedVisit.user_id = visit.user_id
        DBController.updateVisit(updatedVisit)
        Log.v(TAG, "Client updated from ${visit} to ${updatedVisit}")
    }
}