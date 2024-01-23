package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;
    }

//    @ResponseBody // resposebody 사용하면 return이 문자열로 나간다. => response/hello 문자열 출력.
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
                model.addAttribute("data", "hello!");

        return "response/hello";
    }

    @RequestMapping("/response/hello") // 권장하지 않는 방법
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
    /*
    *   String을 반환하는 경우
    *   @ResponseBody가 없으면 response/hello로 뷰 리졸버가 실행되어서 뷰를 찾고, 렌더링 한다.
    *   @ResponseBody가 있으면 뷰 리졸버를 실행하지 않고, HTTP 메시지 바디에 직접 response/hellp 라는 문자가 입력된다.
    *
    *   3번째 void를 반환하는 경우
    *   @Controller를 사용하고, HttpServletResponse, OutpustStream(writer) 같은 HTTP 메시지 바디를 처리하는 파라미터가 없으면 요청
    *   URL을 참고해서 논리 뷰 이름으로 사용
    *       요청 URL : /response/hello
    *       실행 : templates/response/hello.html
    *   - 참고로 이 방식은 명시성이 너무 떨어지고 이렇게 딱 맞는 경우도 많이 없어서, 권장하지 않는다.
    *
    *
    * */
}
