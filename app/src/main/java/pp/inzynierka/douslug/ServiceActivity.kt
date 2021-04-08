package pp.inzynierka.douslug

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.Service

//import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {

    private var editMode: Boolean = false
    private var addMode: Boolean = false
    private lateinit var service: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        val fields = arrayOf(
            findViewById<EditText>(R.id.nameEdit),
            findViewById<EditText>(R.id.timeEdit),
            findViewById<EditText>(R.id.priceEdit)
        )
        setEditable(false, fields)
        val editButton = findViewById<Button>(R.id.edit_button)
        val deleteButton = findViewById<Button>(R.id.delete_button)
        val hamburgerButton = findViewById<Button>(R.id.hamburger)
        val backButton = findViewById<Button>(R.id.back_button)

        val serviceID: String? = intent.getStringExtra("serviceID")
        if (serviceID != null) {
            service = DBController.findServiceById(serviceID)!!
            if (service != null) {
                displayService(fields)
            }
        }
        else {
            addMode = true
            emptyFields(fields)
            enableEditMode(fields, editButton, deleteButton, hamburgerButton)
        }


        editButton.setOnClickListener { editClicked(fields, editButton, deleteButton, hamburgerButton) }
        deleteButton.setOnClickListener { deleteClicked(fields, editButton, deleteButton, hamburgerButton) }
        backButton.setOnClickListener { onBackPressed() }

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
            if (addMode) {
                addNewService(fields)
                addMode = false
            }
            else {
                editService(fields)
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
                displayService(fields)
            }

            // discard changes
        }
        else { // button "Usuń" clicked
            DBController.safeDeleteService(service._id)
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

    private fun displayService(fields: Array<EditText>) {
        fields[0].setText(service.name)
        fields[1].setText(service.duration_min.toString())
        fields[2].setText(service.price.toString())
    }

    private fun emptyFields(fields: Array<EditText>) {
        for (field in fields) {
            field.setText("")
        }
    }

    private fun addNewService(fields: Array<EditText>) {
        val newService = createNewService(fields)
        DBController.insertService(newService)
    }

    private fun createNewService(fields: Array<EditText>) : Service {
        val name = fields[0].text.toString()
        val time = fields[1].text.toString().toLong()
        val price = fields[2].text.toString().toDouble()
        return Service(time, name, price, DBController.getUserId())
    }

    private fun editService(fields: Array<EditText>) {
        val updatedService = createNewService(fields)
        updatedService._id = service._id
        DBController.updateService(updatedService)
        //Log.v(TAG, "Service updated from ${service.toString()} to ${updatedService.toString()}")
    }

}