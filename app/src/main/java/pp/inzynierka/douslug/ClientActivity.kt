package pp.inzynierka.douslug

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ClientActivity : AppCompatActivity() {

    private var editMode: Boolean = false

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

        setEditable(false, fields)
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
            // TODO: save changes to database
        }
        else { //button "Edytuj" clicked
            enableEditMode(fields, edit, delete, hamburger, allVisits)
        }
    }

    private fun deleteClicked(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button, allVisits: Button) {
        if (this.editMode) { // button "Anuluj" clicked
            disableEditMode(fields, edit, delete, hamburger, allVisits)
            // TODO: discard changes
        }
        else { // button "Usuń" clicked
            // TODO
            // delete record from database
            // return to the list of all services
        }
    }

    private fun enableEditMode(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button, allVisits: Button) {
        setEditable(true, fields)
        edit.text = "Zatwierdź"
        delete.text = "Anuluj"
        hamburger.visibility = View.INVISIBLE
        allVisits.visibility = View.INVISIBLE
    }

    private fun disableEditMode(fields: Array<EditText>, edit: Button, delete: Button, hamburger: Button, allVisits: Button) {
        setEditable(false, fields)
        edit.text = "Edytuj"
        delete.text = "Usuń"
        hamburger.visibility = View.VISIBLE
        allVisits.visibility = View.VISIBLE
    }

}