package com.th.feleck.common.rest


open class BaseController {
    protected fun <T> respond(data: T): Response<T> {
        return ResponseBuilder.Build(data)
    }

    protected fun respond(error: ErrorResponse): Response<Void> {
        return ResponseBuilder.Build(error)
    }
}