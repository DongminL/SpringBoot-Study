package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller // 이 클래스를 Controller로 지정, 뷰 템플러 페이지 반환
@Slf4j  // 로깅을 위한 골뱅이(어노테이션)
public class AticleController {

    @Autowired  // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결!
    private ArticleRepository articleRepository;    // Repository 객체 선언

    @GetMapping("/articles/new") // localhost:8080/articles/new 주소로 받음
    public String newArticleForm() {

        return "articles/new";  //  articles 폴더에 있는 new.mustache 파일을 브라우저로 전송
    }

    @PostMapping("/articles/create")    // localhost:8080/articles/create 주소로 보냄
    public String createArticle(ArticleForm form) { // 매개변수인 form은 title, content 값 전달
        //System.out.println(form.toString());  -> 로깅기능으로 대체! (print 함수를 사용하면 서버에 지장을 주기 때문)
        log.info(form.toString());  // 로그 형태로 form에 대한 정보 확인

        // 1.DTO를 변환! entity로!
        Article article = form.toEntity();  // Entity 형태인 article 객체 생성
        //System.out.println(article.toString());
        log.info(article.toString());   // 로그 형태로 article에 대한 정보 확인

        // 2. Repository에게 Entity를 DB안에 저장하게 함
        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString()); // 로그 형태로 saved에 대한 정보 확인

        return "redirect:/articles/" + saved.getId();   // 수정된 결과 페이지로 Redirect
    }

    @GetMapping("/articles/{id}")   // localhost:8080/articles/id 주소로 받음 (id는 변수)
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id); // 주소를 통해 연결된 id 값을 로그 형태로 확인

        // 1. id로 데이터를 가져옴!
        Article articeEntity = articleRepository.findById(id).orElse(null); // orElse(null)은 값이 없어서 찾음

        // 2. 가져온 데이터를 모델에 등록하고 뷰로 전달!
        model.addAttribute("article", articeEntity);

        // 3. 보여줄 페이지를 설정!
        return "articles/show"; // articles 폴더에 show.mastache 파일을 브라우저로 전송
    }

    @GetMapping("/articles")    // localhost:8080/articles 주소로 받음 (id는 변수)
    public String index(Model model) {
        // 1. 모든 Article을 가져온다!
        List<Article> articleEntityList = articleRepository.findAll();

        // 2. Article 묶음을 뷰로 전달!
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지를 설정!
        return "articles/index";    // articles/index.mustache -> 브라우저로 전달
    }

    @GetMapping("articles/{id}/edit")   // localhost:8080/articles/id/edit 주소로 받음 (id는 변수)
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터를 가져오기!
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터를 등록!
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("articles/update") // localhost:8080/articles/update 주소로 보냄
    public String update(ArticleForm form) {
        log.info(form.toString());
        
        // 1. DTO를 Entity로 변환!
        Article articleEntity = form.toEntity();    // Entity로 변환
        log.info(articleEntity.toString()); // 로그 형태로 Entity 정보 확인
        
        // 2. Entity를 DB로 저장!
        // 2-1. DB에서 기존 데이터를 가져온다!
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        
        // 2-2. 기존 데이터에 값을 갱신!
        if (target != null) {   // id가 있으면
            articleRepository.save(articleEntity);  // Entity가 DB로 갱신됨
        }
        
        // 3. 수정 결과 페이지로 Redirect!
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")    // localhost:8080/articles/id/delete 주소로 받음 (id는 변수)
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!");

        // 1. 삭제 대상을 가져온다!
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());    // 로그 형태로 삭제 대상 정보 확인

        // 2. 대상을 삭제한다!
        if (target != null) {   // id가 있으면
            articleRepository.delete(target);   // Repository를 이용하여 DB에서 해당 id를 삭제
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다!");  // 메세지 띄우기
        }

        //3. 결과 페이지로 Redirect 한다!
        return "redirect:/articles";
    }
}
