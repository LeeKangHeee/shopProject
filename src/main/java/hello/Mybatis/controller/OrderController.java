package hello.Mybatis.controller;

import hello.Mybatis.Service.item.ItemService;
import hello.Mybatis.Service.member.MemberService;
import hello.Mybatis.Service.order.OrderService;
import hello.Mybatis.domain.item.Item;
import hello.Mybatis.domain.member.Member;
import hello.Mybatis.domain.orders.Order;
import hello.Mybatis.domain.orders.OrderSearch;
import hello.Mybatis.domain.orders.JoinRepository;
import hello.Mybatis.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final JoinRepository joinRepository;

    static Member findMember(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return loginMember;
    }

    @GetMapping(value = "/order")
    public String createForm(Model model, HttpServletRequest request){

        List<Member> members = memberService.getList();
        List<Item> items = itemService.findAll();
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);


        model.addAttribute("member", loginMember);
        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";
    }

    @PostMapping(value = "/order")
    public String order(
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count,
                        HttpServletRequest request) {

        orderService.order(itemId, count, request);
        return "redirect:/orders";
    }

    /**
     * 주문목록
     */
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model, HttpServletRequest request){
        Member member = findMember(request);
        List<Order> orders = joinRepository.findJoinAll(member.getMemberId(),orderSearch.getOrderStatus());
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    /**
     * 주문 취소
     */
    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
