package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }

        // 세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId()); // 세션 아이디
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());  // 1800초 => 30분
        log.info("creationTime={}", new Date(session.getCreationTime())); // 생성일자
        log.info("lastAccessedTime={}" , new Date(session.getLastAccessedTime())); // 마지막 접근 시간
        log.info("isNew={}", session.isNew()); // 새로 생성된 세션

        return "세션 출력";
    }
}
