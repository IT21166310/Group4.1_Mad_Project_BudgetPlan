package com.example.myjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myjob.model.ExpensesModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class addNewExpenses : AppCompatActivity() {
    private lateinit var etExpName: EditText
    private lateinit var etExpAmount:EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_expenses)

        etExpName = findViewById(R.id.etExpName)
        etExpAmount = findViewById(R.id.etExpAmount)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef= FirebaseDatabase.getInstance().getReference("Expenses")

        btnSaveData.setOnClickListener {
            createExpenses ()
        }
    }

    private fun createExpenses() {
        //getting values
        val expName =etExpName.text.toString()
        val expAmount = etExpAmount.text.toString()

        if (expName.isEmpty()){
            etExpName.error = "Please enter the expense Name"
        }
        if (expAmount.isEmpty()){
            etExpAmount.error = "Please enter the amount"
        }

        val expId = dbRef.push().key!!

        val expenses = ExpensesModel (expId,expName,expAmount)

        dbRef.child(expId).setValue(expenses)

            .addOnCompleteListener {
                Toast.makeText(this,"data inserted !", Toast.LENGTH_LONG).show()

                etExpName.text.clear()
                etExpAmount.text.clear()


            }.addOnFailureListener{err->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()

               }

        }
}