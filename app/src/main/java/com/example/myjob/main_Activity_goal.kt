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
import com.example.myjob.adapters.goalAdapter
import com.example.myjob.databinding.ActivityMainGoalBinding
import com.example.myjob.model.GoalModel
import com.example.myjob.view.dilshan.AddNewGoal
import com.google.firebase.database.*


class   main_Activity_goal : AppCompatActivity() {

    private lateinit var goalRecyclerView: RecyclerView
    private lateinit var gloadingData: TextView
    private lateinit var goalList: ArrayList<GoalModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var binding: ActivityMainGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goalAddBtn.setOnClickListener{
            val  intent = Intent(this,AddNewGoal::class.java)
            startActivity(intent)

        }

        goalRecyclerView=findViewById(R.id.rvgoal)
        goalRecyclerView.layoutManager=LinearLayoutManager(this)
        goalRecyclerView.setHasFixedSize(true)
        gloadingData = findViewById(R.id.tvLoadingData)

        goalList= arrayListOf<GoalModel>()

        getPlanData()
    }
    private fun getPlanData(){
        goalRecyclerView.visibility=View.GONE
        gloadingData.visibility=View.VISIBLE

        val dbRef = FirebaseDatabase.getInstance().getReference("GoalDetails")

        dbRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                goalList.clear()
                if (snapshot.exists()){
                    for(goalSnap in snapshot.children){
                        val goalData = goalSnap.getValue(GoalModel::class.java)
                        goalList.add(goalData!!)

                    }
                    val gAdapter = goalAdapter(goalList)
                    goalRecyclerView.adapter=gAdapter

                    gAdapter.setonItemClickListener(object :goalAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //user directed to see more data
                            val intent = Intent(this@main_Activity_goal,goalDetailsActivity::class.java)

                            //put extra view
                            intent.putExtra("goalId",goalList[position].goalDetailsId)
                            intent.putExtra("goalName",goalList[position].goalName)
                            intent.putExtra("targetamount",goalList[position].targetAmount)
                            intent.putExtra("goaldate",goalList[position].date)
                            intent.putExtra("insertamount",goalList[position].amount)
                            startActivity(intent)

                        }

                    })
                    goalRecyclerView.visibility= View.VISIBLE
                    gloadingData.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        }


}
