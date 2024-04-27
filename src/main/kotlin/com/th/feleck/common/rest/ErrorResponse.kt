package com.th.feleck.common.rest

class ErrorResponse(
    private val errorCode: String,
    private val errorDescription: String
){
    fun getErrorCode(): String = errorCode

    fun getErrorDescription(): String = errorDescription
}