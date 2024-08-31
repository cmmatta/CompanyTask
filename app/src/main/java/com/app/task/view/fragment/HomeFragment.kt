package com.app.task.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.task.R
import com.app.task.databinding.FragmentHomeBinding
import com.app.task.view.adapter.UserAdapter
import com.app.task.viewmodel.UserViewModel

class HomeFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setupViewModel()
        setupRecyclerView()
        observeViewModel()

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addUserFragment)
        }

        return binding.root
    }

    private fun setupViewModel() {
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.viewModel = userViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter(requireContext(), viewModel = userViewModel)
        binding.userRecyclerView.adapter = userAdapter
        binding.userRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observeViewModel() {
        userViewModel.allUsers.observe(viewLifecycleOwner) { users ->
            users?.let {
                userAdapter.submitList(it)
                // Show or hide the "No records found" message based on list size
                if (it.isEmpty()) {
                    binding.noRecordTextView.visibility = View.VISIBLE
                    binding.userRecyclerView.visibility = View.GONE
                } else {
                    binding.noRecordTextView.visibility = View.GONE
                    binding.userRecyclerView.visibility = View.VISIBLE
                }
            }
        }

        userViewModel.showToast.observe(viewLifecycleOwner) { showToast ->
            if (showToast) {
                userViewModel.toastMessage.value?.let { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    userViewModel.onToastShown()
                }
            }
        }
    }
}


