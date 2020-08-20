package com.example.diy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.diy.databinding.FragmentRegisterBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        db = Firebase.firestore
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        binding.btnRegister.setOnClickListener {
            auth.createUserWithEmailAndPassword(binding.inputEmail.text.toString().trim(), binding.inputPassword.text.toString().trim())
                .addOnCompleteListener { it: Task<AuthResult> ->
                    if(!it.isSuccessful) return@addOnCompleteListener

                    Log.d("Authentication", "The user was create successfully")
                    val uid = FirebaseAuth.getInstance().uid ?: ""
                    val user = hashMapOf(
                        "username" to binding.inputUsername.text.toString().trim(),
                        "email" to binding.inputEmail.text.toString().trim()
                    )
                    db.collection("users")
                        .document(uid)
                        .set(user)
                        .addOnSuccessListener {
                            Log.d("Authentication", "The user was create successfully")
                            findNavController().navigate(R.id.action_registerFragment_to_listFragment)
                        }
                        .addOnFailureListener {
                            Log.d("Authentication", "Saving user to Database Failed: ${it.message}")
                        }

                }
                .addOnFailureListener {
                    Toast.makeText(context, "The registration failed ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        return binding.root
    }
}