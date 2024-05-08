package com.th.feleck.user.service

import com.th.feleck.common.exception.CustomException
import com.th.feleck.common.util.JwtTokenUtils
import com.th.feleck.user.jpa.entity.UserEntity
import com.th.feleck.user.jpa.repository.UserEntityRepository
import com.th.feleck.user.model.User
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userEntityRepository: UserEntityRepository,
    private val encoder: BCryptPasswordEncoder,
    @Value("\${jwt.token.expired-time-ms}")
    private var expiredTimeMs: Long,
    @Value("\${jwt.secret-key}")
    private var secretKey: String
) {

    fun loadUserByUsername(userName: String): UserEntity {
        return userEntityRepository.findByUserName(userName) ?: throw CustomException("user not found")
    }

    @Transactional
    fun singUp(userName: String, password: String): User {

        val user = userEntityRepository.findByUserName(userName)
        if (user != null)
            throw CustomException("user found with name: $userName")

        val userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)))

        return User.fromEntity(userEntity)
    }

    fun login(userName: String, password: String): String{
        val userEntity = userEntityRepository.findByUserName(userName) ?:throw CustomException("user not found with name: $userName")
        if (!encoder.matches(password, userEntity.password))
            throw CustomException("wrong password")

        //토큰 생성
        val token = JwtTokenUtils.generateToken(userName, secretKey, expiredTimeMs)
        return token
    }
}