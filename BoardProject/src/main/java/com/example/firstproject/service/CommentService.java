package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service    // 서비스 선언! (서비스 객체를 스트링 부트에 생성)
public class CommentService {
    @Autowired  // DI, 생성 객체를 가져와 연결!
    CommentRepository commentRepository;    // Repository 객체 선언
    @Autowired  // DI, 생성 객체를 가져와 연결!
    ArticleRepository articleRepository;    // Repository 객체 선언
    
    // 댓글 목록 조회
    public List<CommentDto> comments(Long articleId) {
//        // 조회 : 댓글 목록
//        List<Comment> comments = commentRepository.findByArticleId(articleId);    // articleID에 맞는 댓글들을 Entity List로 저장
//
//        // 변환 : Entity -> DTO
//        List<CommentDto> dtos = new ArrayList<CommentDto>();  // 댓글들을 저장할 DTO List 생성
//        for (int i = 0; i < comments.size(); i++) {   // Entity List 크기만큼 반복
//            Comment c = comments.get(i);  // Entity List의 i 번째 Entity를 저장
//            CommentDto dto= CommentDto.createCommentDto(c);   // i 번째 Entity를 DTO로 변환
//            dtos.add(dto);    // 변환된 DTO를 DTO List에 추가
//        }

        // 반환   (stream 문법)
        return commentRepository.findByArticleId(articleId) // DB에서 articleID에 맞는 댓글들을 가져옴
                .stream()   // stream으로 댓글들을 추출
                .map(comment -> CommentDto.createCommentDto(comment))   // Entity 형태인 comment를 commentDto로 Mapping하여 DTO로 변환
                .collect(Collectors.toList());  // 맵핑된 comment들을 DTO List로 만듦
    }

    // 댓글 생성
    @Transactional  // 해당 메소드를 트랜잭션으로 묶는다!, 에러 발생 시 DB를 롤백
    public CommentDto create(Long articleId, CommentDto dto) {
        // 게시글 조회 및 예외 발생
       Article article = articleRepository.findById(articleId)  // articleID에 맞는 게시글 조회
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));    // 만약 없으면 예외 발생

        // 댓글 Entity 생성
        Comment comment = Comment.createComment(dto, article);  // 게시글 Article과 댓글 DTO를 결합하여 Entity로 변환

        // 댓글 Entity를 DB로 저장
        Comment created = commentRepository.save(comment);  // 생성된 Entity 형태의 댓글을 DB에 저장

        // DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);    // Entity 형태의 댓글을 다시 DTO로 변환하여 리턴
    }

    // 댓글 수정
    @Transactional  // 해당 메소드를 트랜잭션으로 묶는다!, 에러 발생 시 DB를 롤백
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id) // 해당 ID에 맞는 댓글 조회
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다.")); // 만약 없으면 예외 발생

        // 댓글 수정
        target.patch(dto);  // 수정하지 않는 데이터를 유지한채 조회한 댓글 수정

        // DB로 갱신
        Comment updated = commentRepository.save(target);   // 수정된 댓글을 DB에 저장

        // 댓글 Entity를 Dto로 변환 및 반환
        return CommentDto.createCommentDto(updated);    // Entity 형태의 수정된 댓글을 DTO로 변환하고 리턴
    }

    // 댓글 삭제
    @Transactional  // 해당 메소드를 트랜잭션으로 묶는다!, 에러 발생 시 DB를 롤백
    public CommentDto delete(Long id) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id) // 해당 ID에 맞는 댓글 조회
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));    // 만약 없으면 예외 발생

        // 댓글 삭제
        commentRepository.delete(target);   // DB에서 조회한 댓글 삭제

        // 삭제 댓글을 DTO로 반환
        return CommentDto.createCommentDto(target); // 삭제된 댓글을 DTO로 변환하고 리턴
    }
}
