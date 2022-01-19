package hello.Mybatis.Service.member;

import hello.Mybatis.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;

    public Member login(String loginId, String password){
        return memberService.findByLoginId(loginId)
                .filter(m->m.getLoginPass().equals(password))
                .orElse(null);

    }
}
