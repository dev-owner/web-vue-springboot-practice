package com.hodol.blog.inflearn_hodol_blog.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/posts 요청 시 hello world 출력")
    void getTest() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello world!"))
                .andDo(MockMvcResultHandlers.print());
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

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목\", \"content\": \"내용\"}")
                ) // application/json
                .andExpect(status().isOk())
                .andExpect(content().string("hello world!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청 시 title 값은 필수다.")
    void postValidationTest() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"\", \"content\": \"내용\"}")
                ) // application/json
                .andExpect(status().isOk())
                .andExpect(content().string("hello world!"))
                .andDo(MockMvcResultHandlers.print());
    }
}