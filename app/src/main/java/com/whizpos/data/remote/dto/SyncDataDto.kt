package com.whizpos.data.remote.dto

data class SyncDataDto(
    val users: List<UserDto>,
    val products: List<ProductDto>
)