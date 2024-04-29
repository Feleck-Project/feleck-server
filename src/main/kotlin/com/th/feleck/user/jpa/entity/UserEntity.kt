package com.th.feleck.user.jpa.entity

import com.th.feleck.user.model.UserRole
import jakarta.persistence.*
import lombok.Builder
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.OffsetDateTime

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
class UserEntity(
    @Column val userName: String,
    @Column val password: String,
    @Column
    @Enumerated(EnumType.STRING)
    val role: UserRole,
    @Column var createdAt: OffsetDateTime,
    @Column var updatedAt: OffsetDateTime?,
    @Column var deletedAt: OffsetDateTime?
) {
    @PrePersist
    fun createdAt() {
        this.createdAt = OffsetDateTime.now()
    }

    @PreUpdate
    fun updatedAt() {
        this.updatedAt = OffsetDateTime.now()
    }

    @Id
    var id: Long = 0

    companion object {
        fun of(userName: String, password: String, role: UserRole = UserRole.USER): UserEntity {
            return UserEntity(
                userName = userName,
                password = password,
                role = role,
                createdAt = OffsetDateTime.now(),
                updatedAt = null,
                deletedAt = null
            )
        }
    }


    }
}