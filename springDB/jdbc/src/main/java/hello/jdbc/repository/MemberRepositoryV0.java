package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DriverManager 사용
 * */
@Slf4j
public class MemberRepositoryV0 {

    /**
     * 리소스 정리는 꼭 해주어야 한다. 예외가 발생하든, 하지 않든 항상 수행되어야 하므로 finally 구문에 주의해서 작성해야 한다.
     * 만약 이 부분을 놓치게 되면 커넥션이 끊어지지 않고 계속 유지되는 문제가 발생할 수 있다.
     * 이런 것을 리소스 누수라고 하는데, 결과적으로 커넥션 부족으로 장애가 발생할 수 있다.
     * */

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null; // pstmt는 Statement의 자식 타입인데, ?를 통한 파라미터 바인딩을 가능하게 해준다.
        // 참고로 SQL Injection 공격을 예방하려면 "PreparedStatement"를 통한 파라미터 바인딩 방식을 사용해야 한다.

        try {
        con = getConnection(); // DBConnectionUtil 에서 얻어온 커넥션 정보를 담아둔다.
        pstmt = con.prepareStatement(sql); // 데이터베이스에 전달할 sql과 파라미터로 전달할 데이터들을 준비한다.
        pstmt.setString(1, member.getMemberId()); // 타입정보와 순서와 값을 지정한다.
        pstmt.setInt(2, member.getMoney());
        pstmt.executeUpdate(); // Statement를 통해 준비된 SQL을 커넥션을 통해 실제 데이터베이스에 전달한다.
                                // excuteUpdate() int를 반환하는데 영향받은 DB row 수를 반환한다.
        return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally {
           close(con, pstmt, null);
            // 항상 호출되는 finally에서 꺼내온 역순으로 close()를 해준다.
            // 외부 리소스인 커넥션을 꼭 닫아줘야한다.
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        /**
         * ResultSet 내부에 있는 커서를 이동해서 다음 데이터를 조회할 수 있다.
         * rs.next() : 이것을 호출하면 커서가 다음으로 이동한다. 최초의 커서는 데이터를 가리키고 있지 않기 때문에 rs.next()를
         *              최초 한번은 호출해야 데이터를 조회할 수 있다.
         */

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery(); // 데이터 변경할 때는 executeUpdate || 조회할 때는 executeQuery
            if (rs.next()) { // 데이터가 있으면 true
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }

        } catch (SQLException e) {
            log.error("db error" , e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize =  {}" , resultSize);
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con , pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }finally {
            close(con , pstmt, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error" , e);
            }

        }

       if (stmt != null) {
         try {
           stmt.close(); // SQLException 에러가 나도 catch로 잡기 때문에 con도 닫을수 있다.
         } catch (SQLException e) {
             log.info("error" , e);
         }

       }
       if (con != null) {
           try {
               con.close();
           } catch (SQLException e) {
               log.info("error" , e);
           }
       }

    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
