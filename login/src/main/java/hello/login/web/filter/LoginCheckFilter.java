package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    // 인증 필터를 적용해도, 홈, 회원가입, 로그인 화면, css 같은 리소스에는 접근할 수 있어야 한다.

    // 로그인 필터를 사용한 덕분에 로그인 하지 않는 사용자는 아래의 경로가 아닌 다른 경로는 접근하지 못한다.
    // 공통 관심사를 서블릿 필터를 사용해서 해결한 덕분에 향후 로그인 관련 정책이 변경되어도 이 부분만 변경하면 된다.
    private static final String[] whitelist = {"/","/members/add", "/login" , "/logout", "/css/*"};
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        try {
            log.info("인증 체크 필터 시작{}" , requestURI);

            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}" ,requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {

                    log.info("미인증 사용자 요청{}", requestURI);
                    // 로그인으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI); // 리다이렉트 후 로그인 성공하면 현재페이지로 다시 리다이렉트
                    return; // 필터는 더 if 문을 타면 결국에 필터는 진행하지 않는다. 이후 필터는 물론 서블릿, 컨트롤러가 호출되지 않는다.
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }catch (Exception e) {
            throw e;
        }finally {
            log.info("인증 체크 필터 종료 {}" , requestURI);
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}
