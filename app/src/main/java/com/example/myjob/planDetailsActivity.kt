package com.example.myjob

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myjob.model.PlanModel
import com.google.firebase.database.FirebaseDatabase

class planDetailsActivity : AppCompatActivity() {

    private lateinit var tvPlanId: TextView
    private lateinit var tvPlanName: TextView
    private lateinit var tvplanIncome: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.budgetplanview)

        initView()
        setValuesToViews()


        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("planId").toString(),
                intent.getStringExtra("planName").toString()

            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("planId").toString()
            )
        }

    }

    private fun deleteRecord(id: String) {
        val dbRef= FirebaseDatabase.getInstance().getReference("BudgetPlan").child(id)
        val pTask = dbRef.removeValue()

        pTask.addOnSuccessListener {
            Toast.makeText(this, "Plan data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this,planList::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }

    }

    private fun setValuesToViews() {
        tvPlanId.text=intent.getStringExtra("planId")
        tvPlanName.text=intent.getStringExtra("planName")
        tvplanIncome.text=intent.getStringExtra("income")
    }

    private fun initView() {

        tvPlanId = findViewById(R.id.planId)
        tvPlanName = findViewById(R.id.planName)
        tvplanIncome = findViewById(R.id.planIncome)

        btnUpdate = findViewById(R.id.BTNplanUpdate)
        btnDelete = findViewById(R.id.BTNplanDelete)

    }
    private fun openUpdateDialog(planId: String, planName: String) {
        val pDialog =  AlertDialog.Builder(this)
        val inflater = layoutInflater
        val pDialogView= inflater.inflate(R.layout.update_plan,null)

        pDialog.setView(pDialogView)

        val edtplanName = pDialogView.findViewById<EditText>(R.id.edtplanName)
        val edtincomeamnt = pDialogView.findViewById<EditText>(R.id.edtincomeamnt)
        val btnUpdate = pDialogView.findViewById<Button>(R.id.btnUpdate)

        edtplanName.setText(intent.getStringExtra("planName").toString())
        edtincomeamnt.setText(intent.getStringExtra("income").toString())

        pDialog.setTitle("Updating $planName Record")

        val alertDialog= pDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener {
            updatePlanData (
                planId,
                edtplanName.text.toString(),
                edtincomeamnt.text.toString()
            )

            Toast.makeText(applicationContext,"Plan Data Updated", Toast.LENGTH_LONG).show()

            //setting updated data to our textviews
            tvPlanName.text= edtplanName.text.toString()
            tvplanIncome.text= edtincomeamnt.text.toString()

            alertDialog.dismiss()
        }
    }
    private fun updatePlanData (
        id:String,
        name:String,
        income: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("BudgetPlan").child(id)
        val planInfo =  PlanModel(id,name,income)
        dbRef.setValue(planInfo)
        }
}