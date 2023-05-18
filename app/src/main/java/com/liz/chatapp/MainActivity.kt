package com.liz.chatapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.FirebaseDatabase.*
import com.google.firebase.database.ValueEventListener
import com.liz.chatapp.adapter.UserAdapter
import com.liz.chatapp.databinding.ActivityMainBinding
import com.liz.chatapp.model.User
import com.liz.chatapp.utils.toast

class MainActivity: AppCompatActivity() {

    lateinit var userRecyclerView: RecyclerView
    lateinit var userAdapter: UserAdapter
    lateinit var userList: ArrayList<User>
    lateinit var auth: FirebaseAuth
    lateinit var DBRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        DBRef = FirebaseDatabase.getInstance().reference
        userList = ArrayList()
        userAdapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.recyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = userAdapter

        DBRef.child("user").addValueEventListener(@SuppressLint("NotifyDataSetChanged")
        object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (post in snapshot.children) {
                    val user = post.getValue(User::class.java)

                    if (auth.currentUser?.uid != user?.uid) {
                        userList.add(user!!)
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            auth.signOut()
            finish()
            toast("Logged out")
            return true
        }
        return true
    }
}