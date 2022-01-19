package hello.Mybatis.Service.item;

import hello.Mybatis.Service.member.MemberService;
import hello.Mybatis.domain.item.Item;
import hello.Mybatis.domain.item.ItemRepository;
import hello.Mybatis.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberService memberService;


    public void save(Item item) {
        itemRepository.save(item);
    }


    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }


    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public List<Item> findMyAll(String loginId) {
        return itemRepository.findMyAll(loginId);
    }



    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setItemId(itemId);
        findItem.setItemName(name);
        findItem.setItemPrice(price);
        findItem.setItemQuantity(stockQuantity);

        itemRepository.updateItem(findItem.getItemId(), findItem.getItemName(), findItem.getItemPrice(), findItem.getItemQuantity());
    }
}
