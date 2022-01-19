package hello.Mybatis.domain.member;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface MemberRepository {

    void save(Member member);

    Member findOne(Long id);

    List<Member> memberList();


}
