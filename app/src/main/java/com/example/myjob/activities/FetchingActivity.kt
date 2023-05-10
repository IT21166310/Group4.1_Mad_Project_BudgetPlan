//package com.example.myjob.activities
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.widget.TextView
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.myjob.R
//import com.example.myjob.adapters.goalAdapter
//import com.example.myjob.model.GoalDetails
//import com.google.firebase.database.*
//
//class FetchingActivity : AppCompatActivity() {
//
//    private lateinit var goalRecyclerView: RecyclerView
//    private lateinit var tvLoadingData:TextView
//    private lateinit var dataList: ArrayList<GoalDetails>
//    private lateinit var dbRef:DatabaseReference
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_fetching_goal)
//
//        goalRecyclerView = findViewById(R.id.goalRec)
//        goalRecyclerView.layoutManager = LinearLayoutManager(this)
//        goalRecyclerView.setHasFixedSize(true)
//        tvLoadingData = findViewById(R.id.tvLoadingData)
//
//        dataList = arrayListOf<GoalDetails>()
//
//        getGoalData()
//
//    }
//    private fun getGoalData(){
//        goalRecyclerView.visibility = View.GONE
//        tvLoadingData.visibility = View.VISIBLE
//
//        dbRef = FirebaseDatabase.getInstance().getReference("GoalDetails")
//        dbRef.addValueEventListener(object : ValueEventListener{
//
////            override fun onDataChange(snapshot: DataSnapshot) {
////                dataList.clear()
////                if (snapshot.exists()){
////                    for (goalSnap in snapshot.children){
////                        val goalData  = goalSnap.getValue(GoalDetails::class.java)
////                        dataList.add(goalData!!)
////                    }
////                    val mAdapter = goalAdapter()
////                    goalRecyclerView.adapter = mAdapter
////
////                    goalRecyclerView.visibility = View.VISIBLE
////                    tvLoadingData.visibility = View.GONE
////                }
////
////            }
//
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
//}