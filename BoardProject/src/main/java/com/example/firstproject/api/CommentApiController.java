package com.example.firstproject.api;

import com.example.firstproject.annotation.RunningTime;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // RestAPI용 컨트롤러! 데이터(JSON)를 리턴
public class CommentApiController {
    @Autowired  // DI, 생성 객체를 가져와 연결!
    private CommentService commentService;  // Service 객체 선언

    // 댓글 목록 조회
    @GetMapping("/api/articles/{articleId}/comments")   // localhost:8080/api/articles/articleId/comments 주소로 받음 (articleId는 변수)
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        // 서비스에게 위임
        List<CommentDto> dtos = commentService.comments(articleId); // 서비스 계층에서 Article ID에 맞는 댓글들을 조회

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos); // ResponseEntity로 Body에 Entity와 정상 응답 리턴
    }

    // 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")  // localhost:8080/api/articles/articleId/comments 주소로 전송 (articleId는 변수)
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId,
                                             @RequestBody CommentDto dto) {
        // 서비스 위임
        CommentDto createdDto = commentService.create(articleId, dto);  // 서비스 계층에서 Article ID와 DTO 결합하여 댓글 생성

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);   // ResponseEntity로 Body에 Entity와 정상 응답 리턴
    }

    // 댓글 수정
    @PatchMapping("/api/comments/{id}") // localhost:8080/api/comments/id 주소의 댓글 수정 (id는 변수)
    public ResponseEntity<CommentDto> update (@PathVariable Long id,
                                              @RequestBody CommentDto dto) {
        // 서비스에게 위임
        CommentDto createdDto = commentService.update(id, dto); // 서비스 계층에서 해당 ID의 댓글을 수정

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);   // ResponseEntity로 Body에 Entity와 정상 응답 리턴
    }

    //댓글 삭제
    @RunningTime
    @DeleteMapping("/api/comments/{id}")    // localhost:8080/api/comments/id 주소의 댓글 삭제 (id는 변수)
    public ResponseEntity<CommentDto> delete (@PathVariable Long id) {
        // 서비스에게 위임
        CommentDto createdDto = commentService.delete(id);  // 서비스 계층에서 해당 ID의 댓글 삭제

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);   // ResponseEntity로 Body에 Entity와 정상 응답 리턴
    }

}
