package com.example.myjob.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myjob.*
import com.example.myjob.databinding.ActivityDashbordMainBinding

class dashbordMain : AppCompatActivity() {

    private lateinit var binding: ActivityDashbordMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashbordMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goalInterface.setOnClickListener{
            val intent = Intent(this, main_Activity_goal::class.java)
            startActivity(intent)
        }

        binding.BudgetInterface.setOnClickListener{
            val intent = Intent(this, mainActivity_plan::class.java)
            startActivity(intent)
        }

        binding.expensesInterface.setOnClickListener {
            val intent = Intent(this,mainActivity_Expenses::class.java)
            startActivity(intent)
        }

        binding.IncomeInterface.setOnClickListener {
            val intent = Intent(this,activity_main_income::class.java)
            startActivity(intent)
        }












    }
}