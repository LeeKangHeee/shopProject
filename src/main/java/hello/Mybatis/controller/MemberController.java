package hello.Mybatis.controller;

import hello.Mybatis.Service.member.MemberService;
import hello.Mybatis.domain.board.Board;
import hello.Mybatis.domain.item.Item;
import hello.Mybatis.domain.member.Member;
import hello.Mybatis.domain.member.MemberRepository;
import hello.Mybatis.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    Map<String, String> errors = new HashMap<>();

    @GetMapping("/members")
    public String memberList(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<Member> members = memberService.getList();
        model.addAttribute("members", members);

        if(loginMember.getLevel()!=0){ // 관리자 이외 해당 URL 접근시 홈으로 redirect
            return "redirect:/";
        }
        return "members/memberList";
    }

    @GetMapping("/members/new")
    public String createMember(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }


    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result, Model model){


        if (result.hasErrors()) { // 오류 발생시 다시 출력해주고 오류 메시지 보여줌
            return "members/createMemberForm";}


        Member member = new Member();
        member.setMemberName(form.getMemberName());
        member.setLoginId(form.getLoginId());
        member.setLoginPass(form.getLoginPass());
        member.setAddress(form.getAddress());
        member.setLevel(1L);

        try{
            memberService.join(member);
        }catch (IllegalStateException e){
            if (e.getMessage().equals("이미 존재하는 회원입니다.")){
                errors.put("loginId", "이미 존재하는 아이디 입니다.");
                model.addAttribute("errors",errors);
                return "members/createMemberForm";
            }
        }


        return "redirect:/";

    }
}
