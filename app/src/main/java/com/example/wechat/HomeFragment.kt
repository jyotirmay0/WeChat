package com.example.wechat

import android.app.ActionBar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.SupportActionModeWrapper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wechat.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var userRecyclerView:RecyclerView
    lateinit var userlist:ArrayList<User>
    lateinit var adapter: UserAdapter
    lateinit var mDbRef:DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        userlist= ArrayList()
        adapter=UserAdapter(requireContext(),userlist)
        userRecyclerView=binding.RecyclerviewName
        userRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        userRecyclerView.adapter=adapter
        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for(postsnapshot in snapshot.children)
                {
                    val currentuser=postsnapshot.getValue(User::class.java)
                    userlist.add(currentuser!!)
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })






                return binding.root

    }



}