package com.th.feleck.post.service

import com.th.feleck.common.exception.CustomException
import com.th.feleck.post.jpa.entity.PostEntity
import com.th.feleck.post.jpa.repository.PostEntityRepository
import com.th.feleck.post.model.Post
import com.th.feleck.user.jpa.repository.UserEntityRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postEntityRepository: PostEntityRepository,
    private val userEntityRepository: UserEntityRepository
) {

    @Transactional
    fun createPost(title: String, body: String, userName: String){
        val userEntity = userEntityRepository.findByUserName(userName) ?: throw CustomException("user not found")

        postEntityRepository.save(PostEntity.of(title, body, userEntity))
    }

    @Transactional
    fun modifyPost(title: String, body: String, userName: String, postId: Long): Post {
        val userEntity = userEntityRepository.findByUserName(userName) ?: throw CustomException("user not found")

        //post exist
        val postEntity = postEntityRepository.findById(postId).orElseThrow { CustomException("post not found") }

        //post permission
        if(postEntity.user != userEntity)
            throw CustomException("invalid permission")

        postEntity.title = title
        postEntity.body = body

        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity))
    }

    @Transactional
    fun deletePost(userName: String, postId: Long) {
        val userEntity = userEntityRepository.findByUserName(userName) ?: throw CustomException("user not found")

        //post exist
        val postEntity = postEntityRepository.findById(postId).orElseThrow { CustomException("post not found") }

        //post permission
        if(postEntity.user != userEntity)
            throw CustomException("invalid permission")

       postEntityRepository.delete(postEntity)
    }

    fun listPosts(pageable: Pageable): Page<PostEntity> {
        return postEntityRepository.findAll(pageable)
    }

    fun myPosts(userNamel: String, pageable: Pageable): Page<Post> {
        val userEntity = userEntityRepository.findByUserName(userNamel)?: throw CustomException("user not found")
        return postEntityRepository.findAllByUser(userEntity, pageable).map { Post.fromEntity(it) }
    }
}

