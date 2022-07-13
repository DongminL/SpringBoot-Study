package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    // DB에 접근하기 위한 Repository로 CrudRepository에 상속 받아
    // Entity인 Article 클래스와 Long타입 id를 가지고 서로 주고 받음

    @Override
    ArrayList<Article> findAll();   // 오버라이딩으로 데이터 타입을 ArrayList로 변환
}
