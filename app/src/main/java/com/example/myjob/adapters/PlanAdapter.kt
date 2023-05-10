package com.example.myjob.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myjob.R
import com.example.myjob.model.PlanModel

class PlanAdapter (private val planList:ArrayList<PlanModel>):
    RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    private lateinit var pListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setonItemClickListener(clickListener: onItemClickListener) {
        pListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.plan_list_item, parent, false)
        return ViewHolder(itemView, pListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPlan = planList[position]
        holder.tvPlanName.text = currentPlan.planName

    }

    override fun getItemCount(): Int {
        return planList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        val tvPlanName: TextView = itemView.findViewById(R.id.tvPlanName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }
}