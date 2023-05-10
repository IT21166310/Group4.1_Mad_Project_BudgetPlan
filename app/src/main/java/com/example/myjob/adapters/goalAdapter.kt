package com.example.myjob.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myjob.R
import com.example.myjob.model.GoalModel

class goalAdapter (private val goalList: ArrayList<GoalModel>):
    RecyclerView.Adapter<goalAdapter.ViewHolder>(){

    private lateinit var gListener: onItemClickListener


    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setonItemClickListener(clickListener: onItemClickListener){
        gListener=clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): goalAdapter.ViewHolder {
        val itemView=LayoutInflater.from(parent.context)
            .inflate(R.layout.goal_list_item, parent,false)
        return ViewHolder(itemView,gListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGoal = goalList[position]
        holder.tvgoalName.text=currentGoal.goalName




    }

    override fun getItemCount(): Int {
        return goalList.size
    }

    class ViewHolder (itemView : View, clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView){

        val tvgoalName:TextView=itemView.findViewById(R.id.tvgoalName)



        init{
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}