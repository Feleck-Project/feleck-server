package com.th.feleck.user.service

import com.th.feleck.common.exception.CustomException
import com.th.feleck.user.jpa.entity.UserEntity
import com.th.feleck.user.jpa.repository.UserEntityRepository
import com.th.feleck.user.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userEntityRepository: UserEntityRepository
) {
    @Throws(CustomException::class)
    fun singUp(userName: String, password: String): User {

        //회원가입하려는 userName으로 회원가입된 user가 있는지
        val user = userEntityRepository.findByUserName(userName)
        if (user != null)
            throw CustomException("user not found with name: $userName")

        //회원가입 진행 = user 등
        val userEntity = userEntityRepository.save(UserEntity.of(userName, password))

        return User.fromEntity(userEntity)
    }

    fun login(userName: String, password: String): String{
        //회원가입 여부 체크
        val userEntity = userEntityRepository.findByUserName(userName) ?:throw CustomException("user not found with name: $userName")

        //비밀번호 체크
        if (!userEntity.password.equals(password))
            throw CustomException("wrong password")


        //토큰 생성
        return "test_token"
    }
}