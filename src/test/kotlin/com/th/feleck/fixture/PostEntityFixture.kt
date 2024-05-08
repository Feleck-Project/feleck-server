package com.th.feleck.fixture

import com.th.feleck.post.jpa.entity.PostEntity
import com.th.feleck.user.jpa.entity.UserEntity
import com.th.feleck.user.model.UserRole
import java.time.OffsetDateTime

object PostEntityFixture {
    fun get(userName: String, postId: Long, userId: Long): PostEntity {
        val userEntity = UserEntity(userName, "title", UserRole.USER, OffsetDateTime.now(), null, null)
        val result = PostEntity("title", "body", userEntity, OffsetDateTime.now(), null, null)
        result.id = postId;

        return result
    }
}