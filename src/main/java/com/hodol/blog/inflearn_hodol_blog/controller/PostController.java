package com.hodol.blog.inflearn_hodol_blog.controller;

import com.hodol.blog.inflearn_hodol_blog.request.PostCreate;
import com.hodol.blog.inflearn_hodol_blog.request.PostEdit;
import com.hodol.blog.inflearn_hodol_blog.request.PostSearch;
import com.hodol.blog.inflearn_hodol_blog.response.PostResponse;
import com.hodol.blog.inflearn_hodol_blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    // SSR -> jsp, thymeleaf, mustach, freemarker,
    // -> html rendering
    // SPA -> vue, react,
    // -> javascript + API (json)
    // vue + SSR = nuxt
    // react + SSR = next

    private final PostService postService;

    // HTTP Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

    // 글 등록
    // POST METHOD

    @PostMapping("/posts")
//    public String post(@RequestParam Map<String, String> params) {
//    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) throws Exception {
    public void post(@RequestBody @Valid PostCreate request) throws Exception {
//        log.info("title={}, content={}", title, content);
        log.info("params={}", request);

        // validation
        String title = request.getTitle();
//        if(title == null || title.equals("")) {
//            //error
//            // 검증 해야할게 많은데 이런방식으로?
//            // dto에 validation 쓰고, @Valid 사용
//            throw new Exception("no title!");
//        }

        String content = request.getContent();

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

        // service layer를 만들고 여기서 repository를 호출하는 것을 추천
        // repository.save(params)
        // db.save(params)


        // POST -> 200 or 201
        // client에서 작성한 것을 다시 내려달라는 요청이 있을 때도 있다.
        // case1. 저장한 데이터 enitty -> response로 응답하기
        // case2. 저장한 데이터의 pk만 응답
        //      client에서는 수신한 id를 글 조회 api를 통해 글 데이터를 수신받음
        // case3. 응답 필요없음

        postService.write(request);
        //return postService.write(request);
        //post면 만든 객체에 대해서 응답을 보통 안준다.
    }

    /*
        /posts -> 글 전체 조회 (검색 + 페이징)
        /posts/{postId} -> 글 한개만 조회
    * */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id) {
        return postService.get(id);
    }

    // 여러개 조회 API
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit) {
        return postService.edit(postId, postEdit);
    }
}
