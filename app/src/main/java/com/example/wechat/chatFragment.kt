package com.example.wechat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wechat.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class chatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var chatrecyclerview:RecyclerView
    private lateinit var massegeAdapter: massegeAdapter
    private lateinit var massegeList:ArrayList<massege>
    var senderroom:String?=null
    var reciverroom:String?=null
    lateinit var mdbref:DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentChatBinding.inflate(layoutInflater,container,false)

        var massegebox=binding.massegeEditTxt
        var sendbutton=binding.sentButton
        chatrecyclerview=binding.recyclerViewMasseges
        massegeList=ArrayList()
        massegeAdapter= massegeAdapter(requireActivity(),massegeList)
        chatrecyclerview.layoutManager=LinearLayoutManager(requireContext())
        chatrecyclerview.adapter=massegeAdapter

        val Name = arguments?.getString("name")

        val senderuid = arguments?.getString("uid")
        val reciveruid=FirebaseAuth.getInstance().currentUser?.uid
        mdbref= FirebaseDatabase.getInstance().getReference()
        senderroom=senderuid+reciveruid
        reciverroom=reciveruid+senderuid





        mdbref.child("chats").child(senderroom!!).child("masseges")
            .addValueEventListener(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {

                    massegeList.clear()

                    for(postsnapshot in snapshot.children)
                    {
                        val messege=postsnapshot.getValue(massege::class.java)
                        massegeList.add(messege!!)
                    }
                    massegeAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        sendbutton.setOnClickListener(){

            val massege=massegebox.text.toString()
            val MassegeObj=massege(massege, senderuid.toString())

            mdbref.child("chats").child(senderroom!!).child("masseges").push()
                .setValue(MassegeObj).addOnSuccessListener{
                    mdbref.child("chats").child(reciverroom!!).child("masseges").push()
                        .setValue(MassegeObj)
                    massegebox.setText(" ")

                }
        }

        return binding.root
    }


}