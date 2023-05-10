package com.example.myjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.incomemad.adapters.incomeAdapter
import com.example.myjob.model.incomeModel
import com.google.firebase.database.*

class IncomeList : AppCompatActivity() {
    private lateinit var incomeRecyclerView: RecyclerView
    private lateinit var tvloadingData: TextView
    private lateinit var incomeList: ArrayList<incomeModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income_list)
        incomeRecyclerView=findViewById(R.id.rvPlan)
        incomeRecyclerView.layoutManager= LinearLayoutManager(this)
        incomeRecyclerView.setHasFixedSize(true)
        tvloadingData = findViewById(R.id.tvLoadingData)

        incomeList= arrayListOf<incomeModel>()

        getPlanData()
    }
    private fun getPlanData(){
        incomeRecyclerView.visibility= View.GONE
        tvloadingData.visibility=View.VISIBLE

        val dbRef = FirebaseDatabase.getInstance().getReference("income")

        dbRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                incomeList.clear()
                if (snapshot.exists()){
                    var totalAmount = 0
                    for(incomeSnap in snapshot.children){
//                        val incomeData = incomeSnap.getValue(incomeModel::class.java)
//                        incomeList.add(incomeData!!)

//                        val amount = incomeData.incomeamt?.toInt()
//                        if (amount != null) {
//                            totalAmount += amount
//                        }
//                        val income  = if (incomeData.incomeamt !="null")incomeData.incomeamt?.toInt() else null
//                        if (income != null){
//                            totalAmount +=income
//                        }

                    }
                    val total = findViewById<TextView>(R.id.totalAmount)
                    total.text = totalAmount.toString()
                    var totalLab = findViewById<TextView>(R.id.totalLable)

                    Log.d("all",totalAmount.toString())

                    if(totalAmount != null){
                        totalLab.visibility = View.VISIBLE
                        total.visibility = View.VISIBLE
                    }


                    val iAdapter = incomeAdapter(incomeList)
                    incomeRecyclerView.adapter=iAdapter

                    iAdapter.setonItemClickListener(object : incomeAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //user directed to see more data
                            val intent = Intent(this@IncomeList, IncomeDetailsActivity::class.java)

                            //put extra view
                            intent.putExtra("planId",incomeList[position].incomeId)
                            intent.putExtra("planName",incomeList[position].incomeName)
                            intent.putExtra("income",incomeList[position].incomeAmount)
                            startActivity(intent)

                        }

                    })

                    incomeRecyclerView.visibility= View.VISIBLE
                    tvloadingData.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

            })
        }
}