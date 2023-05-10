package com.example.myjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myjob.databinding.ActivityMainBinding
import com.example.myjob.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.alreadyRegister.setOnClickListener{
            val  intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.signup.setOnClickListener{
            val email = binding.userName.text.toString()
            val pass = binding.Password.text.toString()
            val confirmPass = binding.rePassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){

                    firebaseAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(this){
                            if(it.isSuccessful){

                                val  intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)

                            }else{
                                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                            }
                    }

                }else{
                    Toast.makeText(this,"password is not match",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Empty Field Are not Allowed !!",Toast.LENGTH_SHORT).show()
            }
        }


    }
}