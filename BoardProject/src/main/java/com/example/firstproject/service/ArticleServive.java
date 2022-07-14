package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service    // 서비스 선언! (서비스 객체를 스트링 부트에 생성)
public class ArticleServive {
    @Autowired  // DI, 생성 객체를 가져와 연결!
    private ArticleRepository articleRepository;    // Repository 객체 선언

    public List<Article> index() {

        return articleRepository.findAll(); // DB에서 모든 Entity를 가져와 리턴
    }

    public Article show(Long id) {

        return articleRepository.findById(id).orElse(null); // DB에서 해당 id의 Entity를 가져와 리턴
    }

    @Transactional  // 해당 메소드를 트랜잭션으로 묶는다!, 에러 발생 시 DB를 롤백
    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();   // DTO를 Entity로 변환
        if (article.getId() != null) {  // id가 존재하면
            return null;    // null return
        }
        return articleRepository.save(article); // Entity를 DB에 저장하고 리턴
    }

    @Transactional  // 해당 메소드를 트랜잭션으로 묶는다!, 에러 발생 시 DB를 롤백
    public Article update(Long id, ArticleForm dto) {
        // 1. 수정용 Entity 생성
        Article article = dto.toEntity();

        // 2. 대상 Entity 조회
        Article target = articleRepository.findById(id).orElse(null);   // DB에서 해당 id의 Entity를 가져와 target에 저장

        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if (target == null || id != article.getId()) {  // DB에 id가 없거나 요청하는 id가 원래 데이터의 id와 다를 경우
            // 400, 잘못된 요청 응답!
            return null;   // ResponseEntity로 잘못된 요청으로 null 반환
        }

        // 4. 업데이트 및 정상 응답(200)
        target.patch(article);  // 수정하지 않는 데이터를 유지한채 조회한 댓글 수정
        Article updated = articleRepository.save(target);   // 업데이트된 Entity를 DB에 저장
        return updated; // 업데이트된 Entity 리턴
    }

    @Transactional  // 해당 메소드를 트랜잭션으로 묶는다!, 에러 발생 시 DB를 롤백
    public Article delete(Long id) {
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);   // DB에서 해당 id의 Entity를 가져와 target에 저장

        // 잘못된 요청 처리
        if (target == null) {   // 해당 id가 DB에 없을 경우
            return null;    // ResponseEntity로 잘못된 요청으로 null 리턴
        }

        // 대상  삭제
        articleRepository.delete(target);   // DB에서 해당 id의 Entity 삭제
        return target;  // 해당 id의 Entity 리턴
    }

    @Transactional  // 해당 메소드를 트랜잭션으로 묶는다!, 에러 발생 시 DB를 롤백
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 Entity 묶음으로 변환
//        List<Article> articleList = dtos.stream()
//                .map(dto -> dto.toEntity())
//                .collect(Collectors.toList());
        
        // dto 묶음을 Entity 묶음으로 변환
        List<Article> articleList = new ArrayList<>();  // Entity List 생성
        for (int i = 0; i < dtos.size(); i++) { // DTO List의 크기만큼 반복
            ArticleForm dto = dtos.get(i);  // DTO List에서 i 번째 DTO를 가져와 저장
            Article entity = dto.toEntity();    // DTO를 Entity로 변환
            articleList.add(entity);    // Entity List에 추가
        }

        // Entity 묶음을 DB로 저장
//        articleList.stream()
//                .forEach(article -> articleRepository.save(article));

        // Entity 묶음을 DB로 저장
        for (int i = 0; i < articleList.size(); i++) {  // Entity List의 크기만큼 반복
            Article entity = articleList.get(i);    // Entity List에서 i 번째 Entity를 가져와 저장
            articleRepository.save(entity); // i 번째 Entity를 DB에 저장
        }

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );

        // 결과값 반환
        return articleList; // Entity List 리턴
    }
}
