package hello.Mybatis.Service.member;

import hello.Mybatis.domain.member.Member;
import hello.Mybatis.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;


    public List<Member> getList() {
        return memberRepository.memberList();
    }


    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }



    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getMemberId();
    }


    public Optional<Member> findByLoginId(String loginId) {
        return getList().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMembers = findByLoginId(member.getLoginId());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}

