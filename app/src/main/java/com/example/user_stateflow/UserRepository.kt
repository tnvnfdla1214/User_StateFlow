package com.example.user_stateflow

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor (private val userDao: UserDao) {
    fun getUser() : Resource<List<User>> {
        return try {
            val response = userDao.getUser()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "에러가 발생했습니다.")
        }
    }

    @WorkerThread
    suspend fun insert(user:User) = withContext(Dispatchers.IO){
        userDao.insert(user)
    }
}