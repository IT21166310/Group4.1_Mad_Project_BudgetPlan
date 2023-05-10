package com.example.myjob

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myjob.main_Activity_goal
import com.example.myjob.model.ExpensesModel
import com.google.firebase.database.FirebaseDatabase

class ExpensesDetailsActivity : AppCompatActivity() {
    private lateinit var tvexpId: TextView
    private lateinit var tvexpName: TextView
    private lateinit var tvexpAmount: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_details)

        initView()
        setValuesToViews()


        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("expId").toString(),
                intent.getStringExtra("expName").toString()

            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("expId").toString()
            )
        }

    }

    private fun deleteRecord(id: String) {
        val dbRef= FirebaseDatabase.getInstance().getReference("Expenses").child(id)
        val eTask = dbRef.removeValue()

        eTask.addOnSuccessListener {
            Toast.makeText(this, "exp data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ExpensesList::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }

    }

    private fun setValuesToViews() {
        tvexpId.text=intent.getStringExtra("expId")
        tvexpName.text=intent.getStringExtra("expName")
        tvexpAmount.text=intent.getStringExtra("expAmount")
    }

    private fun initView() {

        tvexpId = findViewById(R.id.tvexpId)
        tvexpName = findViewById(R.id.tvexpName)
        tvexpAmount = findViewById(R.id.tvexpAmount)

        btnUpdate = findViewById(R.id.btnexpUpdate)
        btnDelete = findViewById(R.id.btnexpDelete)

    }
    private fun openUpdateDialog(tvexpId: String, edtexpName: String) {
        val eDialog =  AlertDialog.Builder(this)
        val inflater = layoutInflater
        val eDialogView= inflater.inflate(R.layout.update_expenses,null)

        eDialog.setView(eDialogView)

        val edtexpName = eDialogView.findViewById<EditText>(R.id.edtexpName)
        val edtexpAmount = eDialogView.findViewById<EditText>(R.id.edtexpamount)
        val btnUpdate = eDialogView.findViewById<Button>(R.id.btnUpdate)

        edtexpName.setText(intent.getStringExtra("expName").toString())
        edtexpAmount.setText(intent.getStringExtra("expAmount").toString())

        eDialog.setTitle("Updating $edtexpName Record")

        val alertDialog= eDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener {
            updateexpData (
                tvexpId,
                edtexpName.text.toString(),
                edtexpAmount.text.toString()
            )

            Toast.makeText(applicationContext,"expense Data Updated", Toast.LENGTH_LONG).show()

            //setting updated data to our textviews
            tvexpName.text= edtexpName.text.toString()
            tvexpAmount.text= edtexpAmount.text.toString()

            alertDialog.dismiss()
        }
    }
    private fun updateexpData (
        id:String,
        expname:String,
        expamount: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Expenses").child(id)
        val expInfo =  ExpensesModel(id,expname,expamount)
        dbRef.setValue(expInfo)
        }
}