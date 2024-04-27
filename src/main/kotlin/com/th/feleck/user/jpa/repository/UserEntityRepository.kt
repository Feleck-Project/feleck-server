package com.th.feleck.user.jpa.repository

import com.th.feleck.user.jpa.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserEntityRepository: JpaRepository<UserEntity, Long> {
    fun findByUserName(name: String) : UserEntity?
}