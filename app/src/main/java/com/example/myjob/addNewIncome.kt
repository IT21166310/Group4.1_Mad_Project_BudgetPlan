package com.example.myjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myjob.model.incomeModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class addNewIncome : AppCompatActivity() {
    private lateinit var editname: EditText
    private lateinit var iamount:EditText
    private lateinit var btnCreate: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_income)

        editname = findViewById(R.id.IncomeName)
        iamount = findViewById(R.id.incmamount)
        btnCreate = findViewById(R.id.btnincomeCreate)

        dbRef= FirebaseDatabase.getInstance().getReference("income")

        btnCreate.setOnClickListener {

            incomeRate ()
        }
    }
    private fun incomeRate() {
        //getting values
        val incomeName =editname.text.toString()
        val incomeamt = iamount.text.toString()

        if (incomeName.isEmpty()){
            editname.error = "Please enter the plan Name"
        }
        if (incomeamt.isEmpty()){
            iamount.error = "Please enter the Income"
        }

        val incomeId = dbRef.push().key!!

        val income = incomeModel (incomeId,incomeName, incomeamt)

        dbRef.child(incomeId).setValue(income)

            .addOnCompleteListener {
                Toast.makeText(this,"Income Added!", Toast.LENGTH_LONG).show()

                editname.text.clear()
                iamount.text.clear()


            }.addOnFailureListener{err->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()

            }

        }

}
