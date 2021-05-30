package com.example.textitalk

import android.content.Intent
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
            performLogin()  // perform login fun
        }
        Back_edittext_login.setOnClickListener {
            finish()
        }
    }
    private  fun performLogin(){
        val email = email_edittext_login.text.toString()
        val password = password_edittext_login.text.toString()

        Log.d("LoginActivity","email is" +email)
        Log.d("LoginActivity","Passwoed is $password")

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill out email/pw.", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener

                Log.d("LoginActivity","singin successfull: ${it.result?.user?.uid}")
                val intent = Intent(this,LatestMessegesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                // Above line clears the stack for smooth running and when we press back button it exit to
                // homepage of the phone insted of the previous activity
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("Main","Failed to create:  ")
                Toast.makeText(this, "Please enetr valid details ", Toast.LENGTH_LONG).show()
            }
    }

}