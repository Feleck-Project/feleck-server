package com.th.feleck.post.rest.dto

data class PostModifyRequest (
    val postId: Long,
    val title: String,
    val body: String
)