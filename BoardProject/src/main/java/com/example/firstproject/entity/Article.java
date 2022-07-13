package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity // DB가 해당 객체를 인식 가능! (해당 클래스로 테이블을 만든다!)
@AllArgsConstructor // 생성자 추가
@ToString   // toString 추가
@NoArgsConstructor  // 디폴트 생성자를 추가!
@Getter // 모든 getter 추가
public class Article {

    @Id // 대표값을 지정! like a 주민등록번호
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1, 2, 3, ... 자동 생성 어노테이션! (DB가 id를 자동 생성 어노테이션)
    private Long id;    // 대표값 (DB에 저장될 때 @GeneratedValue에 의해 값을 가짐)

    @Column // 변수를 DB에 저장하기 위해 Column으로 지정
    private String title;   // 제목

    @Column // 변수를 DB에 저장하기 위해 Column으로 지정
    private String content; // 내용

    // 업데이트 시 누락된 데이터 유지
    public void patch(Article article) {
        if (article.title != null)  // 원본 데이터가 null이 아니면
            this.title = article.title; // 원래 데이터 저장
        if (article.content != null)    // 원본 데이터가 null이 아니면
            this.content = article.content; // 원래 데이터 저장
    }
}
