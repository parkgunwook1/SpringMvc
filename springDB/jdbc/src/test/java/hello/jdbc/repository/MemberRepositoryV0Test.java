package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    /**
     * 마지막에 회원을 삭제하기 대문에 테스트가 정상 수행되면, 이제부터는 같은 테스트를 반복해서 실행할 수 있다.
     * 물론 테스트 중간에 오류가 발생해서 삭제 로직을 수행할 수 없다면 테스트를 반복해서 실행할 수 없다.
     * 트랜잭션을 활용하면 이 문제를 깔끔하게 해결할 수 있다.
     * */

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        // save
        Member member = new Member("memberV100", 10000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}" , findMember);
        log.info("member == findMember {}", member == findMember );            // false
        log.info("member.equals(findMember) = {}", member.equals(findMember)); // true
        assertThat(findMember).isEqualTo(member); // 찾은멤버와 인설트한 멤버가 같은지?

        // update : money : 10000 -> 20000
        repository.update(member.getMemberId() , 20000);
        Member updateMember = repository.findById(member.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);

        // delete
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
        // 회원이 삭제되서 없기 때문에 NoSuchElementException 이 발생한다.
        // assertThatThrownBy 는 해당 예외가 발생해야 검증에 성공한다.

    }
}