package com.whizpos.data.repository

import com.whizpos.data.local.database.UserDao
import com.whizpos.data.managers.SettingsManager
import com.whizpos.data.remote.WhizPosApiService
import com.whizpos.domain.model.User
import com.whizpos.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: WhizPosApiService,
    private val userDao: UserDao,
    private val settingsManager: SettingsManager
) : UserRepository {

    override fun getUsers(): Flow<List<User>> {
        return userDao.getUsers().map { entities ->
            entities.map { it.toUser() }
        }
    }

    override suspend fun syncUsers() {
        val apiKey = settingsManager.connectionDetails.first().apiKey ?: return
        val remoteUsers = apiService.getSyncData(apiKey).users
        userDao.clearUsers()
        userDao.insertUsers(remoteUsers.map { it.toUserEntity() })
    }
}

private fun com.whizpos.data.local.entity.UserEntity.toUser(): User {
    return User(id, name, pin, role, avatarUrl)
}