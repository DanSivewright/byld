package com.example.diy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.AutoScrollHelper
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.diy.databinding.FragmentListBinding
import com.example.diy.databinding.FragmentPostBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentPostBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        db = Firebase.firestore
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)

        binding.btnPost.setOnClickListener {
            val project = hashMapOf(
                "userId" to auth.currentUser?.uid,
                "heading" to binding.inputTitle.toString().trim(),
                "subHeading" to binding.inputSubheading.toString().trim(),
                "body" to binding.inputBody.toString().trim(),
                "createdAt" to Timestamp.now()
            )

            db.collection("projects")
                .add(project)
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to post blog", Toast.LENGTH_SHORT).show()
                }
                .addOnSuccessListener {
                    Log.d("WriteBlogFragment", "The blog was posted successfully ${it.id}")
                    Toast.makeText(context, "You blog has been posted", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_postFragment_to_listFragment)
                }
        }

        return binding.root
    }
}