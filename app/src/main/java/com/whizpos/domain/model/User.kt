package com.whizpos.domain.model

data class User(
    val id: Int,
    val name: String,
    val pin: String,
    val role: String, // "Admin" or "Cashier"
    val avatarUrl: String?
)