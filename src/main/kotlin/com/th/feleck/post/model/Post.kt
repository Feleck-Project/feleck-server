package com.th.feleck.post.model


import com.th.feleck.post.jpa.entity.PostEntity
import com.th.feleck.user.jpa.entity.UserEntity
import com.th.feleck.user.model.User
import jakarta.persistence.*
import java.time.OffsetDateTime

class Post (
    var id: Long,
    var title: String,
    var body: String,
    val user: User,
    var createdAt: OffsetDateTime,
    var updatedAt: OffsetDateTime?,
    var deletedAt: OffsetDateTime?
) {



    companion object {
        fun fromEntity(entity : PostEntity): Post {
            return Post(
                id = entity.id,
                title = entity.title,
                body = entity.body,
                user = entity.user,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                deletedAt = entity.deletedAt
            )
        }
    }
}