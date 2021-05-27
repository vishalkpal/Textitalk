package com.example.textitalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        register_button_register.setOnClickListener(){
           performRegister()

        }

        already_edittext_register.setOnClickListener {
            // login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        profile_pic_button_register.setOnClickListener {
            Log.d("Main","Clicked on select photo")
        }

    }
    private fun performRegister(){
        val email = email_textview_register.text.toString()
        val password = password_edittext_register.text.toString()

        Log.d("RegisterActivity","email is"+email)
        Log.d("RegisterActivity","Password is $password")

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enetr valid details ", Toast.LENGTH_LONG).show()
           return
        }
        // Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main","sucessful: ${it.result?.user?.uid}")

            }
            .addOnFailureListener {
                Log.d("Main","Failed to create: ${it.message}")
                Toast.makeText(this, "Please enetr valid details ", Toast.LENGTH_LONG).show()
            }
    }
}