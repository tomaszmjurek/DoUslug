package pp.inzynierka.douslug

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.Client
import pp.inzynierka.douslug.model.appUser

class ClientActivity : AppCompatActivity() {
    private val TAG = "CLIENT_ACTIVITY"

    private var editMode: Boolean = false
    private var addMode: Boolean = false
    private lateinit var client: Client
    private lateinit var phoneIcon: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        val fields = arrayOf(
            findViewById<EditText>(R.id.clientNameEdit),
            findViewById<EditText>(R.id.phoneNumberEdit),
            findViewById<EditText>(R.id.addressEdit),
            findViewById<EditText>(R.id.commentsEdit)
        )

        val editButton = findViewById<Button>(R.id.edit_button)
        val deleteButton = findViewById<Button>(R.id.delete_button)
        val hamburgerButton = findViewById<Button>(R.id.hamburger)
        val showVisitsButton = findViewById<Button>(R.id.show_visits_button)
        phoneIcon = findViewById<Button>(R.id.phoneButton)

        setEditable(false, fields)

        val clientID: String? = intent.getStringExtra("clientID")
        if (clientID != null) {
            client = DBController.findClientById(clientID)!!
            if (client != null){
                displayClient(fields)
            }
        }
        else {
            addMode = true
            emptyFields(fields)
            enableEditMode(fields, editButton, deleteButton, hamburgerButton, showVisitsButton)
        }


        editButton.setOnClickListener { editClicked(fields, editButton, deleteButton, hamburgerButton, showVisitsButton) }
        deleteButton.setOnClickListener { deleteClicked(fields, editButton, deleteButton, hamburgerButton, showVisitsButton) }

    }

    private fun setEditable(editable: Boolean, fields: Array<EditText>) {
        this.editMode = editable
        for (field in fields) {
            field.isEnabled = editable
        }
    }

    private fun editClicked(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button, allVisits: Button) {
        if (this.editMode) { // button "Zatwierdź" clicked
            disableEditMode(fields, edit, delete, hamburger, allVisits)
            if (addMode){
                addNewClient(fields)
                addMode = false
            }
            else {
                //client.comment = fields[3].getText().toString() //it doesn't work, app crashes
                editClient(fields)
                // TODO:
                // save changes to the existing record
            }
        }
        else { //button "Edytuj" clicked
            enableEditMode(fields, edit, delete, hamburger, allVisits)
        }
    }

    private fun deleteClicked(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button, allVisits: Button) {
        if (this.editMode) { // button "Anuluj" clicked
            disableEditMode(fields, edit, delete, hamburger, allVisits)
            if (addMode) {
                addMode = false
                // TODO
                // come back to the previous view
            }
            else {
                displayClient(fields)
            }
        }
        else { // button "Usuń" clicked
            DBController.safeDeleteClient(client._id)
            onBackPressed() // to cofa do poprzedniego ekranu
        }
    }

    private fun enableEditMode(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button, allVisits: Button) {
        setEditable(true, fields)
        edit.text = "Zatwierdź"
        delete.text = "Anuluj"
        hamburger.visibility = View.INVISIBLE
        allVisits.visibility = View.INVISIBLE
        //phoneIcon.visibility = View.INVISIBLE
    }

    private fun disableEditMode(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button, allVisits: Button) {
        setEditable(false, fields)
        edit.text = "Edytuj"
        delete.text = "Usuń"
        hamburger.visibility = View.VISIBLE
        allVisits.visibility = View.VISIBLE
        //phoneIcon.visibility = View.VISIBLE
    }

    private fun displayClient(fields: Array<EditText>) {
        fields[0].setText(client.first_name + " " + client.last_name)
        fields[1].setText(client.phone_num)
        fields[2].setText(client.address)
        fields[3].setText(client.comment)
    }

    private fun emptyFields(fields: Array<EditText>) {
        for (field in fields) {
            field.setText("")
        }
    }

    private fun addNewClient(fields: Array<EditText>) {
        val newClient = createNewClient(fields)
        DBController.insertClient(newClient)
    }

    private fun createNewClient(fields: Array<EditText>) : Client {
        val lastName = fields[0].text.toString().split(" ").last()
        val firstName = fields[0].text.toString().split(" ").first()
        val address = fields[2].text.toString()
        val comment = fields[3].text.toString()
        val phoneNumber = fields[1].text.toString()
        return Client(address, comment, firstName, lastName, phoneNumber, DBController.getUserId())
    }

    private fun editClient(fields: Array<EditText>) {
        val updatedClient = createNewClient(fields)
        updatedClient._id = client._id
        DBController.updateClient(updatedClient)
        Log.v(TAG, "Client updated from ${client.toString()} to ${updatedClient.toString()}")
    }

}