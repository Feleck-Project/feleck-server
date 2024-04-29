package com.th.feleck.user.rest

import com.th.feleck.common.rest.BaseController
import com.th.feleck.common.rest.Response
import com.th.feleck.user.model.User
import com.th.feleck.user.rest.dto.UserLoginRequest
import com.th.feleck.user.rest.dto.UserSignUpRequest
import com.th.feleck.user.rest.dto.UserSignUpResponse
import com.th.feleck.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
): BaseController() {
    @PostMapping("/api/v1/users/sign-up")
    fun signUp(
        @RequestBody request: UserSignUpRequest
    ): Response<UserSignUpResponse> {
        val (userName, password) = request
        val user =  userService.singUp(userName, password)
        val response = UserSignUpResponse.fromUser(user)
        return respond(response)
    }

    @PostMapping("/api/v1/users/login")
    fun signUp(
        @RequestBody request: UserLoginRequest
    ): Response<String> {
        val (userName, password) = request
        return respond(userService.login(userName, password))
    }

}