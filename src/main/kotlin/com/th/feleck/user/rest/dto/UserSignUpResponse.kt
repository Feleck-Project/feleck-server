package com.th.feleck.user.rest.dto

import com.th.feleck.user.model.User
import com.th.feleck.user.model.UserRole

class UserSignUpResponse(
    private val id: Long,
    private val userName: String,
    private val role: UserRole
) {

    companion object{
        fun  fromUser(user: User): UserSignUpResponse {
            return UserSignUpResponse(
                id = user.id,
                userName = user.userName,
                role = user.role
            )
        }
    }
}