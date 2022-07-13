package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // RestAPI용 컨트롤러! 데이터(JSON)를 반환
public class FirstApiController {

    @GetMapping("/api/hello")   // http://localhost:8080/api/hello 주소로 받음
    public String hello() {

        return "hello world!";  // Text 반환
    }
}
