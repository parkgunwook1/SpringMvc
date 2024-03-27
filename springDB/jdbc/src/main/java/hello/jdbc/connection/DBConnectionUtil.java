package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
 @Slf4j
public class DBConnectionUtil {
    public static Connection getConnection() {

/**
 * jdbc가 제공하는 "DriverManager"는 라이브러리에 등록된 db 드라이버들을 관리하고, 커넥션을 획득하는 기능을 제공한다.
 * 애플리케이션 로직에서 커넥션이 필요하면 DriverManager.getConnection()을 호출한다.
 * "DriverManager"는 라이브러리에 등록된 드라이버 목록을 자동으로 인식한다.
 * 라이브러리에 등록된(예: mysql, h2) 드라이버들에게 순서대로 정보를 넘겨서 커넥션을 획득할 수 있는지 확인한다.
 * 정보가 다르면 처리할 수 없다는 결과를 리턴하고, 다음 드라이버에게 순서가 넘어간다.
 * 이렇게 찾은 커넥션 구현체가 클라이언트에게 반환된다.
 * */
        // h2 데이터베이스 드라이버만 라이브러리에 등록했기 때문에 h2 드라이버가 제공하는 h2 커넥션을 제공받는다.
        // 물론 h2 커넥션은 jdbc가 제공하는 java.sql.Connection 인터페이스를 구현하고 있다.
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME,PASSWORD);
            log.info("get connection={}, class={}", connection,
                    connection.getClass());
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

