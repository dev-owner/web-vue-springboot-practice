package com.hodol.blog.inflearn_hodol_blog.service;

import com.hodol.blog.inflearn_hodol_blog.domain.Post;
import com.hodol.blog.inflearn_hodol_blog.domain.PostEditor;
import com.hodol.blog.inflearn_hodol_blog.repository.PostRepository;
import com.hodol.blog.inflearn_hodol_blog.request.PostCreate;
import com.hodol.blog.inflearn_hodol_blog.request.PostEdit;
import com.hodol.blog.inflearn_hodol_blog.request.PostSearch;
import com.hodol.blog.inflearn_hodol_blog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        return PostResponse.builder()
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

    }

    public List<PostResponse> getList(PostSearch postSearch) {
        /**
         * 이것도 나쁘지는 않은데, 나중에 많아지면 문제
         */
//        return postRepository.findAll().stream()
//                .map(post -> PostResponse.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .build())
//                .collect(Collectors.toList());

//        return postRepository.findAll(pageable).stream()
//                .map(PostResponse::new)
//                .collect(Collectors.toList());

        //parameter로 pageable을 그대로 써도 되지만.. 나중에 정렬, 검색 옵션등이 추가될수 있다.
        // 그럴수 있기 때문에, request class를 따로 만든다.
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();
        PostEditor postEditor = postEditorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);
        return new PostResponse(post);
    }


}
