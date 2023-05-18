package com.liz.chatapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.liz.chatapp.ChatActivity
import com.liz.chatapp.R
import com.liz.chatapp.model.User

class UserAdapter(
    private val context: Context,
    private val userList: ArrayList<User>
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.textName.text = user.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name",user.name)
            intent.putExtra("uid",user.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.findViewById(R.id.txtName)
    }
}