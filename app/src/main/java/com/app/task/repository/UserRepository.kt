package com.app.task.repository

import androidx.lifecycle.LiveData
import com.app.task.database.User
import com.app.task.database.UserDao


class UserRepository(private val userDao: UserDao) {

    fun getAllUsers(): LiveData<List<User>> {
        return userDao.getAllUsers()
    }

    // Function to insert a user
    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }
}
