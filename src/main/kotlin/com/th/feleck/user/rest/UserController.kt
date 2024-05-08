package com.th.feleck.user.rest

import com.th.feleck.common.logger
import com.th.feleck.common.rest.BaseController
import com.th.feleck.common.rest.Response
import com.th.feleck.user.rest.dto.UserLoginRequest
import com.th.feleck.user.rest.dto.UserLoginResponse
import com.th.feleck.user.rest.dto.UserSignUpRequest
import com.th.feleck.user.rest.dto.UserResponse
import com.th.feleck.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
): BaseController() {
    val log = logger()
    @PostMapping("/api/v1/users/sign-up")
    fun signUp(
        @RequestBody request: UserSignUpRequest
    ): Response<UserResponse> {
        val (userName, password) = request
        val user =  userService.singUp(userName, password)
        val response = UserResponse.fromUser(user)
        return respond(response)
    }

    @PostMapping("/api/v1/users/login")
    fun signUp(
        @RequestBody request: UserLoginRequest
    ): Response<UserLoginResponse> {
        val (userName, password) = request
        val token = userService.login(userName, password)
        return respond(UserLoginResponse(token))
    }

}