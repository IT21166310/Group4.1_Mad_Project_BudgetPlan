package com.example.myjob.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myjob.R
import com.example.myjob.model.ExpensesModel

class ExpenseAdapter (private val expenseList:ArrayList<ExpensesModel>):
    RecyclerView.Adapter<ExpenseAdapter.ViewHolder>(){

    private lateinit var eListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setonItemClickListener(clickListener: onItemClickListener){
        eListener=clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseAdapter.ViewHolder {
        val itemView= LayoutInflater.from(parent.context)
            .inflate(R.layout.expense_list_item, parent,false)
        return ViewHolder(itemView,eListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentExp = expenseList[position]
        holder.tvExpName.text=currentExp.expName
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    class ViewHolder (itemView : View, clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView) {

        val tvExpName: TextView = itemView.findViewById(R.id.tvexpName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}