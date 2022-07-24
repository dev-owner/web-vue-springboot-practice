package com.hodol.blog.inflearn_hodol_blog.service;

import com.hodol.blog.inflearn_hodol_blog.domain.Post;
import com.hodol.blog.inflearn_hodol_blog.repository.PostRepository;
import com.hodol.blog.inflearn_hodol_blog.request.PostCreate;
import com.hodol.blog.inflearn_hodol_blog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post write(PostCreate postCreate) {
        // repository.save(postCreate)
        // postCreate -> Entity

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        return postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        /**
         * response의 변환을 여기서 하는게 맞나?
         *
         * controller -> webPostService -> repository
         *            -> postService
         * 애매해지면 service를 나누기도 한다.
         */

        /**
         *  지금 클래스 분리를 잘 보면,
         *  request, response, entity를 명확하게 나누었다.
         *  request -> postCreate
         *  response -> postResponse
         *  entity -> Post
         */

        return response;

    }
}
