package com.hodol.blog.inflearn_hodol_blog.repository;

import com.hodol.blog.inflearn_hodol_blog.domain.Post;
import com.hodol.blog.inflearn_hodol_blog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
