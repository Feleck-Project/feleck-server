package com.th.feleck.common.rest


open class BaseController {
    protected fun <T> respond(data: T): Response<T> {
        return ResponseBuilder.Build(data)
    }

    protected fun <T> respond(error: ErrorResponse): Response<ErrorResponse> {
        return ResponseBuilder.Build(error)
    }
}