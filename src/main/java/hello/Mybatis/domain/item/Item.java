package hello.Mybatis.domain.item;

import hello.Mybatis.domain.member.Member;
import hello.Mybatis.web.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter@ToString
public class Item {
    private Long itemId;
    private String itemName;
    private int itemQuantity;
    private int itemPrice;
    private String userId;



    /**
     * stock 증가
     */
    public void addStock(int quantity){
        this.itemQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity){
        int restStock = this.itemQuantity - quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.itemQuantity = restStock;
    }
}
