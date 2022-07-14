package com.example.firstproject.dto;

import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 생성자 추가
@NoArgsConstructor  // 디폴트 생성자 추가
@ToString   // toString 추가
@Getter // 모든 Getter 추가
public class CommentDto {
    private Long id;    // DTO의 ID

    @JsonProperty("article_id") // DB의 article_id Column을 articleID와 매칭
    private Long articleId; // Article의 ID

    private String nickname;    // 닉네임
    private String body;    // 댓글 내용

    // Entity -> DTO로 변환
    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
