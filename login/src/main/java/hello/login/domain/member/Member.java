package hello.login.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Member {

    private Long id;

    @NotEmpty // 값이 null이면 안된다.
    private String loginId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;
}
