package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 * DataSourceUtils.getConnection()
 * DataSourceUtils.releaseConnection()
 * */
@Slf4j
public class MemberRepositoryV3 {

    private final DataSource dataSource;

    public MemberRepositoryV3(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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

        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        // 주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        DataSourceUtils.releaseConnection(con, dataSource);
    }

    private Connection getConnection() throws SQLException {
        // 주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 된다.
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("get connection={}, calss={}", con, con.getClass());
        return con;
    }
}
