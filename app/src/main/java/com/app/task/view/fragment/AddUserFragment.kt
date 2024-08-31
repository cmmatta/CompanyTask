package com.app.task.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.task.databinding.FragmentAddUserBinding
import com.app.task.viewmodel.UserViewModel
import android.util.Patterns
import androidx.lifecycle.Observer
import com.app.task.R

class AddUserFragment : Fragment() {
    private lateinit var binding: FragmentAddUserBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        setupViewModel()
        setupObservableModel()
        return binding.root
    }

    private fun setupObservableModel() {
        viewModel.toastMessage.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })
        viewModel.navigationCommand.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            if (shouldNavigate) {
                findNavController().popBackStack()
            }
        })    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner    }


}
