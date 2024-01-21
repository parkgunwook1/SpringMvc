package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    // 1. 기존 Servlet 방법

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
//        log.info("username={}, age={}" , username,age);

        response.getWriter().write("ok");
    }

    // 스프링이 제공하는 @RequestParam을 사용하면 요청 파라미터를 매우 편리하게 사용할 수 있다.
    // 2. 스프링 @RequestParam

    @ResponseBody // view 조회를 무시하고, Http message body에 직접 해당내용 입력
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName, // request.getParamter와 같은 효과가 있다.
            @RequestParam("age") int memberAge) {

        log.info("username={}, age={}" , memberName,memberAge);

        return "ok"; // 클래스 레벨에서 @RestController 를 사용해서 http body에 데이터를 찍을 수 있지만,
                     // @ResponseBody를 메서드 레벨에서 사용해도 return "ok" 데이터를 body에 찍을 수 있다.
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {

        log.info("username={}, age={}" , username,age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) { // String, int, Integer 등의 단순 타입이면 @RequestParam 생략 가능

        log.info("username={}, age={}" , username, age);
        return "ok";

        // @RequestParam이 있으면 명확하게 요청 파라미터에서 데이터를 읽는 다는 것을 알 수 있기 때문에 생략은 하지말자.
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username, // true는 url에 꼭 들어와야한다. => 400 에러
            @RequestParam(required = false) Integer age) {      // false는 없어도 된다. => 500 에러 why? int이기 때문에 Integer는 에러 안남.

//        int a = null;    => 기본형에는 null값 X
//        Integer b = null; => 객체는 null 가능

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam
     * - defaultValue 사용
     *
     * 참고: defaultValue는 빈 문자의 경우에도 적용
     * /request-param-default?username=
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
        // 파라미터에 값이 없는 경우 defaultValue를 사용하면 기본값을 적용할 수 있다.
    }

    /**
     * @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...]) ex) (key=userIds, value=[id1, id2])
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"),
                paramMap.get("age"));
        return "ok";

        // 파라미터의 값이 1개가 확실하다면 Map을 사용해도 되지만, 그렇지 않다면 MultiValueMap을 사용하자.
    }

}
