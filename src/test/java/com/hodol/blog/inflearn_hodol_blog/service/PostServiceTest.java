package com.hodol.blog.inflearn_hodol_blog.service;

import com.hodol.blog.inflearn_hodol_blog.domain.Post;
import com.hodol.blog.inflearn_hodol_blog.repository.PostRepository;
import com.hodol.blog.inflearn_hodol_blog.request.PostCreate;
import com.hodol.blog.inflearn_hodol_blog.request.PostEdit;
import com.hodol.blog.inflearn_hodol_blog.request.PostSearch;
import com.hodol.blog.inflearn_hodol_blog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        //when
        postService.write(postCreate);

        //then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목", post.getTitle());
        assertEquals("내용", post.getContent());

    }

    @Test
    @DisplayName("글 한개 조회")
    void test2() {
        //given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);


        //when
        PostResponse postResponse = postService.get(requestPost.getId());

        //then
        assertNotNull(postResponse);
        Post returnPost = postRepository.findAll().get(0);
        assertEquals("foo", returnPost.getTitle());
        assertEquals("bar", returnPost.getContent());

        // json으로 잘 내려오지만, 문제가 발생할 수 있음
        // post service에서 repository에서 조회 후 controller에 응답하는데,
        // 나중에 큰 문제 발생
        // -> 클라이언트 요구사항
        // json 응답에서 title 값 길이를 최대 10글자로 해주세요.
        // 클라가 맞긴한데, 서버에서 해야 할 경우
        // public String getTitle() {
        //        return this.title.substring(0, 10);
        //    }
        // 위를 Post entity에 생성하면.., 근데 이건 당연히 이상한거같은데?
        // 그럴때는 응답 클래스 분리 -> 서비스 정책에 맞는 응답 클래스를 만들어야 한다.


    }

    @Test
    @DisplayName("글 1 page 조회")
    void test3() {
        //given
        List<Post> requestPosts = IntStream.range(1, 20)
                .mapToObj(i ->
                        Post.builder()
                                .title("제목 " + i)
                                .content("내용 " + i)
                                .build()
                ).collect(Collectors.toList());

        // sql -> select, limit, offset
        postRepository.saveAll(requestPosts);

//        Pageable pageable = PageRequest.of(0, 5);
        PostSearch postSearch = PostSearch.builder().page(1).build();

        //when
        List<PostResponse> posts = postService.getList(postSearch);

        //then
        assertEquals(10L, posts.size());
        assertEquals("제목 19", posts.get(0).getTitle());

    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        //given

        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();
        postRepository.save(post);
        PostEdit postEdit = PostEdit.builder()
                .title("호돌걸")
                .content("반포자이")
                .build();
        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("호돌걸", changedPost.getTitle());
        assertEquals("반포자이", changedPost.getContent());
    }


    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        //given

        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();
        postRepository.save(post);
        PostEdit postEdit = PostEdit.builder()
                .title("호돌맨")
                .content("초가집")
                .build();
        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("호돌맨", changedPost.getTitle());
        assertEquals("초가집", changedPost.getContent());
    }

}