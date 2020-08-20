package com.example.diy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.diy.data.ProjectPost
import com.example.diy.databinding.FragmentDetailsBinding
import com.example.diy.databinding.FragmentListBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class DetailsFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        db = Firebase.firestore

        db.collection("blogs").document("test").get()
            .addOnSuccessListener {
                val item = it.toObject<ProjectPost>()
                if(item == null) return@addOnSuccessListener
                binding.detailTitle.text = item.title
                binding.detailDesc.text = item.subHeading
                binding.detailSteps.text = item.body

            }
            .addOnFailureListener {
                Toast.makeText(context, "Sorry could not get blog", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_detailsFragment_to_listFragment)
            }

        return binding.root
    }
}