package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import lombok.*;

import javax.persistence.*;

@Entity // DB가 해당 객체를 인식 가능! (해당 클래스로 테이블을 만든다!)
@Getter // 모든 Getter 추가
@ToString   // toString 추가
@AllArgsConstructor // 생성자 추가
@NoArgsConstructor  // 디폴트 생성자 추가
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

    // DTO -> Entity로 변환
    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생
        if (dto.getId() != null)    // DTO의 ID가 있으면
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");   // 예외 발생
        if (dto.getArticleId() != article.getId())  // DTO가 가진 Article ID와 주소에 적은 Article ID가 다르면
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");  // 예외 발생

        // Entity 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );  // DTO를 Entity로 변환하고 리턴
    }

    // 수정하지 않는 데이터를 유지한채 데이터 수정
    public void patch(CommentDto dto) {
        // 예외 발생
        if (this.id != dto.getId()) // 주소에 적은 ID와 수정할 DTO의 ID가 다르면
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");   // 예외 발생

        // 객체 갱신
        if (dto.getNickname() != null)  // 닉네임을 수정할 것이면
            this.nickname = dto.getNickname();  // 닉네임 수정
        if (dto.getBody() != null)  // 댓글 내용을 수정할 것이면
            this.body = dto.getBody();  // 댓글 내용 수정
    }
}
