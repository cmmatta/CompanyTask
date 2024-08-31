package com.app.task.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.task.database.User
import com.app.task.database.UserDatabase

import com.app.task.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val allUsers: LiveData<List<User>>
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage
    private val _navigationCommand = MutableLiveData<Boolean>()
    val navigationCommand: LiveData<Boolean> get() = _navigationCommand






    private val userRepository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
        allUsers = userRepository.getAllUsers()
//        isUserListEmpty = Transformations.map(allUsers) { userList ->
//            userList.isEmpty()
//        }


    }

    fun addUser(name: String, email: String, password: String) {
        val user = User(name = name, email = email, password = password)
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }


    fun onSaveButtonClick() {
        val nameValue = name.value.orEmpty()
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()

        when {
            nameValue.isEmpty() || emailValue.isEmpty() || passwordValue.isEmpty() -> {
                _toastMessage.value = "Please fill all fields"
            }
            !isValidEmail(emailValue) -> {
                _toastMessage.value = "Invalid email format"
            }
            else -> {
                addUser(nameValue, emailValue, passwordValue)
                _toastMessage.value = "User added successfully"
                _navigationCommand.value = true
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }



}


