package com.example.myjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myjob.activities.dashbordMain
//import com.example.myjob.databinding.ActivityAddGoalDetailsBinding
import com.example.myjob.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.notRegister.setOnClickListener{
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

        binding.signin.setOnClickListener {
            val email = binding.userName.text.toString()
            val pass = binding.Password.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty() ){
                    firebaseAuth.signInWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(this){
                            if(it.isSuccessful){

                                val  intent = Intent(this, dashbordMain::class.java)
                                startActivity(intent)

                            }else{
                                Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
            }else{
                Toast.makeText(this,"Empty Field Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null){
            val  intent = Intent(this, dashbordMain::class.java)
            startActivity(intent)
        }
    }
}