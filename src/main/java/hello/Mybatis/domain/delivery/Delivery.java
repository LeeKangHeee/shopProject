package hello.Mybatis.domain.delivery;

import hello.Mybatis.domain.orders.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Delivery {
    private Long deliveryId;
    private String address;
    private DeliveryStatus deliveryStatus;
    private String deliveryKey;

    private Order order;
}
