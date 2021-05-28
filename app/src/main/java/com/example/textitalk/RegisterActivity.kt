package com.example.textitalk

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

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
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

    }

    // function of image slection
    var SelectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null ) {
            Log.d("image", "selcted a image")

            SelectedPhotoUri = data.data                                                 // getting the location of the image
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,SelectedPhotoUri )
            val bitmapDrawable = BitmapDrawable(bitmap)
            profile_pic_button_register.setBackgroundDrawable(bitmapDrawable)

        }
    }

    // function to handle the email password authentication for registration process
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

                Uploadselectedphoto()

            }
            .addOnFailureListener {
                Log.d("Main","Failed to create: ${it.message}")
                Toast.makeText(this, "Please enetr valid details ", Toast.LENGTH_LONG).show()
            }
    }
     // uploading the selected photo to user inside /images/ section
    private fun Uploadselectedphoto(){
        if(SelectedPhotoUri == null) return             // check if uri not null
           val filename = UUID.randomUUID().toString()  // a random long string

        val Ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        Ref.putFile(SelectedPhotoUri!!)                        // !! because of mismathc names
            .addOnSuccessListener {
                Log.d("Main","sucessfully uploaded image : ${it.metadata?.path}")

                // downloading the image we saved
                Ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("mainimage","downloaded image url : $it")
                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener{
                Log.d("main","Failed image uri")
            }
    }
    private fun saveUserToFirebaseDatabase(ProfileImageUrl:String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val txt: EditText = findViewById(R.id.usename_textview_register)
        val user = User(uid ,txt.text.toString(),ProfileImageUrl)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("main","User saved to firebase")
            }
    }

}
class User(val uid:String,val username:String, val ProfileimageUrl:String)