package com.example.wechat

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.wechat.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.values
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    private lateinit var mauth: FirebaseAuth;
    private lateinit var databaseref:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignUpBinding.inflate(inflater,container,false)
         mauth = FirebaseAuth.getInstance()



        binding.SignUpButton.setOnClickListener {
            var Useremail=binding.EmailNo.text.toString()
            var Username=binding.nameFiled.text.toString()
            var UserPassword=binding.PasswordNo.text.toString()
            signup(Username,Useremail,UserPassword)
        }

        return binding.root


    }

    private fun signup(name : String ,email: String, password: String) {

        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = mauth.currentUser
                    Toast.makeText(
                        requireContext(),
                        "Welcome, ${user?.email}!",
                        Toast.LENGTH_SHORT
                    ).show()
                    AddtoDatabase(name,email,mauth.currentUser?.uid)
                    findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)

                } else {
                    val errorMessage = task.exception?.message ?: "Sign-up failed"
                    Log.e("SignUpFragment", "Error: $errorMessage")
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()

                }
            }

    }

    private fun AddtoDatabase(name: String, email: String, uid: String?) {
        databaseref=FirebaseDatabase.getInstance().getReference()
        databaseref.child("user").child(uid!!).setValue(User(name,email,uid))


    }


}