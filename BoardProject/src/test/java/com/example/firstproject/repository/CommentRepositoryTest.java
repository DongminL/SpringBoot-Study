package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        /* Case 1 : 4번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 4L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");
            Comment a = new Comment(1L, article, "Park", "Good will hunting");
            Comment b = new Comment(2L, article, "Kim", "I am Sam");
            Comment c = new Comment(3L, article, "Choi", "The Shawshank Redemption");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력");
        }

        /* Case 2 : 1번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 1L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(1L, "가가가가", "1111");
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음");
        }

        /* Case 3 : 9번 게시글의 모든 댓글 조회 */
        {
            Long articleId = 9L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = null;
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(expected.toString(), comments.toString(), "9번 글은 댓글이 없음");
        }

        /* Case 4 : 9999번 게시글의 모든 댓글 조회 */
        {
            Long articleId = 9999L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = null;
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(expected.toString(), comments.toString(), "9999번 글은 댓글이 없음");
        }

        /* Case 5 : -1번 게시글의 모든 댓글 조회 */
        {
            Long articleId = -1L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = null;
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(expected.toString(), comments.toString(), "-1번 글은 댓글이 없음");
        }

    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1 : "Park"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "Park";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);


            // 예상하기
            Article article1 = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");
            Article article2 = new Article(5L , "당신의 소울 푸드는?", "댓글 ㄱㄱ");
            Article article3 = new Article(6L , "당신의 취미는?", "댓글 ㄱㄱㄱ");
            Comment a = new Comment(1L, article1, nickname, "Good will hunting");
            Comment b = new Comment(4L, article2, nickname, "치킨");
            Comment c = new Comment(7L, article3, nickname, "조깅");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString());

        }

        /* Case 2 : "Kim"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "Kim";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);


            // 예상하기
            Article article1 = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");
            Article article2 = new Article(5L , "당신의 소울 푸드는?", "댓글 ㄱㄱ");
            Article article3 = new Article(6L , "당신의 취미는?", "댓글 ㄱㄱㄱ");
            Comment a = new Comment(2L, article1, nickname, "I am Sam");
            Comment b = new Comment(5L, article2, nickname, "샤브샤브");
            Comment c = new Comment(8L, article3, nickname, "유튜브");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString());
        }

        /* Case 3 : null의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = null;

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);


            // 예상하기
            Article article1 = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");
            Article article2 = new Article(5L , "당신의 소울 푸드는?", "댓글 ㄱㄱ");
            Article article3 = new Article(6L , "당신의 취미는?", "댓글 ㄱㄱㄱ");
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString());
        }

        /* Case 4 : ""의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);


            // 예상하기
            Article article1 = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");
            Article article2 = new Article(5L , "당신의 소울 푸드는?", "댓글 ㄱㄱ");
            Article article3 = new Article(6L , "당신의 취미는?", "댓글 ㄱㄱㄱ");
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString());
        }

        /* Case 5 : "i"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "i";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);


            // 예상하기
            Article article1 = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");
            Article article2 = new Article(5L , "당신의 소울 푸드는?", "댓글 ㄱㄱ");
            Article article3 = new Article(6L , "당신의 취미는?", "댓글 ㄱㄱㄱ");
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString());
        }

    }
}