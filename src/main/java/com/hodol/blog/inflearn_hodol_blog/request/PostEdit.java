package com.hodol.blog.inflearn_hodol_blog.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PostEdit {
    /*
        PostCreate와 내용이 완전 같은데.. 역할이 다르기 때문에 만드는걸 추천한다.
        나중에 내용 추가되면 하나에서 둘로 분리하기가 굉장히 어렵다.
     */
    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;
    @NotBlank(message = "컨텐츠를 입력해주세요.")
    private String content;

    @Builder // class 위에 선언할 수도 있지만, 여러가지 케이스에서 모순이 발생할 수 있어서 생성자 위를 추천
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
