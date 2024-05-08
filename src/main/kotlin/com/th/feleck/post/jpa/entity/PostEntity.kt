package com.th.feleck.post.jpa.entity

import com.th.feleck.user.jpa.entity.UserEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.OffsetDateTime

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
class PostEntity (
    var title: String,
    @Column(columnDefinition = "TEXT")
    var body: String,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    companion object {
        fun of(title: String, body: String, user: UserEntity): PostEntity {
            return PostEntity(
                title = title,
                body = body,
                user = user,
                createdAt = OffsetDateTime.now(),
                updatedAt = null,
                deletedAt = null
            )
        }
    }
}