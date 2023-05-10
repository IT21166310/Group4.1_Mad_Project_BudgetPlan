package com.example.myjob

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myjob.adapters.ExpenseAdapter
import com.example.myjob.model.ExpensesModel
import com.google.firebase.database.*

class ExpensesList : AppCompatActivity() {
    private lateinit var expenseRecyclerView: RecyclerView
    private lateinit var etvLoadingData: TextView
    private lateinit var expenseList: ArrayList<ExpensesModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_list)

        expenseRecyclerView=findViewById(R.id.rvexpense)
        expenseRecyclerView.layoutManager= LinearLayoutManager(this)
        expenseRecyclerView.setHasFixedSize(true)
        etvLoadingData = findViewById(R.id.etvLoadingData)

        expenseList= arrayListOf<ExpensesModel>()

        getExpenseData()
    }
    private fun getExpenseData(){
        expenseRecyclerView.visibility= View.GONE
        etvLoadingData.visibility=View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Expenses")

        dbRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                expenseList.clear()
                if (snapshot.exists()){
                    for(expenseSnap in snapshot.children){
                        val expenseData = expenseSnap.getValue(ExpensesModel::class.java)
                        expenseList.add(expenseData!!)
                    }
                    val eAdapter = ExpenseAdapter(expenseList)
                    expenseRecyclerView.adapter=eAdapter

                    eAdapter.setonItemClickListener(object :ExpenseAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //user directed to see more data
                            val intent = Intent(this@ExpensesList, ExpensesDetailsActivity::class.java)

                            //put extra view
                            intent.putExtra("expId",expenseList[position].expId)
                            intent.putExtra("expName",expenseList[position].expName)
                            intent.putExtra("expAmount",expenseList[position].ExpAmount)
                            startActivity(intent)

                        }

                    })

                    expenseRecyclerView.visibility= View.VISIBLE
                    etvLoadingData.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

            })
    }
}