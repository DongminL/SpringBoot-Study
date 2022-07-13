package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 이 클래스를 Controller로 지정, 뷰 템플러 페이지 반환
public class FirstController {

    @GetMapping("/hi")  // localhost:8080/hi 주소로 연결
    public String niceToMeetYou(Model model) {
        model.addAttribute("username", "Dongmin");  // Model에 데이터 등록하고 뷰로 전달
        return "greetings"; // templates/greetings.mustache -> 브라우저로 전송!
    }

    @GetMapping("bye")  // localhost:8080/hi 주소로 연결
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "동민");   // Model에 데이터 등록하고 뷰로 전달
        return "goodbye";   // templates/goodbye.mustache -> 브라우저로 전송!
    }
}
