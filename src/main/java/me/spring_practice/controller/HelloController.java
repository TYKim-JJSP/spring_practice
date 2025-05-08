package me.spring_practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")    //get 메소드로 url 에 "hello" 문자열이 붙으면 해당 메소드 실행
    public String hello(Model model){   //메소드 실행 시 Spring 이 model 를 만들어서 인자로 넣어준다.
        model.addAttribute("data", "spring!!");  //data 는 키, spring!! 는 값
        return "hello"; // resources.templates 의 hello.html 을 viewResolver 로 랜더링하라는 뜻
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        //RequestParam 어노테이션은 디폴트로 request 옵션이 true 이기 때문에
        //웹브라우저에서 get 메소드로 키 값이 name 인 value 를 입력하지 않으면 오류가 발생한다.
        //localhost:8080?name=***
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody   //html 의 body 태그가 아니라 http 의 body 부분에 데이터를 직접 넣는다는 뜻이다.
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);

        // 웹 브라우저에서 hello-api?name=*** 요청시 hello 객체를 JSON 방식으로 보내준다.
        // json 은 키와 밸류 쌍으로 이루어진 자료구조이다.
        // 이전에는 xml 방식도 썼으나, 현재는 json 방식을 사용하는 것이 보편적이다.
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
