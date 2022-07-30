package com.hodol.blog.inflearn_hodol_blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodol.blog.inflearn_hodol_blog.domain.Post;
import com.hodol.blog.inflearn_hodol_blog.repository.PostRepository;
import com.hodol.blog.inflearn_hodol_blog.request.PostCreate;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청")
    void postTest() throws Exception {
        // 글 제목
        // 글 내용
        // 사용자
        // id
        // name
        // level
        // ..
        // 이런식의 데이터를 표현하려면 kv는 어렵다.
        // title=xx&content=xx&user_id=xx&userName=xx.. -> 한계
        /*{
            "title": "xxx",
            "content": "xxx",
            "user" : {
                "id": "xxx"
            }
        }*/
        // json의 큰 장점
        // 이것으로 class로 표현하기도 괜찮다.
        // 그렇기 때문에 json으로 통신이 대세

        //given
//        PostCreate postCreate = new PostCreate("제목", "내용");

        // 파라미터 순서 바뀌어도 괜찮고
        //


        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        System.out.println(json);

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                ) // application/json
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 title 값은 필수다.")
    void postValidationTest() throws Exception {
        //given

        PostCreate postCreate = PostCreate.builder()
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);


        //expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                ) // application/json
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청 입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 DB에 값이 저장된다.")
    void test3() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목", post.getTitle());
        assertEquals("내용", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("글 1개 조회, 10글자 제목 validation")
    void test5() throws Exception {
        //given
        Post post = Post.builder()
                .title("123456789012345")
                .content("내용")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("글 1 page 조회")
    void test6() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i ->
                        Post.builder()
                                .title("제목 " + i)
                                .content("내용 " + i)
                                .build()
                ).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        //expected
        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].id", is(30)))
                .andExpect(jsonPath("$[0].title", is("제목 30")))
                .andExpect(jsonPath("$[0].content", is("내용 30")))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test7() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i ->
                        Post.builder()
                                .title("제목 " + i)
                                .content("내용 " + i)
                                .build()
                ).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        //expected
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].id", is(30)))
                .andExpect(jsonPath("$[0].title", is("제목 30")))
                .andExpect(jsonPath("$[0].content", is("내용 30")))
                .andDo(print());
    }

}