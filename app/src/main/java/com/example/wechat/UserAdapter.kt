package com.example.wechat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.combineTransform

class UserAdapter(val context:Context,val  userList:ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textname = itemView.findViewById<TextView>(R.id.NameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentuser = userList[position]
        holder.textname.text = currentuser.name

        holder.itemView.setOnClickListener {

            val argbundle = Bundle()

            argbundle.putString("name",currentuser.name)
            argbundle.putString("uid",currentuser.uid)
            val fragment  = chatFragment()
            fragment.arguments=argbundle
            if (context is AppCompatActivity) {
                val activity = context as AppCompatActivity
                val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainerView, chatFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }


        }
    }
}