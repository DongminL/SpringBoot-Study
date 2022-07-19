package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글의 모든 댓글 조회
    @Query(value =
            "SELECT  * " +
            "FROM comment " +
            "WHERE article_id = :articleId",
            nativeQuery = true) // DB에 명령을 하기 위한 Query문
    List<Comment> findByArticleId(Long articleId);  // DB에서 Article ID로 댓글을 찾는 함수

    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname);  // DB에서 닉네임으로 댓글을 찾는 함수
}
