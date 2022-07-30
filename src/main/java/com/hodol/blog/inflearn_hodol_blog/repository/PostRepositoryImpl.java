package com.hodol.blog.inflearn_hodol_blog.repository;

import com.hodol.blog.inflearn_hodol_blog.domain.Post;
import com.hodol.blog.inflearn_hodol_blog.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hodol.blog.inflearn_hodol_blog.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset()) // 값을 꺼내서 조합의 형태는.. 조건이 달라지면 힘들다.
                .orderBy(post.id.desc())
                .fetch();
    }
}
