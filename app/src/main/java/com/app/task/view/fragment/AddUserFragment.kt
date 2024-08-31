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
import com.app.task.R

class AddUserFragment : Fragment() {
    private lateinit var binding: FragmentAddUserBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        binding.saveButton.setOnClickListener {
//            val name = binding.etName.text.toString()
//            val email = binding.etEmail.text.toString()
//            val password = binding.etPassword.text.toString()
//
//            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
//                if (isValidEmail(email)) {
//                    viewModel.addUser(name, email, password)
//                    Toast.makeText(requireContext(), R.string.user_added, Toast.LENGTH_SHORT).show()
//                    findNavController().navigateUp()
//                } else {
//                    Toast.makeText(requireContext(), R.string.invalid_email, Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(requireContext(), R.string.fill_all, Toast.LENGTH_SHORT).show()
//            }
//        }

        return binding.root
    }

//    private fun isValidEmail(email: String): Boolean {
//        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
//    }
}
