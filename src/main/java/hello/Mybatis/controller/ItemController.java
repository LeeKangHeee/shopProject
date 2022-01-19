package hello.Mybatis.controller;

import hello.Mybatis.Service.item.ItemService;
import hello.Mybatis.Service.member.MemberService;
import hello.Mybatis.domain.item.Item;
import hello.Mybatis.domain.member.Member;
import hello.Mybatis.domain.member.MemberRepository;
import hello.Mybatis.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new ItemForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(ItemForm form, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setItemPrice(form.getItemPrice());
        item.setItemQuantity(form.getItemQuantity());
        item.setUserId(loginMember.getLoginId());

        itemService.save(item);
        return "redirect:/items";
    }

    /**
     * 모든 상품 목록 
     */
    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findAll();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    /**
     * 내 상품 목록
     */
    @GetMapping("/items/my")
    public String myList(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<Item> items = itemService.findMyAll(loginMember.getLoginId());
        model.addAttribute("items",items);
        return "items/myitemList";
    }

    /**
     * 상품 수정 폼
     */
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Item item =itemService.findOne(itemId);


        ItemForm form = new ItemForm();
        form.setItemId(item.getItemId());
        form.setItemName(item.getItemName());
        form.setItemPrice(item.getItemPrice());
        form.setItemQuantity(item.getItemQuantity());


        model.addAttribute("form",form);
        return "items/updateItemForm";
    }

    /**
     * 상품 수정
     */
    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") ItemForm form) {
        itemService.updateItem(form.getItemId(), form.getItemName(), form.getItemPrice(), form.getItemQuantity());
        return "redirect:/items";
    }

}
