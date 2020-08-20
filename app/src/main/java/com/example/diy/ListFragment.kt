package com.example.diy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.diy.data.ProjectPost
import com.example.diy.databinding.FragmentListBinding
import com.example.diy.databinding.FragmentRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.single_list_item.*


class ListFragment : Fragment() {
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        db = Firebase.firestore
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        var adapter = GroupAdapter<GroupieViewHolder>()

        binding.listView.adapter = adapter

        adapter.setOnItemClickListener { item, view ->
            val projectItem = item as ProjectItem
            findNavController().navigate(R.id.action_listFragment_to_detailsFragment)
        }

        db.collection("projects").get()
            .addOnSuccessListener {
                if (it != null){
                    for (project in it) {
                        var resultProjectItem = project.toObject<ProjectPost>()
                        resultProjectItem.id = project.id
                        adapter.add(ProjectItem(resultProjectItem))
                    }
                }

            }

        binding.floatingActionButton2.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_postFragment)
        }

        return binding.root
    }
}

class ProjectItem(val projectItem: ProjectPost) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.list_title.text = projectItem.title
        viewHolder.list_desc.text = projectItem.subHeading
        viewHolder.list_steps.text = projectItem.body
    }

    override fun getLayout(): Int = R.layout.fragment_list
}