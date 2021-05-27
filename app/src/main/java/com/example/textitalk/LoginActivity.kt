package com.example.textitalk

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            Log.d("LoginActivity","email is" +email)
            Log.d("LoginActivity","Passwoed is $password")

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener

                    Log.d("LoginActivity","singin successfull: ${it.result?.user?.uid}")
                }
                .addOnCanceledListener {
                    Log.d("Main","Failed to create:  ")
                    Toast.makeText(this, "Please enetr valid details ", Toast.LENGTH_LONG).show()
                }
        }
        Back_edittext_login.setOnClickListener {
            finish()
        }
    }

}