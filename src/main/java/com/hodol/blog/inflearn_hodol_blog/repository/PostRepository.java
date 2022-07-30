package com.hodol.blog.inflearn_hodol_blog.repository;

import com.hodol.blog.inflearn_hodol_blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

}
