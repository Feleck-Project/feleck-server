package com.th.feleck.common.rest

import com.th.feleck.common.exception.CustomException
import com.th.feleck.common.logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler: BaseController() {

    private val log = logger()

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception): Response<ErrorResponse> {
        log.error("error occurred. details: ${e.message}")
        //TODO: implement errorCode, errorDescription
        return respond(ErrorResponse("errorCode", "errorDescription"))
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: Exception): Response<ErrorResponse> {
        log.error("custom exception occured. details: ${e.message}")
        return respond(ErrorResponse("customExceptionCode", "customExceptionDescritpion"))
    }
}