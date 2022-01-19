package hello.Mybatis.Service.order;

import hello.Mybatis.Service.item.ItemService;
import hello.Mybatis.domain.delivery.Delivery;
import hello.Mybatis.domain.delivery.DeliveryStatus;
import hello.Mybatis.domain.item.Item;
import hello.Mybatis.domain.item.ItemRepository;
import hello.Mybatis.domain.member.Member;
import hello.Mybatis.domain.orders.*;
import hello.Mybatis.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final DeliveryService deliveryService;
    private final ItemService itemService;
    private final OrderItemRepository orderItemRepository;

    public Long order(Long itemId, int count, HttpServletRequest request) {

        //엔티티 조회
        Item item = itemRepository.findOne(itemId);  // itemId, itemName, itemQuantity, itemPrice 조회가능

        //현재 세션 멤버 id값 찾기
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long memberId = member.getMemberId();

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getItemPrice(), count);

        // 아이템 업데이트 ( 주문수만큼 Quantity 감소 )
        itemService.updateItem(item.getItemId(), item.getItemName(), item.getItemPrice(), item.getItemQuantity());


        String deliveryKey = UUID.randomUUID().toString();


        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);
        delivery.setDeliveryKey(deliveryKey);
        deliveryService.save(delivery);


        // 멤버 주소를 통해 delivery id값 가져오기 수정 !!!!!!!!!!!!!!!1
        Delivery dv = deliveryService.findDeliveryId(deliveryKey);
        Long deliveryId = dv.getDeliveryId();

        //주문 생성
        Order order = Order.createOrder(memberId, deliveryId);

        //주문 저장
        orderRepository.save(order);


        // 주문상품저장
        Order getOrder = orderRepository.findOrderId(deliveryId, memberId);
        Long orderId = getOrder.getOrderId();
        orderItem.setOrderId(orderId);
        orderItemRepository.save(orderItem);

        return order.getOrderId();


    }

    /**
     * 주문취소
     */
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);


        // DB 갱신 orderStatus = CANCEL, orderItemCount 갱신
        OrderItem orderItem = orderItemRepository.findByOrderId(orderId);
        Long itemId = orderItem.getItemId();
        int count = orderItem.getOrderItemCount();

        Item item = itemRepository.findById(itemId);
        //주문 취소
        order.cancel(item, count);
        itemService.updateItem(item.getItemId(), item.getItemName(), item.getItemPrice(), item.getItemQuantity());

        orderRepository.updateStatus(orderId, OrderStatus.CANCEL);

    }
}


