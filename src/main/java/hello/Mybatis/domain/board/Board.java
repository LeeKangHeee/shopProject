package hello.Mybatis.domain.board;

import hello.Mybatis.domain.item.Item;
import hello.Mybatis.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class Board {

    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private String userId;
    private String boardDate;

    // 삭제시 visible 변경하여 DB 내부에서는 완전 삭제 X 사용자 화면에서만 삭제된것 처럼 보이게 하기위함
    // visible = 1 보임, visible = 0 안보임
    private Long visible;

    private Member accountMember;
    private Item accountItem;


}
 