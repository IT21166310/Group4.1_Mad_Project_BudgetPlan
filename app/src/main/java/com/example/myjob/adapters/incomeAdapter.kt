package com.example.incomemad.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myjob.R
import com.example.myjob.model.incomeModel


class incomeAdapter(private val incomeList: ArrayList<incomeModel>) :
    RecyclerView.Adapter<incomeAdapter.ViewHolder>() {


    private lateinit var iListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setonItemClickListener(clickListener: onItemClickListener){
        iListener=clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): incomeAdapter.ViewHolder {
        val itemView= LayoutInflater.from(parent.context)
            .inflate(R.layout.income_list_item, parent,false)
        return ViewHolder(itemView,iListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentExp = incomeList[position]
        holder.tvinName.text=currentExp.incomeName
    }

    override fun getItemCount(): Int {
        return incomeList.size
    }

    class ViewHolder (itemView : View, clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView){

        val tvinName: TextView =itemView.findViewById(R.id.tvinName)

        init{
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
            }
    }
}