package hello.Mybatis.domain.orders;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderRepository {

    void save(Order order);
    void updateStatus(Long orderId, OrderStatus orderStatus);

    Order findOne(Long id);
    Order findOrderId(Long deliveryId, Long memberId);

}
