package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleServive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController // RestAPI 용 컨트롤러! 데이터(JSON)를 리턴
@Slf4j
public class ArticleApiController {

    @Autowired  // DI, 스프링 부트가 이미 생성해 놓은 객체를 가져와 연결
    private ArticleServive articleServive;    // Service 객체 선언

    // GET
    @GetMapping("/api/articles")    // http://localhost:8080/api/articles 주소로 받음
    public List<Article> index() {
        return articleServive.index(); // Service 계층을 통해 DB에서 모든 Entity를 가져와 리턴
    }

    @GetMapping("/api/articles/{id}")   // http://localhost:8080/api/articles/id 주소로 받음 (id는 변수)
    public Article show(@PathVariable Long id) {
        return articleServive.show(id); // Service 계층을 통해 DB에서 해당 id의 Entity를 가져와 리턴
    }

    // POST
    @PostMapping("/api/articles")   // htpp://localhost:8080/api/articles 주소로 보냄
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleServive.create(dto);   // Service 계층을 통해 DB에 DTO와 같은 데이터 생성
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :    // ResponseEntity로 Body에 Entity와 정상 응답 리턴
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // ResponseEntity로 잘못된 요청 응답 리턴
    }

    // PATCH
    @PatchMapping("/api/articles/{id}") // http://localhost:8080/api/articles/id 주소의 데이터 업데이트
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        Article updated = articleServive.update(id, dto);   // Service 계층을 통해 DB에 해당 id의 데이터를 DTO에 맞게 수정
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) : // ResponseEntity로 Body에 Entity와 정상 응답 리턴
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // ResponseEntity로 잘못된 요청 응답 리턴
    }

    // DELETE
    @Transactional
    @DeleteMapping("/api/articles/{id}")    // http://localhost:8080/api/articles/id 주소의 데이터 삭제 (id는 변수)
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleServive.delete(id);    // Service 계층을 통해 DB에서 해당 id의 Entity를 삭제
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :    // ResponseEntity로 정상 응답 리턴
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // ResponseEntity로 잘못된 요청 응답 리턴
    }

    // Transaction -> 실패 -> 롤백!
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
        List<Article> createdList = articleServive.createArticles(dtos);    // Service 계층을 통해 트랜잭션으로 묶어 DB에 저장
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :    // ResponseEntity로 정상 응답 리턴
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // ResponseEntity로 잘못된 요청 응답 리턴
    }
}
