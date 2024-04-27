package com.th.feleck.fixture

import com.th.feleck.user.jpa.entity.UserEntity

object UserEntityFixture {
    fun get(userName: String, password: String): UserEntity {
        val result = UserEntity(userName, password)
        result.id = 1;

        return result
    }
}