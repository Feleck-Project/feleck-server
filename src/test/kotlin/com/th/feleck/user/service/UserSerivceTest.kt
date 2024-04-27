package com.th.feleck.user.service

import com.th.feleck.common.exception.CustomException
import com.th.feleck.fixture.UserEntityFixture
import com.th.feleck.user.jpa.repository.UserEntityRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class UserSerivceTest
{
    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userEntityRepository: UserEntityRepository

    @Test
    fun 회원가입이_정상적으로_동작하는_경우(){

        val userName = "userName"
        val password = "password"

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(null)
        `when`(userEntityRepository.save(any())).thenReturn(UserEntityFixture.get(userName, password))

        assertDoesNotThrow {
            userService.singUp(userName, password)
        }

    }


    @Test
    fun 회원가입시_userName으로_회원가입한_유저가_이미_있는경우(){

        val userName = "userName"
        val password = "password"

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(UserEntityFixture.get(userName, password))
        `when`(userEntityRepository.findByUserName(userName)).thenReturn(UserEntityFixture.get(userName, password))

        assertThrows<CustomException> {
            userService.singUp(userName, password)
        }
    }

    @Test
    fun 로그인이_정상적으로_동작하는_경우(){
        val userName = "userName"
        val password = "password"

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(UserEntityFixture.get(userName, password))

        assertDoesNotThrow {
            userService.login(userName, password)
        }
    }

    @Test
    fun 로그인시_userName으로_회원가입한_유저가_없는_경우(){
        val userName = "userName"
        val password = "password"

        `when`(userEntityRepository.findByUserName(userName)).thenThrow(CustomException("user not found with name $userName"))

        assertThrows<CustomException> {
            userService.login(userName, password)
        }
    }

    @Test
    fun 로그인시_패스워드가_틀린_경우(){
        val userName = "userName"
        val password = "password"

        val originalPassword = "originalPassword"

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(UserEntityFixture.get(userName, originalPassword))
        
        assertThrows<CustomException> {
            userService.login(userName, password)
        }
    }
}