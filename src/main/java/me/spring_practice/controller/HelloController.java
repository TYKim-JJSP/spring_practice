package me.spring_practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")    //get 메소드로 url 에 "hello" 문자열이 붙으면 해당 메소드 실행
    public String hello(Model model){   //메소드 실행 시 Spring 이 model 를 만들어서 인자로 넣어준다.
        model.addAttribute("data", "spring!!");  //data 는 키, spring!! 는 값
        return "hello"; // resources.templates 의 hello.html 을 viewResolver 로 랜더링하라는 뜻
    }
}
