package hello.Mybatis.domain.item;

import hello.Mybatis.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ItemRepository {

        void save(Item item);
        Item findOne(Long itemId);
        List<Item> findAll();
        List<Item> findMyAll(String loginId);
        Item findById(Long itemId);

        void updateItem(Long itemId, String itemName, int itemPrice, int itemQuantity);
}
