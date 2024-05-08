package com.th.feleck.post.jpa.repository

import com.th.feleck.post.jpa.entity.PostEntity
import com.th.feleck.user.jpa.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostEntityRepository: JpaRepository<PostEntity, Long> {
    override fun findById(id: Long): Optional<PostEntity>

    fun findAllByUser(userEntity: UserEntity, pageable: Pageable) : Page<PostEntity>
}