package com.liz.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.liz.chatapp.adapter.MessageAdapter
import com.liz.chatapp.databinding.ActivityChatBinding
import com.liz.chatapp.model.Message

class ChatActivity: AppCompatActivity() {
    lateinit var messageRecyclerView: RecyclerView
    lateinit var messageAdapter: MessageAdapter
    lateinit var messageBox: EditText
    lateinit var sendButton: Button
    lateinit var messageList: ArrayList<Message>
    lateinit var senderRoom: String
    lateinit var receiverRoom: String
    lateinit var DBRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receiveruid = intent.getStringExtra("uid")
        val senderuid = FirebaseAuth.getInstance().currentUser!!.uid

        senderRoom =receiveruid + senderuid
        receiverRoom = senderuid + receiveruid

        supportActionBar?.title = name

        messageRecyclerView = findViewById(R.id.RecyclerView)
        sendButton = findViewById(R.id.Send)
        messageBox = findViewById(R.id.messBox)
        messageList =ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        DBRef = FirebaseDatabase.getInstance().reference

        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter =messageAdapter
//logic for adding data to recyclerview
        DBRef.child("chats").child(senderRoom!!).child("messages").push()
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnap in snapshot.children) {
                        val message = postSnap.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
//adding message to dtabase

        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message,senderuid)

            DBRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    DBRef.child("chats").child(senderRoom!!).child("messages").push()
                        .setValue(messageObject)
                        }
            messageBox.setText("")

        }



    }



}