package com.example.textitalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class LatestMessegesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latestmessegesactivity)

       VerifyuserloggedIn()

    }

    private  fun VerifyuserloggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null){
            val intent = Intent(this , RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            // Above line clears the stack for smooth running and when we press back button it exit to
            // homepage of the phone insted of the previous activity
            startActivity(intent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId){
            R.id.menu_new_messege -> {
                val intent = Intent(this ,newmessegeActivity::class.java)
                startActivity(intent)

            }
            R.id.menu_sign_out  -> {
                FirebaseAuth.getInstance().signOut()      //signing out user
                val intent = Intent(this , RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                // Above line clears the stack for smooth running and when we press back button it exit to
                // homepage of the phone insted of the previous activity
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}