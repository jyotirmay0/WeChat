package com.example.wechat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class massegeAdapter(val context: Context,val massegeList:ArrayList<massege>):RecyclerView.Adapter<RecyclerView.ViewHolder> (){

    var itemSent=2
    var itemRecived=1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if(viewType==1)
       {
           val view = LayoutInflater.from(context).inflate(R.layout.recieve,parent,false)
           return massegeAdapter.reciverViewholder(view)
       }else {
           val view = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
           return massegeAdapter.sentViewholder(view)

       }
    }

    override fun getItemViewType(position: Int): Int {
        val currentItem=massegeList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentItem.senderid))
        {
            return itemSent
        }else
        {
            return itemRecived
        }
    }

    override fun getItemCount(): Int {
        return massegeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMassege=massegeList[position]
        if(holder.javaClass==sentViewholder::class.java)
        {
            val viewHolder=holder as sentViewholder

            holder.sentMassege.text=currentMassege.massege

        }else{
            val viewHolder=holder as reciverViewholder
            holder.recivedMassege.text=currentMassege.massege

        }
    }
    class sentViewholder(itemView:View):RecyclerView.ViewHolder(itemView){
        val sentMassege=itemView.findViewById<TextView>(R.id.sentText)

    }
    class reciverViewholder(itemView:View):RecyclerView.ViewHolder(itemView){
        val recivedMassege=itemView.findViewById<TextView>(R.id.recievedText)

    }
}