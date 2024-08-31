package com.app.task.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.task.R
import com.app.task.databinding.FragmentHomeBinding
import com.app.task.view.adapter.UserAdapter
import com.app.task.viewmodel.UserViewModel


class HomeFragment : Fragment() {
    private lateinit var userviewModel: UserViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var userAdapter: UserAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        userviewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.viewModel = userviewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.userRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        userAdapter= UserAdapter(requireContext())
        binding.userRecyclerView.adapter = userAdapter

        // Observe the LiveData
        userviewModel.allUsers.observe(viewLifecycleOwner, Observer { users ->
            users?.let {
                userAdapter.submitList(it)
            }
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addUserFragment)
        }

        return binding.root
    }

}


