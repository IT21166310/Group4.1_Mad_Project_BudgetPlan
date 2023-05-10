package com.example.myjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myjob.R
import com.example.myjob.adapters.PlanAdapter
import com.example.myjob.databinding.ActivityMainBinding
import com.example.myjob.databinding.ActivityMainPlanBinding
import com.example.myjob.databinding.ActivityRegisterBinding
import com.example.myjob.model.PlanModel
import com.google.firebase.database.*

class mainActivity_plan : AppCompatActivity() {

    private lateinit var planRecyclerView: RecyclerView
    private lateinit var tvloadingData: TextView
    private lateinit var planList: ArrayList<PlanModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var binding: ActivityMainPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.budgetAddBtn.setOnClickListener{
            val  intent = Intent(this,addNewPlan::class.java)
            startActivity(intent)

        }
        planRecyclerView = findViewById(R.id.rvPlan)
        planRecyclerView.layoutManager = LinearLayoutManager(this)
        planRecyclerView.setHasFixedSize(true)
        tvloadingData = findViewById(R.id.tvLoadingData)

        planList = arrayListOf<PlanModel>()

        getPlanData()

    }


    private fun getPlanData() {
        planRecyclerView.visibility = View.GONE
        tvloadingData.visibility = View.VISIBLE

        val dbRef = FirebaseDatabase.getInstance().getReference("BudgetPlan")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                planList.clear()
                if (snapshot.exists()) {
                    for (planSnap in snapshot.children) {
                        val planData = planSnap.getValue(PlanModel::class.java)
                        planList.add(planData!!)
                    }
                    val pAdapter = PlanAdapter(planList)
                    planRecyclerView.adapter = pAdapter

                    pAdapter.setonItemClickListener(object : PlanAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            //user directed to see more data
                            val intent =
                                Intent(this@mainActivity_plan, planDetailsActivity::class.java)

                            //put extra view
                            intent.putExtra("planId", planList[position].planId)
                            intent.putExtra("planName", planList[position].planName)
                            intent.putExtra("income", planList[position].income)
                            startActivity(intent)

                        }

                    })

                    planRecyclerView.visibility = View.VISIBLE
                    tvloadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}