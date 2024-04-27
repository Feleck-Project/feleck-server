package com.th.feleck.user.model

import java.time.OffsetDateTime

class User(
    val userName: String,
    val password: String,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val updateAt: OffsetDateTime? = null
)