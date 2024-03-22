package hello.typeconverter.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // 여기서 받은 HTTP 쿼리 스트링으로 전달하는 data=10 부분에서 10은 숫자 10이 아니라 문자열 10이다.
    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data"); // 문자 타입 조회
        Integer intValue = Integer.valueOf(data); // 숫자 타입으로 변경
        System.out.println("intValue = " + intValue);
        return "ok";
    }

    // 스프링이 제공하는 @RequestParam을 사용하면 이 문자 10을 Integer 타입의 숫자 10으로 편리하게 받을 수 있다.
    // 스프링이 중간에서 타입을 변환해주었기 때문이다.
    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data) {
        System.out.println("data = " + data);
        return "ok";
    }
    // 스프링 MVX 요청 파라미터 @RequestParam , @ModelAttribute, @PathVariable 등등 타입 변환을 해준다.



}
