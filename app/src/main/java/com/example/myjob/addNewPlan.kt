package com.example.myjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myjob.databinding.ActivityMainPlanBinding
import com.example.myjob.model.PlanModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class addNewPlan : AppCompatActivity() {
    private lateinit var editname: EditText
    private lateinit var incomeamnt:EditText
    private lateinit var btnCreate: Button
    private lateinit var dbRef: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_plan)

        editname = findViewById(R.id.editname)
        incomeamnt = findViewById(R.id.incomeamnt)
        btnCreate = findViewById(R.id.btnCreate)

        dbRef= FirebaseDatabase.getInstance().getReference("BudgetPlan")

        btnCreate.setOnClickListener {
            createBudgetplan ()

            val intent = Intent(this,mainActivity_plan::class.java)
            startActivity(intent)
        }
    }

    private fun createBudgetplan() {
        //getting values
        val planName = editname.text.toString()
        val income = incomeamnt.text.toString()

        if (planName.isEmpty()) {
            editname.error = "Please enter the plan Name"
        }
        if (income.isEmpty()) {
            incomeamnt.error = "Please enter the Income"
        }

        val planId = dbRef.push().key!!

        val plan = PlanModel(planId, planName, income)

        dbRef.child(planId).setValue(plan)

            .addOnCompleteListener {
                Toast.makeText(this, "data inserted !", Toast.LENGTH_LONG).show()

                editname.text.clear()
                incomeamnt.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()

            }

    }

}