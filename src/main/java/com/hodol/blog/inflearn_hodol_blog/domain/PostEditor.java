package com.hodol.blog.inflearn_hodol_blog.domain;

import lombok.Builder;
import lombok.Getter;

/*
    Post에서 수정할 필드만 좁혀서 정의
 */
@Getter
public class PostEditor {
    private final String title;
    private final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
