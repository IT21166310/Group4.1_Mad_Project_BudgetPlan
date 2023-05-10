package com.example.myjob

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class mainActivity_Expenses : AppCompatActivity() {
    private lateinit var ebtnInsertData: Button
    private lateinit var ebtnFetchData: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_expenses)


        ebtnInsertData = findViewById(R.id.ebtnInsertData)
        ebtnFetchData = findViewById(R.id.ebtnFetchData)

        ebtnInsertData.setOnClickListener {
            val intent = Intent(this, addNewExpenses::class.java)
            startActivity(intent)
        }

        ebtnFetchData.setOnClickListener {
            val intent = Intent(this, ExpensesList::class.java)
            startActivity(intent)
            }

       }

   }
