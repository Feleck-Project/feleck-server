package com.th.feleck.common.exception

import com.th.feleck.common.rest.Response
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.contentType = "application/json"
        response?.status = HttpStatus.UNAUTHORIZED.value()
        response?.writer?.write(Response(data = "unautorized").toStream()) //TODO 수정 필요
    }

}
