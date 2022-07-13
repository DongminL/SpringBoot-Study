package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id     // 대표값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1, 2, 3, ... 자동 생성 어노테이션! (DB가 id를 자동 생성 어노테이션)
    private  Long id;   // 대표값 (DB에 저장될 때 @GeneratedValue에 의해 값을 가짐)

    @ManyToOne  // 해당 댓글 Entity 여러개가, 하나의 Article에 연관된다!
    @JoinColumn(name = "article_id")    // "article_id" 컬럼에 Article의 대표값을 저장, 외래키
    private Article article;    // Comment와 연관된 Article

    @Column // 변수를 DB에 저장하기 위해 Column으로 지정
    private String nickname;    // 닉네임

    @Column // 변수를 DB에 저장하기 위해 Column으로 지정
    private String body;    // 댓글 내용

    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생
        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if (dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");

        // Entity 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if (this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");

        // 객체 갱신
        if (dto.getNickname() != null)
            this.nickname = dto.getNickname();
        if (dto.getBody() != null)
            this.body = dto.getBody();
    }
}
