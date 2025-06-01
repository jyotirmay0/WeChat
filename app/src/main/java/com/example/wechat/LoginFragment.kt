package com.example.wechat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.wechat.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var mauth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(layoutInflater,container,false)

        mauth= FirebaseAuth.getInstance()

        binding.LoginButton.setOnClickListener {
            var email=binding.EmailNo.text.toString()
            var password=binding.PasswordNo.text.toString()
            login(email,password)
        }
        binding.SignUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }


        return binding.root


    }
    public override fun onStart() {
        super.onStart()
        mauth= FirebaseAuth.getInstance()
        val currentUser = mauth.currentUser
        if (currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun login(email: String, password: String) {
        mauth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = mauth.currentUser
                    Toast.makeText(
                        requireContext(),
                        "Welcome, ${user?.email}!",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                } else {
                    val errorMessage = task.exception?.message ?: "Sign-up failed"
                    Log.e("SignUpFragment", "Error: $errorMessage")
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
            }

    }


}