package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 생성자 추가  
@ToString   // toString 추가
public class ArticleForm {

    private Long id;    // id
    private String title;   // 제목
    private String content; // 내용

    public Article toEntity() {
        return new Article(id, title, content);   // Entity 생성
    }
}
