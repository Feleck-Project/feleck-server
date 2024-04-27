package com.th.feleck.user.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "users")
class UserEntity(
    @Column val userName: String,
    @Column val password: String,
    @Column val createdAt: OffsetDateTime = OffsetDateTime.now(),
    @Column val updateAt: OffsetDateTime? = null
){
    @Id
    var id: Long = 0
}