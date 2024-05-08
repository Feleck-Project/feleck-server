package com.th.feleck.user.model

import com.th.feleck.user.jpa.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.OffsetDateTime

class User(
    var id: Long = 0,
    private val userName: String,
    private val password: String,
    val role: UserRole = UserRole.USER,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val updateAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null
): UserDetails{
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

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(this.role.toString()))
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.username
    }

    override fun isAccountNonExpired(): Boolean {
        return this.deletedAt == null;
    }

    override fun isAccountNonLocked(): Boolean {
        return this.deletedAt == null;
    }

    override fun isCredentialsNonExpired(): Boolean {
        return this.deletedAt == null;
    }

    override fun isEnabled(): Boolean {
        return this.deletedAt == null;
    }
}