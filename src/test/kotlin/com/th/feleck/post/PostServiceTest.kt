package com.th.feleck.post

import com.th.feleck.common.exception.CustomException
import com.th.feleck.fixture.PostEntityFixture
import com.th.feleck.fixture.UserEntityFixture
import com.th.feleck.post.jpa.entity.PostEntity
import com.th.feleck.post.jpa.repository.PostEntityRepository
import com.th.feleck.post.service.PostService
import com.th.feleck.user.jpa.entity.UserEntity
import com.th.feleck.user.jpa.repository.UserEntityRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional

@SpringBootTest
class PostServiceTest {

    @Autowired
    private lateinit var postService: PostService

    @Autowired
    private lateinit var userEntityRepository: UserEntityRepository

    @Autowired
    private lateinit var postEntityRepository: PostEntityRepository

    @Test
    fun 포스트작성이_성공한경우(){
        val title = "title"
        val body = "body"
        val userName = "userName"

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(mock(UserEntity::class.java))
        `when`(postEntityRepository.save(any())).thenReturn(mock(PostEntity::class.java))

        assertDoesNotThrow {
            postService.createPost(title, body, userName)
        }
    }

    @Test
    fun 포스트작성시_요청한유저가_존재하지않는경우(){
        val title = "title"
        val body = "body"
        val userName = "userName"

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(mock(UserEntity::class.java))
        `when`(postEntityRepository.save(any())).thenReturn(mock(PostEntity::class.java))

        val e: CustomException = assertThrows {
            postService.createPost(title, body, userName)
        }

        //TODO: error 부분 수정 필요
        Assertions.assertEquals("ErrorCode.USER_NOT_FOUND", e.message)
    }

    @Test
    fun 포스트수정이_성공한경우(){
        val title = "title"
        val body = "body"
        val userName = "userName"
        val postId = 1L

        val postEntity = PostEntityFixture.get(userName, postId, 1L)
        val userEntity = postEntity.user

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(userEntity)
        `when`(postEntityRepository.findById(any())).thenReturn(Optional.of(postEntity))
        `when`(postEntityRepository.saveAndFlush(any())).thenReturn(postEntity)
        assertDoesNotThrow {
            postService.modifyPost(title, body, userName, postId)
        }
    }

    @Test
    fun 포스트수정시_포스트가_존재하지않는_경우(){
        val title = "title"
        val body = "body"
        val userName = "userName"
        val postId = 1L

        val postEntity = PostEntityFixture.get(userName, postId, 1L)
        val userEntity = postEntity.user

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(userEntity)
        `when`(postEntityRepository.findById(any())).thenReturn(Optional.of(postEntity))

        val e = assertThrows<CustomException> {
            postService.modifyPost(title, body, userName, postId)
        }

        //TODO: error 부분 수정 필요
        Assertions.assertEquals("ErrorCode.POST_NOT_FOUND", e.message)
    }

    @Test
    fun 포스트수정시_권한이_없는_경우(){
        val title = "title"
        val body = "body"
        val userName = "userName"
        val postId = 1L

        val postEntity = PostEntityFixture.get(userName, postId, 1L)
        val userEntity = UserEntityFixture.get("userName", "password", 1)

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(userEntity)
        `when`(postEntityRepository.findById(any())).thenReturn(Optional.of(postEntity))

        val e = assertThrows<CustomException> {
            postService.modifyPost(title, body, userName, postId)
        }

        //TODO: error 부분 수정 필요
        Assertions.assertEquals("ErrorCode.INVALID_PERMISSION", e.message)
    }

    @Test
    fun 포스트삭제시_성공한경우(){
        val userName = "userName"
        val postId = 1L

        val postEntity = PostEntityFixture.get(userName, postId, 1L)
        val userEntity = postEntity.user

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(userEntity)
        `when`(postEntityRepository.findById(any())).thenReturn(Optional.of(postEntity))
        assertDoesNotThrow {
            postService.deletePost(userName, postId)
        }
    }

        @Test
        fun 포스트삭제시_포스트가_존재하지않는_경우(){

        val userName = "userName"
        val postId = 1L

        val postEntity = PostEntityFixture.get(userName, postId, 1L)
        val userEntity = postEntity.user

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(userEntity)
        `when`(postEntityRepository.findById(any())).thenReturn(Optional.of(postEntity))

        val e = assertThrows<CustomException> {
            postService.deletePost(userName, postId)
        }

        //TODO: error 부분 수정 필요
        Assertions.assertEquals("ErrorCode.POST_NOT_FOUND", e.message)
    }

    @Test
    fun 포스트삭제시_권한이_없는_경우(){
        val title = "title"
        val body = "body"
        val userName = "userName"
        val postId = 1L

        val postEntity = PostEntityFixture.get(userName, postId, 1L)
        val userEntity = UserEntityFixture.get("userName", "password", 1)

        `when`(userEntityRepository.findByUserName(userName)).thenReturn(userEntity)
        `when`(postEntityRepository.findById(any())).thenReturn(Optional.of(postEntity))

        val e = assertThrows<CustomException> {
            postService.deletePost(userName, postId)
        }

        //TODO: error 부분 수정 필요
        Assertions.assertEquals("ErrorCode.INVALID_PERMISSION", e.message)
    }

    fun 피드목록요청이_성공한경우(){
        val pageable = mock(Pageable::class.java)
        `when`(postEntityRepository.findAll(pageable)).thenReturn(Page.empty())
        assertDoesNotThrow {
            postService.listPosts(pageable)
        }
    }

    fun 내피드목록요청이_성공한경우(){
        val pageable = mock(Pageable::class.java)
        val user = mock(UserEntity::class.java)
        `when`(postEntityRepository.findAllByUser(any(), pageable)).thenReturn(Page.empty())
        `when`(userEntityRepository.findByUserName(any())).thenReturn(user)
        assertDoesNotThrow {
            postService.myPosts("", pageable)
        }
    }
}