package com.example.myjob

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myjob.model.incomeModel
import com.google.firebase.database.FirebaseDatabase

class IncomeDetailsActivity : AppCompatActivity() {
    private lateinit var tvinId: TextView
    private lateinit var tvinName: TextView
    private lateinit var tvinAmount: TextView
    private lateinit var btniUpdate: Button
    private lateinit var btniDelete: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.incomedetails)

        initView()
        setValuesToViews()


        btniUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("expId").toString(),
                intent.getStringExtra("expName").toString()

            )
        }
        btniDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("expId").toString()
            )
        }

    }

    private fun deleteRecord(incomeId: String) {
        val dbRef= FirebaseDatabase.getInstance().getReference("income").child(incomeId)
        val iTask = dbRef.removeValue()

        iTask.addOnSuccessListener {
            Toast.makeText(this, "income data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this,activity_main_income::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }

    }

    private fun setValuesToViews() {
        tvinId.text=intent.getStringExtra("expId")
        tvinName.text=intent.getStringExtra("expName")
        tvinAmount.text=intent.getStringExtra("expAmount")
    }

    private fun initView() {

        tvinId = findViewById(R.id.tvinId)
        tvinName = findViewById(R.id.tvincomeName)
        tvinAmount = findViewById(R.id.tvinAmount)

        btniUpdate = findViewById(R.id.incomebtninUpdate)
        btniDelete = findViewById(R.id.incomebtninDelete)

    }

    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(incomeId: String, incomeName: String) {
        val iDialog =  AlertDialog.Builder(this)
        val inflater = layoutInflater
        val iDialogView= inflater.inflate(R.layout.update_income,null)

        iDialog.setView(iDialogView)

        val edtinName = iDialogView.findViewById<EditText>(R.id.IncomeedtinName)
        val edtinAmount = iDialogView.findViewById<EditText>(R.id.incomeedtinamount)
        val btnUpdate = iDialogView.findViewById<Button>(R.id.BTNincomeUpdate)

        edtinName.setText(intent.getStringExtra("expName").toString())
        edtinAmount.setText(intent.getStringExtra("expAmount").toString())

        iDialog.setTitle("Updating $edtinName Record")

        val alertDialog= iDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener {
            updatePlanData (
                incomeId,
                edtinName.text.toString(),
                edtinAmount.text.toString()
            )

            Toast.makeText(applicationContext,"expense Data Updated", Toast.LENGTH_LONG).show()

            //setting updated data to our textviews
            tvinName.text= edtinName.text.toString()
            tvinAmount.text= edtinAmount.text.toString()

            alertDialog.dismiss()
        }
    }
    private fun updatePlanData (
        id:String,
        incomeName:String,
        incomeAmount:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("income").child(id)
        val incomeInfo =  incomeModel(id,incomeName,incomeAmount)
        dbRef.setValue(incomeInfo)
        }
}