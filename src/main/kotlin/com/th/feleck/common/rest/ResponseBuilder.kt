package com.th.feleck.common.rest

object ResponseBuilder {

    fun <T> Build(item: T): Response<T>{
        return Response(item)
    }

    fun Build(errorResponse: ErrorResponse): Response<ErrorResponse>{
        return Response(errorResponse)
    }
}