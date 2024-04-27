package com.th.feleck.common.rest

class Response<T> {
    private var data: T? = null
    private var error: ErrorResponse? = null

    constructor()

    constructor(errors: ErrorResponse) {
        this.error = errors
    }

    constructor(data: T) {
        this.data = data
    }

    fun getData() = data
    fun getErrors() = error
}