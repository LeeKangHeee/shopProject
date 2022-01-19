package hello.Mybatis.domain.orders;

import hello.Mybatis.domain.delivery.Delivery;
import hello.Mybatis.domain.delivery.DeliveryStatus;
import hello.Mybatis.domain.item.Item;
import hello.Mybatis.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
public class Order {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Long memberId;
    private Long deliveryId;

    private List<OrderItem> orderItems = new ArrayList<>();

    private Delivery accountDelivery;
    private Member accountMember;
    private Item accountItem;
    private OrderItem accountOrderItem;



    public void setDelivery(Long deliveryId){
        this.deliveryId = deliveryId;
    }

    public static Order createOrder(Long memberId, Long deliveryId){
        Order order = new Order();
        order.setMemberId(memberId);
        order.setDelivery(deliveryId);
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문취소
     */
    public void cancel(Item item, int count){
//        if(accountDelivery.getDeliveryStatus()== DeliveryStatus.COMP){ // 배송완료 되면
//            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
//        }
        item.addStock(count);
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;

        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();


    }
}
