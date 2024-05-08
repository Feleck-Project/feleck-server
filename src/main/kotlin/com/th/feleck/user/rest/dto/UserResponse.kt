package com.th.feleck.user.rest.dto

import com.th.feleck.user.model.User
import com.th.feleck.user.model.UserRole

data class UserResponse(
    val id: Long,
    val userName: String,
    val role: UserRole
) {

    companion object{
        fun fromUser(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                userName = user.username,
                role = user.role
            )
        }
    }
}