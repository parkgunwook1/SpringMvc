package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*  @RestController
*
*   @Controller는 반환 값이 String이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 렌더링 된다.
*
*   @RestController는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다.
* */


@Slf4j // 아래의 private log 코드를 자동으로 넣어준다.
@RestController
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

//        log.trace("trace log= " + name);
        // 로그를 사용하지 않는데 연산이 일어나니, 쓸모없는 리소스를 사용하고 있다.
        // 그래서 아래와 같이 로그를 찍어줘야한다.

        log.trace("trace log={}" , name);
        log.debug("debug log={}" , name);
        log.info("info log={}" , name);
        log.warn("warn log={}" , name);
        log.error("error log={}" , name);

        return "ok";
    }
}
