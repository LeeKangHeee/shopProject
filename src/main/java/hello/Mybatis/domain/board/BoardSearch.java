package hello.Mybatis.domain.board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BoardSearch {
    private String findString;
    private BoardState boardState; // 검색 기준 [제목, 제목+내용, 작성자]
}
