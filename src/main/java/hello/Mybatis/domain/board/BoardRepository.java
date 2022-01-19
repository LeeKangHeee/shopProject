package hello.Mybatis.domain.board;

import hello.Mybatis.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BoardRepository {
    void save(Board board);
    void updateBoard(Long boardId, String boardTitle, String boardContent);
    void deleteBoard(Long boardId);

    Board findById(Long boardId);

    List<Board> findAll(Long visible);
    List<Board> findByUserId(String userId);

    List<Board> findByTitle(String findString); //  제목으로 검색
    List<Board> findByContent(String findString); // 제목+ 내용으로 검색




    Board getBoardId(String boardTitle, String boardContent, String userId, String boardDate);

}
