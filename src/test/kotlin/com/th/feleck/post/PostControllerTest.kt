package com.th.feleck.post

import com.fasterxml.jackson.databind.ObjectMapper
import com.th.feleck.common.exception.CustomException
import com.th.feleck.fixture.PostEntityFixture
import com.th.feleck.post.model.Post
import com.th.feleck.post.rest.dto.PostCreateRequest
import com.th.feleck.post.rest.dto.PostModifyRequest
import com.th.feleck.post.service.PostService
import jdk.jshell.Snippet.Status
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var postService: PostService

    @Test
    @WithMockUser
    fun 포스트작성() {
        val title = "title"
        val body = "body"

        mockMvc.perform(post("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(PostCreateRequest(title, body)))
        )
        .andExpect(status().isOk)
    }

    @Test
    @WithAnonymousUser
    fun 포스트작성시_로그인하지않은경우(){
        val title = "title"
        val body = "body"

        mockMvc.perform(post("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(PostCreateRequest(title, body)))
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser
    fun 포스트수정(){
        val title = "title"
        val body = "body"

        `when`(postService.modifyPost(eq(title), eq(body), any(), eq(1L)))
            .thenReturn(Post.fromEntity(PostEntityFixture.get("userName", 1L, 1L)))


        mockMvc.perform(put("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(PostModifyRequest(1L, title, body)))
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithAnonymousUser
    fun 포스트수정시_로그인하지않은경우(){
        val title = "title"
        val body = "body"

        mockMvc.perform(put("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(PostCreateRequest(title, body)))
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser
    fun 포스트수정시_본인이_작성한_글이_아니라면_에러발생(){
        val title = "title"
        val body = "body"
        val userName = "userName"
        doThrow(CustomException("err")).`when`(postService).modifyPost(eq(title), eq(body), any(), eq(1L))

        mockMvc.perform(put("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(PostCreateRequest(title, body)))
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser
    fun 포스트수정시_수정하려는_글이_없는경우(){
        val title = "title"
        val body = "body"

        doThrow(CustomException("err")).`when`(postService).modifyPost(eq(title), eq(body), any(), eq(1L))

        mockMvc.perform(put("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(PostCreateRequest(title, body)))
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser
    fun 포스트삭제(){
        val title = "title"
        val body = "body"

//        `when`(postService.modifyPost(eq(title), eq(body), any(), eq(1L)))
//            .thenReturn(Post.fromEntity(PostEntityFixture.get("userName", 1L, 1L)))
//

        mockMvc.perform(delete("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(PostModifyRequest(1L, title, body)))
        )
            .andExpect(status().isUnauthorized)
    }


    @Test
    @WithAnonymousUser
    fun 포스트삭제시_로그인하지않은경우(){
        val title = "title"
        val body = "body"

        mockMvc.perform(delete("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser
    fun 포스트삭제시_본인이_작성한_글이_아니라면_에러발생(){
        val title = "title"
        val body = "body"
        doThrow(CustomException("err")).`when`(postService).deletePost(any(), any())

        mockMvc.perform(delete("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser
    fun 포스트삭제시_삭제하려는_글이_없는경우(){
        val title = "title"
        val body = "body"

        doThrow(CustomException("err")).`when`(postService).deletePost(any(), any())

        mockMvc.perform(delete("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun 피드목록() {

        `when`(postService.listPosts(any())).thenReturn(Page.empty())

        mockMvc.perform(get("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun 피드목록요청시_로그인하지_않은경우(){

        `when`(postService.listPosts(any())).thenReturn(Page.empty())

        mockMvc.perform(get("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun 내피드목록() {
        `when`(postService.myPosts(any(), any())).thenReturn(Page.empty())
        mockMvc.perform(get("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun 내피드목록요청시_로그인하지_않은경우(){

        `when`(postService.myPosts(any(), any())).thenReturn(Page.empty())
        mockMvc.perform(get("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isUnauthorized)
    }
}