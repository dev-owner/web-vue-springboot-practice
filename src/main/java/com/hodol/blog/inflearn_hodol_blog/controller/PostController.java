package com.hodol.blog.inflearn_hodol_blog.controller;

import com.hodol.blog.inflearn_hodol_blog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
//    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) throws Exception {
    public Map<String, String> post(@RequestBody @Valid PostCreate params) throws Exception {
//        log.info("title={}, content={}", title, content);
        log.info("params={}", params);

        // validation
        String title = params.getTitle();
//        if(title == null || title.equals("")) {
//            //error
//            // 검증 해야할게 많은데 이런방식으로?
//            // dto에 validation 쓰고, @Valid 사용
//            throw new Exception("no title!");
//        }

        String content = params.getContent();

        // 잘못 들어온다면, response를 만들고 싶다.
        // 이것도 좋지만.. 매번 메서드마다 검증을 해야한다.
        //  > 까먹을 수 있고, 지겹다, 버그가 발생할 여지가 높다.
        // 응답 값에 hashmap > 응답 클래스를 만들어 주자
        // 여러개의 에러처리가 힘들다
        // 세 번 이상 반복작업은 피하자
        // controlleradvice를 통해 개선해보자
        // 모든 컨트롤러에 대한 에러를 처리
       /* if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String invalidFieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(invalidFieldName, errorMessage);
            return error;
        }*/
        return Map.of();
    }

}
