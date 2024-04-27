package com.th.feleck.user.rest

import com.th.feleck.common.rest.BaseController
import com.th.feleck.common.rest.Response
import com.th.feleck.user.model.User
import com.th.feleck.user.rest.dto.UserLoginRequest
import com.th.feleck.user.rest.dto.UserSignUpRequest
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
        @RequestBody userSignUpRequest: UserSignUpRequest
    ): Response<User> {
        val (userName, password) = userSignUpRequest
        return respond(userService.singUp(userName, password))
    }

    @PostMapping("/api/v1/users/login")
    fun signUp(
        @RequestBody userLoginRequest: UserLoginRequest
    ): Response<String> {
        val (userName, password) = userLoginRequest
        return respond(userService.login(userName, password))
    }

}