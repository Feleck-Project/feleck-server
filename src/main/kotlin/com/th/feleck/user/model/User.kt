package com.th.feleck.user.model

import com.th.feleck.user.jpa.entity.UserEntity
import java.time.OffsetDateTime

class User(
    val id: Long,
    val userName: String,
    val password: String,
    val role: UserRole,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val updateAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null
){
    companion object {
        fun fromEntity(entity: UserEntity)
        : User {
            return User(
                entity.id,
                entity.userName,
                entity.password,
                entity.role,
                entity.createdAt,
                entity.updatedAt,
                entity.deletedAt
            )
        }
    }
}