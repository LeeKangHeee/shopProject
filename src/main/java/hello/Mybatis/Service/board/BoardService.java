package hello.Mybatis.Service.board;

import hello.Mybatis.controller.BoardForm;
import hello.Mybatis.domain.board.Board;
import hello.Mybatis.domain.board.BoardRepository;

import hello.Mybatis.domain.board.BoardState;
import hello.Mybatis.domain.member.Member;
import hello.Mybatis.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(Board board){
            boardRepository.save(board);
    }

    public void updateBoard(Long boardId, String boardTitle, String boardContent){
        boardRepository.updateBoard(boardId,boardTitle,boardContent);
    }

    public void deleteBoard(Long boardId){
        boardRepository.deleteBoard(boardId);
    }


    public List<Board> boardList(){
        return boardRepository.findAll(1L);
    }

    public Board getId(String boardTitle, String boardContent, String userId, String boardDate){
        return boardRepository.getBoardId(boardTitle,boardContent,userId, boardDate);
    }

    public Long board(BoardForm form, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Board board = new Board();

        board.setBoardTitle(form.getBoardTitle());
        board.setBoardContent(form.getBoardContent());
        board.setUserId(loginMember.getLoginId());
        board.setBoardDate(time);
        board.setVisible(1L);


        save(board);

        Board id = getId(board.getBoardTitle(), board.getBoardContent(), board.getUserId(), board.getBoardDate());
        return id.getBoardId();

    }

    public List<Board> boardSearch(BoardState boardState, String findString){
        if(boardState == BoardState.userId){
           return boardRepository.findByUserId(findString);
        }

        if(boardState == BoardState.TitleContent){
           return boardRepository.findByContent(findString);
        }

        if(boardState == BoardState.Title){
            return boardRepository.findByTitle(findString);
        }

        return boardRepository.findAll(1L);
    }





}
