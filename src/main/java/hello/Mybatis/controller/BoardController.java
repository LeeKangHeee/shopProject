package hello.Mybatis.controller;

import hello.Mybatis.Service.board.BoardService;
import hello.Mybatis.domain.board.*;
import hello.Mybatis.domain.member.Member;
import hello.Mybatis.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;


    static Member findMember(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return loginMember;
    }


    //게시글 목록
    @GetMapping("/board")
    public String list(@ModelAttribute("BoardSearch") BoardSearch boardSearch, Model model){
        List<Board> boards = boardService.boardSearch(boardSearch.getBoardState(), boardSearch.getFindString());


        model.addAttribute("boards",boards);
        return "board/boardList";

    }

    //내게시글 목록
    @GetMapping("/board/my")
    public String myList(Model model, HttpServletRequest request){
        Member loginMember = findMember(request);
        List<Board> boards = boardRepository.findByUserId(loginMember.getLoginId());

        model.addAttribute("boards",boards);
        return "board/boardView";

    }

    //삭제후 목록 재출력
   @GetMapping("board/{boardId}/delete")
   public String deleteBoard(@PathVariable("boardId") Long boardId,Model model,HttpServletRequest request){
        boardService.deleteBoard(boardId);
        Member loginMember = findMember(request);

        List<Board> boards = boardRepository.findByUserId(loginMember.getLoginId());
        model.addAttribute("boards",boards);
        return "board/boardView";
   }


    //게시글 수정
    @GetMapping("board/{boardId}/edit")
    public String updateBoardForm(@PathVariable("boardId") Long boardId, Model model){
        Board board = boardRepository.findById(boardId);
        BoardForm form = new BoardForm();
        //form.setBoardId(board.getBoardId());
        form.setBoardTitle(board.getBoardTitle());
        form.setBoardContent(board.getBoardContent());

        model.addAttribute("form",form);
        return "board/updateBoardForm";
    }

    @PostMapping("board/{boardId}/edit")
    public String updateBoard(@ModelAttribute("form") BoardForm form) {
        boardService.updateBoard(form.getBoardId(), form.getBoardTitle(), form.getBoardContent());
        return "redirect:/board";
    }

    // 게시글작성
    @GetMapping("/board/new")
    public String createForm(Model model){
        model.addAttribute("form", new BoardForm());
        return "board/createBoardForm";

    }

    @PostMapping("/board/new")
    public String create(@Valid BoardForm form, RedirectAttributes redirectAttributes, HttpServletRequest request){
        Long boardId = boardService.board(form, request);

        redirectAttributes.addAttribute("boardId", boardId);

        return "redirect:/board/{boardId}";
    }

    // 게시글 상세폼
    @GetMapping("/board/{id}")
    public String boards(@PathVariable Long id, Model model, HttpServletRequest request) {
        Board board = boardRepository.findById(id);

        Member loginMember = findMember(request);
        String loginId = loginMember.getLoginId();

        model.addAttribute("loginId", loginId); // 자기자신의 글만 수정, 삭제 버튼활성화
        model.addAttribute("board", board);
        return "board/board";
    }
}
