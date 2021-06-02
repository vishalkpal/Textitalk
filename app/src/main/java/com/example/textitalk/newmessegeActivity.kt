package com.example.textitalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_newmessege.*
import kotlinx.android.synthetic.main.user_row_newmessege.view.*

class newmessegeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newmessege)

        supportActionBar?.title ="Select User"

//        val adapter = GroupAdapter<ViewHolder>()
//
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//        recyclerview_newmesseges.adapter =adapter

        fetchUser()
    }
    private fun fetchUser(){
        val ref = FirebaseDatabase.getInstance().getReference("/users/")
        // using singlevalueevent bcz () and {} type constantly get called when changing data
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
            val adapter = GroupAdapter<ViewHolder>()

            snapshot.children.forEach{
                Log.d("newmessege",it.toString())
                val user = it.getValue(User::class.java)
                   if(user != null) {
                       adapter.add(UserItem(user))
                   }
            }
                recyclerview_newmesseges.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                //
            }
        })
    }
}

class UserItem(val user: User): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        // will be called in our list
        viewHolder.itemView.username_textview_newmessege.text = user.username
    }

    override fun getLayout(): Int {
        return R.layout.user_row_newmessege
    }
}