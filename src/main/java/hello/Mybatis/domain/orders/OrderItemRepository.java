package hello.Mybatis.domain.orders;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OrderItemRepository {

    void save(OrderItem orderItem);
    OrderItem findByOrderId(Long orderId);
}
