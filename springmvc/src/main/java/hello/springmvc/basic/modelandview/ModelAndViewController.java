package hello.springmvc.basic.modelandview;


import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public class ModelAndViewController {

    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "홍길동");
        mav.setViewName("경로제시");
        return mav;
        /*
        *   ModelAndView는 객체를 만들고, 객체 형태로 반환한다.
        *   ModelAndView는 어노테이션 사용전부터 사용했음으로 구식이다.
        *
        *   요즘 트렌드는 model를 사용한다.
        * */
    }

    public String index(Model model) {
        model.addAttribute("name", "홍길동");

        return "main/index";
        /*
        * model은 파라미터에 넣어주고 String 형태로 반환한다.
        *   객체 생성 안해줘도됨.
        * */

    }


}
