package com.example.myjob.view.dilshan

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myjob.R
import com.example.myjob.activities.AddGoalDetails
import com.example.myjob.model.GoalModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class  AddNewGoal : AppCompatActivity() {
    private lateinit var newGoal:EditText
    private lateinit var btnNewGoal:Button
    private lateinit var dbRef:DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_goal)

        newGoal = findViewById(R.id.etGoal)
        btnNewGoal = findViewById(R.id.creatGoalBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("NewGoal")

        btnNewGoal.setOnClickListener {

            saveNewGoal()
            if(newGoal.text.toString().trim().isEmpty()){
                newGoal.error = "required"
                Toast.makeText(this, "goal Required ", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, AddGoalDetails::class.java)
                startActivity(intent)
            }

        }

    }
    private fun saveNewGoal(){
        val newgoal = newGoal.text.toString()

        if(newgoal.isEmpty()){
            newGoal.error = "please enter goal"
        }

        val goalID = dbRef.push().key!!

        val goal = GoalModel(goalID, newgoal)

        dbRef.child(goalID).setValue(goal)
            .addOnCompleteListener{
                Toast.makeText(this,"Data insert successfully",Toast.LENGTH_LONG).show()

                newGoal.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()

            }


    }
}