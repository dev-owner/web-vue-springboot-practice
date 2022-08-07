package com.hodol.blog.inflearn_hodol_blog.domain;

import com.hodol.blog.inflearn_hodol_blog.request.PostEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

//    public String getTitle() {
//        //절대 서비스의 정책을 넣으면 안된다.
//        return this.title.substring(0, 10);
//    }

    public void change(String title, String content) {
        // 이 방법도 나쁘지는 않은데 파라미터가 많으면 실수할 확률이 높다.
        this.title = title;
        this.content = content;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }
}
