package com.hmduc.chatapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.hmduc.chatapp.R
import com.hmduc.chatapp.adapter.UserAdapter.UserAdapter
import com.hmduc.chatapp.model.User
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    var userList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        listUserRecycleView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
    }

    private fun getUserList() {
        val firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for (data: DataSnapshot in snapshot.children) {
                    val user = data.getValue(User::class.java)

                    user?.let {
                        if (it.userId == firebaseUser.uid) {
                            userList.add(it)
                        }
                    }
                }

                val userAdapter = UserAdapter(this@UserActivity, userList)
                listUserRecycleView.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}