package com.example.myjob

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.incomemad.adapters.incomeAdapter
import com.example.myjob.databinding.ActivityMainGoalBinding
import com.example.myjob.databinding.ActivityMainIncomeBinding
import com.example.myjob.databinding.ActivityMainPlanBinding
import com.example.myjob.model.incomeModel
import com.google.firebase.database.*

class activity_main_income : AppCompatActivity() {

    private lateinit var incomeRecyclerView: RecyclerView
    private lateinit var tviLoadingData: TextView
    private lateinit var incomeList: ArrayList<incomeModel>
    private lateinit var dbRef: DatabaseReference


    private lateinit var binding: ActivityMainIncomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.budgetAddBtn.setOnClickListener{
            val  intent = Intent(this,addNewIncome::class.java)
            startActivity(intent)
        }

        incomeRecyclerView=findViewById(R.id.rvincome)
        incomeRecyclerView.layoutManager= LinearLayoutManager(this)
        incomeRecyclerView.setHasFixedSize(true)
        tviLoadingData = findViewById(R.id.itvLoadingData)

        incomeList= arrayListOf<incomeModel>()

        getincomeData()

    }
    private fun getincomeData(){
        incomeRecyclerView.visibility= View.GONE
        tviLoadingData.visibility=View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("income")

        dbRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                incomeList.clear()
                if (snapshot.exists()){
                    var totalincome = 0

                    for(incomeSnap in snapshot.children){
                        val incomeData = incomeSnap.getValue(incomeModel::class.java)
                        incomeList.add(incomeData!!)

                        //val income = if (incomeData.incomeAmount != "null") incomeData.incomeAmount?.toInt() else null

                        val income = if (incomeData.incomeAmount != "null") incomeData.incomeAmount?.toInt() else null
                        if (income != null){
                            totalincome += income
                        }
                    }
                    val itotal= findViewById<TextView>(R.id.totalincome)
                    itotal.text=totalincome.toString()
                    var itotallab=findViewById<TextView>(R.id.itotallable)

                    Log.d("all",totalincome.toString())

                    if (totalincome != null){
                        itotallab.visibility=View.VISIBLE
                        itotal.visibility=View.VISIBLE
                    }

                    val iAdapter = incomeAdapter(incomeList)
                    incomeRecyclerView.adapter=iAdapter

                    iAdapter.setonItemClickListener(object :incomeAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //user directed to see more data
                            val intent = Intent(this@activity_main_income, IncomeDetailsActivity::class.java)

                            //put extra view
                            intent.putExtra("expId",incomeList[position].incomeId)
                            intent.putExtra("expName",incomeList[position].incomeName)
                            intent.putExtra("expAmount",incomeList[position].incomeAmount)
                            startActivity(intent)

                        }

                    })

                    incomeRecyclerView.visibility= View.VISIBLE
                    tviLoadingData.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

            })
        }
}
