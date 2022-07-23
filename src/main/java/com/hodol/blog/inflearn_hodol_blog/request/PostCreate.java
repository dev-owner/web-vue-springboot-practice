package com.hodol.blog.inflearn_hodol_blog.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;
    @NotBlank(message = "컨텐츠를 입력해주세요.")
    private String content;

    @Builder // class 위에 선언할 수도 있지만, 여러가지 케이스에서 모순이 발생할 수 있어서 생성자 위를 추천
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //builder의 장점
    // 가독성에 좋다
    // 필요한 값만 받을 수 있다. -> 오버로딩 따로 구현 안해도됨
    // 객체의 불변성
}

