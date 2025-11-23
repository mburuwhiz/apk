package com.whizpos.data.remote.dto

import com.whizpos.data.local.entity.UserEntity

data class UserDto(
    val id: Int,
    val name: String,
    val pin: String,
    val role: String, // "Admin" or "Cashier"
    val avatarUrl: String?
) {
    fun toUserEntity(): UserEntity {
        return UserEntity(
            id = id,
            name = name,
            pin = pin,
            role = role,
            avatarUrl = avatarUrl
        )
    }
}