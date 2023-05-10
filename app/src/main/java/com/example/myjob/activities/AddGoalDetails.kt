package com.example.myjob.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myjob.R
import com.example.myjob.main_Activity_goal
import com.example.myjob.model.GoalModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddGoalDetails : AppCompatActivity() {

    private lateinit var goalName:EditText
    private lateinit var targetAmount:EditText
    private lateinit var date:EditText
    private lateinit var amount:EditText
    private lateinit var btnGoalDetails:Button
    private lateinit var dbRef:DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goal_details2)

        goalName = findViewById(R.id.goalName)
        targetAmount = findViewById(R.id.targetAmount)
        date = findViewById(R.id.date)
        amount = findViewById(R.id.addamount)
        btnGoalDetails = findViewById(R.id.addgoal)

        dbRef = FirebaseDatabase.getInstance().getReference("GoalDetails")

        btnGoalDetails.setOnClickListener {

            saveGoalDetails()
            if(targetAmount.text.toString().trim().isEmpty()&&date.text.toString().toString().trim().isEmpty()&&amount.text.toString().trim().isEmpty()){
                targetAmount.error = "required"
                Toast.makeText(this, "targetAmount  Required ", Toast.LENGTH_SHORT).show()

                date.error = "required"
                Toast.makeText(this, "Date  Required ", Toast.LENGTH_SHORT).show()

                amount.error = "required"
                Toast.makeText(this, "Amount  Required ", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, main_Activity_goal::class.java)
                startActivity(intent)
            }
        }

    }
    private fun saveGoalDetails(){

        val goalName = goalName.text.toString()

        val targetAmount = targetAmount.text.toString()

        val date = date.text.toString()

        val amount = amount.text.toString()

        val goalDetailsId = dbRef.push().key!!

        val goaldetails = GoalModel(goalDetailsId,goalName,targetAmount,date,amount,)

        dbRef.child(goalDetailsId).setValue(goaldetails)
            .addOnCompleteListener{
                Toast.makeText(this,"Data insert successfully",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{err ->

                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_SHORT).show()

            }
    }

}