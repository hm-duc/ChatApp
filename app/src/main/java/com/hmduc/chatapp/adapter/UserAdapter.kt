package com.hmduc.chatapp.adapter.UserAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hmduc.chatapp.R
import com.hmduc.chatapp.model.User
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val context: Context, private val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    class MyViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val txtUserName: TextView = v.findViewById(R.id.txtUserName)
        val txtTemp: TextView = v.findViewById(R.id.txtTemp)
        val imageUser: CircleImageView = v.findViewById(R.id.userImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.row_user,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = userList[position]
        holder.txtUserName.text = user.userName

        Glide.with(context).load(user.usereImage).placeholder(R.drawable.ic_launcher_background).into(holder.imageUser)

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}