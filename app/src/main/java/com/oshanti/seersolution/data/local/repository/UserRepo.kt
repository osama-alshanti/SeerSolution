package com.oshanti.seersolution.data.local.repository

import com.oshanti.seersolution.data.local.dao.UserDao
import com.oshanti.seersolution.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepo @Inject constructor(private val userDao: UserDao) {

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    fun getUserById(userId: Int) = userDao.getUserById(userId)
}