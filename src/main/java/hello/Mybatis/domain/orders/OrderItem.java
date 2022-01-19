package hello.Mybatis.domain.orders;

import hello.Mybatis.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class OrderItem {

    private Long orderItemId;
    private int orderItemCount;
    private int orderItemPrice;
    private Long itemId;
    private Long orderId;


    private Item item;
    private Order order;

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);

        orderItem.setOrderItemPrice(orderPrice);
        orderItem.setOrderItemCount(count);
        orderItem.setItemId(item.getItemId());

        item.removeStock(count);
        return orderItem;
    }


    //==조회 로직==//
    public int getTotalPrice() {
        return getOrderItemPrice() * getOrderItemCount();
    }

}
