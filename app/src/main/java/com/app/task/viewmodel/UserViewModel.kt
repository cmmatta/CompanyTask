package com.app.task.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.task.R
import com.app.task.database.User
import com.app.task.database.UserDatabase
import com.app.task.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val allUsers: LiveData<List<User>>
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage
    private val _navigationCommand = MutableLiveData<Boolean>()
    val navigationCommand: LiveData<Boolean> get() = _navigationCommand

    private val _showToast = MutableLiveData<Boolean>()
    val showToast: LiveData<Boolean> get() = _showToast


    private val userRepository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
        allUsers = userRepository.getAllUsers()
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            try {
                userRepository.deleteUser(user)
                _toastMessage.value = getApplication<Application>().getString(R.string.deleted)
                _showToast.value = true

            } catch (e: Exception) {
                _toastMessage.value = "Error deleting user: ${e.message}"
                _showToast.value = true

            }
        }
    }

    fun onToastShown() {
        _showToast.value = false
    }

    fun addUser(name: String, email: String, password: String) {
        val user = User(name = name, email = email, password = password)
        viewModelScope.launch {
            try {
                userRepository.insertUser(user)
                _toastMessage.postValue(getApplication<Application>().getString(R.string.user_added))
                _navigationCommand.postValue(true)
            } catch (e: Exception) {
                _toastMessage.postValue("Error adding user: ${e.message}")
            }
        }
    }

    fun onSaveButtonClick() {
        val nameValue = name.value.orEmpty()
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()

        when {
            nameValue.isEmpty() || emailValue.isEmpty() || passwordValue.isEmpty() -> {
                _toastMessage.postValue(getApplication<Application>().getString(R.string.fill_all))
            }
            !isValidEmail(emailValue) -> {
                _toastMessage.postValue(getApplication<Application>().getString(R.string.invalid_email))
            }
            else -> {
                addUser(nameValue, emailValue, passwordValue)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}
