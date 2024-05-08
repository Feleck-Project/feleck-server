package com.th.feleck.post.rest

import com.th.feleck.common.rest.BaseController
import com.th.feleck.common.rest.Response
import com.th.feleck.post.jpa.entity.PostEntity
import com.th.feleck.post.model.Post
import com.th.feleck.post.rest.dto.PostCreateRequest
import com.th.feleck.post.rest.dto.PostModifyRequest
import com.th.feleck.post.rest.dto.PostResponse
import com.th.feleck.post.service.PostService
import jakarta.annotation.Nullable
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController

class PostController (
  private val postService: PostService
): BaseController() {

    @PostMapping
    fun createPost(
        @RequestBody request: PostCreateRequest,
        authentication: Authentication
    ): Response<String> {
        val (title, body) = request
        postService.createPost(title, body, authentication.username)
        return respond(data = "null")
    }

    @PutMapping
    fun modifyPost(
        @RequestBody request: PostModifyRequest,
        authentication: Authentication
    ): Response<PostResponse> {
        val (postId, title, body) = request
        val post = postService.modifyPost(title, body, authentication.username, postId)
        return respond(data = PostResponse.fromPost(post))
    }

    @DeleteMapping("")
    fun deletePost(
        @PathVariable postId: Long,
        authentication: Authentication
    ): Response<String> {
        postService.deletePost(authentication.username, postId)
        return respond(data = "null")
    }

    @GetMapping
    fun listPosts(
        pageable: Pageable,
        authentication: Authentication
    ): Response<Page<PostEntity>> {
        val posts = postService.listPosts(pageable)
        return respond(data = posts)
    }

    @GetMapping
    fun myPosts(
        pageable: Pageable,
        authentication: Authentication
    ): Response<Page<Post>> {
        val myPosts = postService.myPosts(authentication.username, pageable)
        return respond(data = myPosts)
    }
}