package com.th.feleck.post.rest.dto

import com.th.feleck.post.model.Post
import com.th.feleck.user.rest.dto.UserResponse
import java.time.OffsetDateTime

data class PostResponse (
    val id: Long,
    val title: String,
    val body: String,
    val user: UserResponse,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime?,
    val deletedAt: OffsetDateTime?

) {
    companion object {
        fun fromPost(post: Post): PostResponse {
            return PostResponse(
                id = post.id,
                title = post.title,
                body = post.body,
                user = UserResponse.fromUser(post.user),
                createdAt = post.createdAt,
                updatedAt = post.updatedAt,
                deletedAt = post.deletedAt
            )

        }
    }
}