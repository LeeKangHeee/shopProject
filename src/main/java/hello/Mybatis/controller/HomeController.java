package hello.Mybatis.controller;

import hello.Mybatis.domain.member.Member;
import hello.Mybatis.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    @RequestMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        //세션이 없으면 home
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);


        //세션에 회원 데이터가 없으면 home 으로 이동
        if (loginMember == null) {
            return "home";
        }

        //세션이 유지되면 loginHome 으로 이동
        model.addAttribute("member", loginMember);

        // 관리자 로그인시 관리자 홈
        if(loginMember.getLevel()==0){
            return "adminHome";
        }
        
        // 일반회원 홈
        return "loginHome";
    }
}
