package com.th.feleck.fixture

import com.th.feleck.user.jpa.entity.UserEntity
import com.th.feleck.user.model.UserRole
import java.time.OffsetDateTime

object UserEntityFixture {
    fun get(userName: String, password: String, userId: Long): UserEntity {
        val result = UserEntity(userName, password, UserRole.USER, OffsetDateTime.now(), null, null)
        result.id = 1;

        return result
    }
}