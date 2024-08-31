package com.app.task.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginEvent = MutableLiveData<Boolean>()
    val loginEvent: LiveData<Boolean>
        get() = _loginEvent

    fun onLoginButtonClick() {
        if (isInputValid()) {
            val isValid = validateCredentials()
            _loginEvent.value = isValid
        } else {
            _loginEvent.value = false
        }
    }

    private fun isInputValid(): Boolean {
        val emailValue = email.value
        val passwordValue = password.value

        return !emailValue.isNullOrEmpty() && !passwordValue.isNullOrEmpty()
    }

    private fun validateCredentials(): Boolean {
        val emailValue = email.value
        val passwordValue = password.value

        // Dummy check for email and password
        return emailValue == "test@gmail.com" && passwordValue == "123456"
    }
}
