package com.th.feleck.user.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.th.feleck.user.model.User
import com.th.feleck.user.rest.dto.UserLoginRequest
import com.th.feleck.user.rest.dto.UserSignUpRequest
import com.th.feleck.user.service.UserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserController::class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var userService: UserService


    @Test
    fun 회원가입(){
        val userName = "userName"
        val password = "password"

        val request = UserSignUpRequest(userName, password);

        `when`(userService.singUp(userName, password)).thenReturn(User(0, userName, password))

        //TODO : Mocking
        mockMvc.perform(
            post("/api/v1/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data.userName").value("userName"))
            .andExpect(jsonPath("$.data.password").value("password"))
    }

    @Test
    fun 회원가입시_이미_회원가입된_userName으로_회원가입을_하는경우_에러반환(){
        val userName = "userName"
        val password = "password"

        val request = UserSignUpRequest(userName, password);

        mockMvc.perform(
            post("/api/v1/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request))
        )
            .andExpect(status().isConflict)
    }

    @Test
    fun 로그인(){
        val userName = "userName"
        val password = "password"

        `when`(userService.login(userName, password)).thenReturn("test_token")

        mockMvc.perform(
            post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(UserLoginRequest(userName, password)))
        )
            .andExpect(status().isOk)
    }

    @Test
    fun 로그인시_회원가입이_안된_userName을_입력할경우_에러반환(){
        val userName = "userName"
        val password = "password"

        `when`(userService.login(userName, password)).thenReturn("test_token")

        mockMvc.perform(
            post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(UserLoginRequest(userName, password)))
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun 로그인시_틀린_password를_입력할경우_에러반환(){
        val userName = "userName"
        val password = "password"


        `when`(userService.login(userName, password)).thenReturn("test_token")

        mockMvc.perform(
            post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(UserLoginRequest(userName, password)))
        )
            .andExpect(status().isUnauthorized)
    }
}