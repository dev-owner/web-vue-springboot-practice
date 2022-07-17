package com.hodol.blog.inflearn_hodol_blog.controller;

import com.hodol.blog.inflearn_hodol_blog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
public class PostController {
    // SSR -> jsp, thymeleaf, mustach, freemarker,
    // -> html rendering
    // SPA -> vue, react,
    // -> javascript + API (json)
    // vue + SSR = nuxt
    // react + SSR = next

    @GetMapping("/posts")
    public String get() {
        return "hello world!";
    }

    // HTTP Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

    // 글 등록
    // POST METHOD

    @PostMapping("/posts")
//    public String post(@RequestParam Map<String, String> params) {
    public String post(@RequestBody PostCreate params) throws Exception {
//        log.info("title={}, content={}", title, content);
        log.info("params={}", params);

        // validation
        String title = params.getTitle();
        if(title == null || title.equals("")) {
            //error
            throw new Exception("no title!");
        }

        String content = params.getContent();
        if(content == null || content.equals("")) {
            //error
        }

        return "hello world!";
    }

}
