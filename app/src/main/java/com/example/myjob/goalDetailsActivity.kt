package com.example.myjob

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myjob.model.GoalModel
import com.google.firebase.database.FirebaseDatabase

class goalDetailsActivity : AppCompatActivity() {
    private lateinit var tvgoalId: TextView
    private lateinit var tvgoalName: TextView
    private lateinit var tvgoaltargetamount: TextView
    private lateinit var tvgoaldate: TextView
    private lateinit var tvinsertedAmount: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plandetailview)

        initView()
        setValuesToViews()


        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("goalId").toString(),
                intent.getStringExtra("goalName").toString()

            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("goalId").toString()
            )
        }

    }

    private fun initView() {

        tvgoalId = findViewById(R.id.goalId)
        tvgoalName = findViewById(R.id.goaleName)
        tvgoaltargetamount = findViewById(R.id.targetgAmount)
        tvgoaldate = findViewById(R.id.goalDate)
        tvinsertedAmount = findViewById(R.id.insertgoalAmount)

        btnUpdate = findViewById(R.id.BTNgoalUpdate)
        btnDelete = findViewById(R.id.BTNgoalDelete)

    }
    private fun setValuesToViews() {
        tvgoalId.text = intent.getStringExtra("goalId")
        tvgoalName.text = intent.getStringExtra("goalName")
        tvgoaltargetamount.text = intent.getStringExtra("targetamount")
        tvgoaldate.text = intent.getStringExtra("goaldate")
        tvinsertedAmount.text = intent.getStringExtra("insertamount")

//        Log.d("my ID", tvgoalId.text.toString())
//        Log.d("my Name", tvgoalName.text.toString())
//        Log.d("my smoun", tvgoaltargetamount.text.toString())
//        Log.d("my dste", tvgoaldate.text.toString())
//        Log.d("my inamoi", tvinsertedAmount.text.toString())

    }


    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(goalId: String, goalName: String) {
        val pDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val pDialogView = inflater.inflate(R.layout.updategoal, null)

        pDialog.setView(pDialogView)

//        val etId = pDialogView.findViewById<EditText>(R.id.etId)
        val edtgoalName = pDialogView.findViewById<EditText>(R.id.edtgoalName)
        val edtinsertamount = pDialogView.findViewById<EditText>(R.id.insertamount)
        val edttargetamount = pDialogView.findViewById<EditText>(R.id.edittargetamount)
        val edtgoaldate = pDialogView.findViewById<EditText>(R.id.editgoaldate)
        val btnUpdate = pDialogView.findViewById<Button>(R.id.btnUpdate)

//        etId.setText(intent.getStringExtra("goalId"))
        edtgoalName.setText(intent.getStringExtra("goalName"))
        edttargetamount.setText(intent.getStringExtra("targetamount"))
        edtinsertamount.setText(intent.getStringExtra("insertamount"))
         edtgoaldate.setText(intent.getStringExtra("goaldate"))

        pDialog.setTitle("Updating $goalName Record")

        val alertDialog = pDialog.create()
        alertDialog.show()


        btnUpdate.setOnClickListener {
            updategoalData (
                goalId,
                edtgoalName.text.toString(),
                edttargetamount.text.toString(),
                edtinsertamount.text.toString(),
                edtgoaldate.text.toString()
            )

            Toast.makeText(applicationContext,"goal Data Updated", Toast.LENGTH_LONG).show()

            //setting updated data to our textviews
            tvgoalName.text= edtgoalName.text.toString()
            tvgoaltargetamount.text= edttargetamount.text.toString()
            tvgoaldate.text= edtgoaldate.text.toString()
            tvinsertedAmount.text= edtinsertamount.text.toString()

            alertDialog.dismiss()
        }
    }
    private fun updategoalData (
        id:String,
        name:String,
        targetamount: String,
        insertamount: String,
        goaldate: String,

        ){
        val dbRef = FirebaseDatabase.getInstance().getReference("GoalDetails").child(id)
        val goalInfo =  GoalModel(id,name,targetamount, insertamount, goaldate)
        dbRef.setValue(goalInfo)
        }

    private fun deleteRecord(id: String) {
        val dbRef= FirebaseDatabase.getInstance().getReference("GoalDetails")
        val pTask = dbRef.child(id).removeValue()

        pTask.addOnSuccessListener {
            Toast.makeText(this, "goal data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this,main_Activity_goal::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }

    }
}

