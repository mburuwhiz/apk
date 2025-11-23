package com.whizpos.domain.repository

import com.whizpos.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    suspend fun syncUsers()
}